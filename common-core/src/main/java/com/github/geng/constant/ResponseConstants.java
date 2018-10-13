package com.github.geng.constant;


/**
 * 系统自定义响应状态码
 * @author geng
 */
public class ResponseConstants {
    private ResponseConstants() { }
    // ------------------------------------------------
    // 用户端token
    /**
     * 用户token无效
     */
    public static int USER_INVALID_TOKEN = 50001;
    /**
     * 用户token 禁止访问
     */
    public static int USER_FORBIDDEN_TOKEN = 500403;
    /**
     * 默认客户输入异常状态码
     */
    public static int USER_INPUT_ERROR = 500500;

    /**
     * 远程调用异常
     */
    public static int REMOTE_EXCEPTION = 500002;

    /**
     *  服务端token异常
     */
    public static final int CLIENT_FORBIDDEN = 4403;

}
