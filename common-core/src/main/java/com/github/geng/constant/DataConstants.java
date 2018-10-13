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
    // --------------------------------------------------------
    // 提示语
    public static final String NO_FOUND = "查无对应记录";

    // 用户权限不足
    public static final String USER_FORBIDDEN = "用户权限不足";
    // -------------------------------------------------------
    // 网关向内部微服务传递的请求头常量名
    /**
     * 用户id
     */
    public static final String USER_ID = "userId";
    /**
     * 用户名称
     */
    public static final String USER_NAME = "userName";
}
