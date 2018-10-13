package com.github.geng.gateway.filter;

import com.github.geng.response.ApiResponseData;
import com.github.geng.util.JSONUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <pre>
 * 网关全局异常处理
 * 参考 https://www.cnblogs.com/duanxz/p/7543040.html
 *      https://blog.csdn.net/vakinge/article/details/78705319
 *      https://www.jianshu.com/p/2de6290d7bf1
 * </pre>
 * @author geng
 */
@Slf4j
@Component
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        //需要在默认的 SendErrorFilter 之前
        return -1; // Needs to run before SendErrorFilter which has filterOrder == 0
    }

    @Override
    public boolean shouldFilter() {
        // only forward to errorPath if it hasn't been forwarded to already
        return RequestContext.getCurrentContext().containsKey("throwable");
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        try {
            HttpServletRequest request = ctx.getRequest();
            // 异常状态默认为 500
            int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            String message = (String) ctx.get("error.message");
            if (ctx.containsKey("error.exception")) {
                Throwable e = (Exception) ctx.get("error.exception");
                Throwable re = getOriginException(e);
                if(re instanceof java.net.ConnectException){
                    statusCode = HttpStatus.SERVICE_UNAVAILABLE.value(); // 无效
                    message = "Real Service Connection refused";
                    log.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
                } else if(re instanceof java.net.SocketTimeoutException){
                    statusCode = HttpStatus.REQUEST_TIMEOUT.value(); // 超时
                    message = "Real Service Timeout";
                    log.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
                } else if(re instanceof com.netflix.client.ClientException){
                    message = re.getMessage();
                    log.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
                } else {
                    log.warn("Error during filtering",e);
                }
            }
            if (StringUtils.isBlank(message)) {
                message = "系统繁忙,请稍后再试";
            }
            // Remove error code to prevent further error handling in follow up filters
            // 删除该异常信息,不然在下一个过滤器中还会被执行处理
            ctx.remove("throwable");
            // 根据具体的业务逻辑来处理
            this.outputJson(response, new ApiResponseData<>(statusCode, message));
        } catch (Exception ex) {
            log.error("Exception filtering in custom error filter", ex);
            try {
                this.outputJson(
                        response,
                        new ApiResponseData<>(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取最终异常信息
     * @param e 异常
     * @return 最终异常信息
     */
    private Throwable getOriginException(Throwable e){
        e = e.getCause();
        while(e.getCause() != null){
            e = e.getCause();
        }
        return e;
    }

    private <T> void outputJson(HttpServletResponse response, ApiResponseData<T> apiResponseData)
            throws IOException {
        // utf-8 输出json 数据
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();//获取PrintWriter输出流
        out.write(JSONUtil.createJson(apiResponseData));
        out.flush();
    }
}
