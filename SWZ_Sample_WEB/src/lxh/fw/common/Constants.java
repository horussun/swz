package lxh.fw.common;

import java.util.Locale;

/**
 * 定义静态变量
 */
public class Constants {

    public static final Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;

    public static final String DEFAULT_RFC_LOCALE = "zh-CN";

    static {
        Locale.setDefault(DEFAULT_LOCALE);
    }

    /* AJAX post parameter */

    public static final String AJAX_POST_PARAM_KEY = "";

    public static final String AJAX_POST_PARAM_DATA = "tms.data";

    public static final String AJAX_POST_PARAM_TYPE = "tms.type";

    public static final String AJAX_POST_PARAM_CONTENT = "tms.content";

    public static final String AJAX_POST_PARAM_FILTER = "filter";

    public static final String AJAX_POST_JSON_PARA_NAME = "tms.json";

    public static final String AJAX_POST_RESULT = "tms.result";

    public static final String JSON_ITEMS = "items";

    // 批量输入地点后缀
    public static final String LOC_SUFFIX = "+";
    
    public static String USER_CONTEXT = "user.context";
    
    public static final int DEFAULT_PAGE_SIZE = 10;
    
    public static final String COMMAND = "command";

}
