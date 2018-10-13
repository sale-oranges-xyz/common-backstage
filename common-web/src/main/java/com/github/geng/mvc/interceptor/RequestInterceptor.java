package com.github.geng.mvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 * 使用参考 https://blog.csdn.net/u011781521/article/details/78624276
 * @author geng
 */
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {
    // 如果 httpServletRequest.setAttribute 无法生效，可以使用 ThreadLocal 进行代替

    /**
     * <pre>
     *      该方法将在请求处理之前进行调用，只有该方法返回true，才会继续执行后续的Interceptor和Controller，
     *      当返回值为true 时就会继续调用下一个Interceptor的preHandle 方法，
     *      如果已经是最后一个Interceptor的时候就会是调用当前请求的Controller方法
     * </pre>
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        httpServletRequest.setAttribute("logStartTime", startTime);
        // 如果需要输出json 格式，这里拼接
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod h = (HandlerMethod) handler;
//            ControllerLog controllerLog = h.getMethodAnnotation(ControllerLog.class);
//            if (null != controllerLog) {
//                 // 这里需要处理用户操作日志，考虑统一推到日志处理中心，然后分析处理
//            }
//        }
        return true;
    }

    /**
     * <pre>
     *     该方法将在请求处理之后，DispatcherServlet进行视图返回渲染之前进行调用，
     *     可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作
     * </pre>
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * <pre>
     *     该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行，
     *     该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。用于进行资源清理。
     * </pre>
     * */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            long startTime = (Long) httpServletRequest.getAttribute("logStartTime");
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;
            HandlerMethod h = (HandlerMethod) handler;
            StringBuilder sb = new StringBuilder(1000);
            // 如果需要输出json 格式，这里拼接
            sb.append("URI: ").append(httpServletRequest.getRequestURI()).append(" ");
            sb.append(" ").append(h.getBean().getClass().getName());
            sb.append(".").append(h.getMethod().getName()).append("(");
            Class<?>[] parameterTypes = h.getMethod().getParameterTypes();
            for (Class clazz : parameterTypes) {
                sb.append(clazz.getName());
                sb.append(",");
            }
            sb = sb.deleteCharAt(sb.length() - 1); // 去掉末尾 ,
            sb.append(")");
            sb.append(String.format("执行时间: %s ms", executeTime));
            log.debug(sb.toString());
        }
    }


}
