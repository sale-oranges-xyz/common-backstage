package com.github.geng.security.filter;

import com.github.geng.constant.DataConstants;
import com.github.geng.constant.ResponseConstants;
import com.github.geng.exception.ErrorMsg;
import com.github.geng.security.entity.SysUserDetails;
import com.github.geng.token.TokenService;
import com.github.geng.token.info.Token;
import com.github.geng.token.info.TokenInfo;
import com.github.geng.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


/**
 * token 过滤器
 * @author geng
 */
@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private Token token;
    /**
     * Spring Security 的核心操作服务类
     * 在当前类中将使用 UserDetailsService 来获取 userDetails 对象
     */
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse response, FilterChain chain)
            throws IOException {

        try {
            // token
            String realToken = this.getToken(httpRequest);
            if (null == realToken) {
                chain.doFilter(httpRequest, response);
            } else {
                // 使用token 解析
                TokenInfo tokenInfo = this.tokenService.parseToken(realToken);

                // UserDetails 类是 Spring Security 用于保存用户权限的实体类
                SysUserDetails userDetails = (SysUserDetails)this.userDetailsService.loadUserByUsername(tokenInfo.getName());
                if (userDetails.getUsername().equals(tokenInfo.getName())) { // 用户名一致
                    if (this.validateToken(realToken, userDetails)) {
                        // 生成通过认证
                        UsernamePasswordAuthenticationToken authentication
                                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        // 将权限写入本次会话
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        // 用户信息写入request
                        httpRequest.setAttribute(DataConstants.USER_ID, tokenInfo.getId());
                        httpRequest.setAttribute(DataConstants.USER_NAME, tokenInfo.getName());
                        chain.doFilter(httpRequest, response);
                        return;
                    }
                }
                this.sendErrorMsg(response, "用户登录超时，请重新登录");
            }
        } catch (Exception e) {
            logger.error("token 过滤器处理异常", e);
            this.sendErrorMsg(response, e.getMessage());
        }
    }

    // private methods ------------------------------------------------------------------------
    private void sendErrorMsg (HttpServletResponse response, String errMsg) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        ErrorMsg errorMsg = new ErrorMsg(errMsg, DataConstants.USER_TIME_OUT);
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
            return !userDetails.isUpdatedPassword(expirationDate);
        }
        return false;
    }

    private String getToken(HttpServletRequest httpRequest) {
        String tokenHeader = httpRequest.getHeader(this.token.getHeader());
        if (null != tokenHeader) {
            return this.token.getToken(tokenHeader);
        }
        return null;
    }

    // setters ----------------------------------------------------------------------
    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @Autowired
    public void setToken(Token token) {
        this.token = token;
    }
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}

