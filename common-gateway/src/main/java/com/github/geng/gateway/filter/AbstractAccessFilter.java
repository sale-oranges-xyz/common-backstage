package com.github.geng.gateway.filter;

import com.github.geng.constant.DataConstants;
import com.github.geng.constant.ResponseConstants;
import com.github.geng.exception.BaseException;
import com.github.geng.response.ApiResponseData;
import com.github.geng.token.TokenService;
import com.github.geng.token.info.TokenInfo;
import com.github.geng.util.JSONUtil;
import com.github.geng.util.SysStringUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;

/**
 * <pre>
 *  只抽象网关run 部分通用token验证逻辑
 *  验证什么与如何验证，交给子类处理
 * </pre>
 * @author geng
 */
public abstract class AbstractAccessFilter extends ZuulFilter {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${gate.ignore.startWith}")
    private String startWith;
    // 这里一个接口，具体逻辑由子类实现，使用接口，原因是类设计原则的接口隔离原则(ISP)
    @Autowired
    private TokenService tokenService;

    /**
     * 过滤器的具体逻辑
     * @return object
     */
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();

        HttpServletRequest request = context.getRequest();
        // System.out.printf("content-type: "+ request.getContentType());
        String requestUri = request.getRequestURI(); // 添加了zuul前缀的请求url

        // 拦截白名单
        if (null != startWith && SysStringUtil.isStartWith(requestUri, startWith.split(","))) {
            return null;
        }
        // 从request header获取token数据
        String token = request.getHeader(this.getTokenHeader());
        if (!StringUtils.hasText(token)) {
            this.sendErrMsg("用户未登录", ResponseConstants.USER_INVALID_TOKEN, context);
        } else { // 从token 获取用户信息
            String realToken = this.getRealToken(token); // 或者真正的token数据
            try {
                TokenInfo tokenInfo = tokenService.parseToken(realToken);
                this.validateTokenInfo(tokenInfo, (String)context.get(REQUEST_URI_KEY), request.getMethod(), context);
                // 用户信息写入请求头，供后续内部微服务使用
                context.addZuulRequestHeader(DataConstants.USER_ID, tokenInfo.getId());
                context.addZuulRequestHeader(DataConstants.USER_NAME, tokenInfo.getName());
                return Boolean.TRUE;
            } catch (Exception e) {
                int statusCode = ResponseConstants.USER_INVALID_TOKEN;
                if (e instanceof BaseException) {
                    statusCode = ((BaseException)e).getStatus();
                }
                logger.error("网关获取解析用户token异常,原因", e);
                this.sendErrMsg(e.getMessage(), statusCode, context);
            }
        }
        return null;
    }

    // -----------------------------------------------------------------------
    // abstract methods
    /**
     * 获取请求头的token
     * @return 请求头token
     */
    protected abstract String getTokenHeader();

    /**
     * 解析出真正的token
     * @return 真正token
     */
    protected abstract String getRealToken(String tokenHeader);

    /**
     * <pre>
     *  验证token 具体逻辑
     *  所有异常可以向上抛出,抽象类已捕获全部异常
     * </pre>
     * @param tokenInfo 解析出来的token信息
     * @param requestUri 请求路径
     * @param method 请求方法
     * @param context 上下文信息，也可以使用RequestContext.getCurrentContext()获取
     */
    protected abstract void validateTokenInfo(TokenInfo tokenInfo, String requestUri, String method, RequestContext context);

    // -----------------------------------------------------------------------
    // methods
    /**
     * 发送错误信息，不过状态码为200
     * @param errMsg 错误消息
     * @param returnCode 返回状态码
     * @param ctx 请求上下文
     */
    protected void sendErrMsg(String errMsg, int returnCode, RequestContext ctx) {
        if (null == ctx.getResponseBody()) {
            ctx.setResponseStatusCode(HttpStatus.OK.value());
            ctx.setResponseBody(JSONUtil.createJson(new ApiResponseData<>(returnCode, errMsg)));
        }
    }
}
