package com.github.geng.security.filter;

import com.github.geng.constant.DataConstants;
import com.github.geng.exception.ErrorMsg;
import com.github.geng.security.service.SysUserDetails;
import com.github.geng.security.service.SysUserDetailsService;
import com.github.geng.token.TokenService;
import com.github.geng.token.info.Token;
import com.github.geng.token.info.TokenInfo;
import com.github.geng.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;


/**
 * token 过滤器
 * @author geng
 */
@Slf4j
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

//    @Autowired
//    private AuthenticationSerious authenticationSerious;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Token token;
    /**
     * Spring Security 的核心操作服务类
     * 在当前类中将使用 UserDetailsService 来获取 userDetails 对象
     */
    @Autowired
    private SysUserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        try {
            // token
            // 尝试获取请求头的 token
            String headerToken = httpRequest.getHeader(this.token.getTokenHeader());
            String realToken = this.token.getToken(headerToken);
            // 使用token 解析
            TokenInfo tokenInfo = this.tokenService.parseToken(realToken);

            // UserDetails 类是 Spring Security 用于保存用户权限的实体类
            SysUserDetails userDetails = this.userDetailsService.loadUserByUsername(tokenInfo.getName());
            if (userDetails.getUsername().equals(tokenInfo.getName())) { // 用户名不一致
                if (this.validateToken(realToken, userDetails)) {
                    // 暂时不需要权限验证
                    // 生成通过认证
                    // UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    //         = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // 将权限写入本次会话
                    // super.setDetails(httpRequest, usernamePasswordAuthenticationToken);
                    // SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    // 用户信息写入request
                    request.setAttribute(DataConstants.USER_ID, tokenInfo.getId());
                    request.setAttribute(DataConstants.USER_NAME, tokenInfo.getName());
                    chain.doFilter(request, response);
                }
            }
            this.sendErrorMsg(response, "用户登录超时，请重新登录");
        } catch (Exception e) {
            this.sendErrorMsg(response, e.getMessage());
        }
    }

    private void sendErrorMsg (ServletResponse response, String errMsg) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        ErrorMsg errorMsg = new ErrorMsg(errMsg, HttpStatus.FORBIDDEN.value());
        response.getWriter().print(JSONUtil.createJson(errorMsg));
        response.getWriter().flush();
    }

    /**
     * @param realToken httpRequest对象获取的token
     * @param userDetails 查询的用户信息
     * @return true 验证通过 | false 验证不通过
     */
    private boolean validateToken(String realToken, SysUserDetails userDetails) {
        // 检查用户带来的 token 是否有效
        // 包括 token 和 userDetails 中用户名是否一样， token 是否过期， token 生成时间是否在最后一次密码修改时间之前
        // 若是检查通过
        Date expirationDate = tokenService.getExpirationDate(realToken);
        if (new Date().before(expirationDate)) { // 没有过期
            return !userDetails.isPasswordUpdate(expirationDate);
        }
        return false;
    }
}

