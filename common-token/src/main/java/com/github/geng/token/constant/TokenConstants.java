package com.github.geng.token.constant;

/**
 * token 部分常量配置
 * <pre>
 *   虽然把这个token常量类放在common-core constant包下也可以
 *   但是这个常量类是因为使用了token才会出现
 *   如果不使用token的话，这个类不应该出现代码其他地方
 *   这个常量类，是很明显的模块私有化类
 * </pre>
 * @author geng
 */
public class TokenConstants {

    public static final String RECORD_ID = "userId";
    public static final String RECORD_NAME = "name";
    public static final String CLAIM_KEY_USERNAME = "sub";
    public static final String CLAIM_KEY_CREATED = "created";
}
