package swz.infra.tools.cache;

public class BQDCacheKeys {
	
	private static final String m_MAIN_CUT="$";
	private static final String m_SUB_CUT="*";
	/**
	 * 得到Portlet的内存Key
	 * @param user_id
	 * @param app_code
	 * @param model_id
	 * @param page_name
	 * @param portlet_name
	 * @return
	 */
	public static String getPortletMemKey(String user_id, String app_code, String model_id, String page_name,
			String portlet_name){
		String subKey=m_MAIN_CUT+app_code+m_SUB_CUT+model_id+m_SUB_CUT+page_name+m_SUB_CUT+portlet_name;
		return getUserKey(user_id)+subKey;
	}
	
	/**
	 * 得到theme的内存Key
	 * @param user_id
	 * @param app_code
	 * @param theme_code
	 * @return
	 */
	public static String getThemeMemKey(String user_id, String app_code){
		String subKey=m_MAIN_CUT+app_code+m_SUB_CUT+"theme";
		return getUserKey(user_id)+subKey;
	}
	
	/**
	 * 得到内存中 用户Key
	 * @param user_id
	 * @return
	 */
	public static String getUserKey(String user_id){
		return user_id;
	}

}
