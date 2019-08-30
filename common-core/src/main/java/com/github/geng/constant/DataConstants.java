package com.github.geng.constant;

/**
 * 系统数据常量配置
 * @author geng
 */
public class DataConstants {
    private DataConstants(){}

    //==================================
    //状态
    /**
     * 禁用标志
     */
    public static final int DISABLE = 0;
    /**
     * 启用标志
     */
    public static final int ENABLE = 1;
    /**
     * 删除状态
     */
    public static final int DELETE = 2;
    // 用户登录异常代码  --------------------------------------------------
    /**
     * 用户登录账号密码错误
     */
    public static final int USER_ERR_PWD = -1;
    /**
     * 用户账号禁止访问
     */
    public static final int USER_FORBIDDEN = -2;
    /**
     * 用户登录超时
     */
    public static final int USER_TIME_OUT = -3;
    /**
     * 用户未注册
     */
    public static final int USER_NOT_FOUND = -4;

    // --------------------------------------------------------
    // 网关向内部微服务传递的请求头常量名
    /**
     * 用户id
     */
    public static final String USER_ID = "userId";
    /**
     * 用户名称
     */
    public static final String USER_NAME = "userName";

    // --------------------------------------------------------
    // 微信相关
    /**
     * 类型：微信小程序
     */
    public static final int WX_MIN_APP_TYPE = 0;

    /**
     * 类型：微信
     */
    public static final int WX_TYPE = 1;
}
