package com.self.cloudserver.constants;

/**
 * 公共常量
 * @author zp
 */
public final class CommonConstants {

    private CommonConstants(){
        super();
    }

    /**
     * 空字符串
     */
    public static final String STR_EMPTY = "";

    /**
     * POINT
     */
    public static final String STR_POINT = ".";

    /**
     * COMMA
     */
    public static final String STR_COMMA = ",";

    /**
     * COLON
     */
    public static final String STR_COLON = ":";

    /**
     * Token Header名称
     */
    public static final String TOKEN_HEADER_KEY = "access_token";

    /**
     * Token终端类型Header名称
     */
    public static final String TOKEN_TERMINAL_TYPE = "terminal_type";

    /**
     * Token前缀(web)
     */
    public static final String TOKEN_WEB_PREFIX = "web_";

    /**
     * Token前缀(app)
     */
    public static final String TOKEN_APP_PREFIX = "app_";

    /**
     * 登录信息前缀
     */
    public static final String LOGIN_DETAIL_PREFIX = "smart:cloud:loginDetail:";

}
