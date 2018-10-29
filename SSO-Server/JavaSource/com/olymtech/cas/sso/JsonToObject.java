package com.olymtech.cas.sso;

import org.json.JSONException;    
import org.json.JSONObject;  

/**
 * 序列化对象为JSON格式 遵循JSON组织公布标准
 * 
 * @date 2009/07/20
 * @version 1.0.0
 */
public class JsonToObject {
	/**
	 * 将str 转为jsonobject 
	 * @param str
	 * @return
	 */
	public static JSONObject getJSONObject(String str) {
		if (str == null || str.trim().length() == 0)
			return null;
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(str);
		} catch (JSONException e) {
			e.printStackTrace(System.err);
		}
		return jsonObject;
	}

	 
}
