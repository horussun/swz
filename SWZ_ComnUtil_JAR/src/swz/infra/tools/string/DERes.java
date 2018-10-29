package swz.infra.tools.string;

import java.util.ResourceBundle;

/**
 * 读取配置
 *
 */
public class DERes {

	private final static String _PROPERTIES_FILE = "resource.mq";

	private static ResourceBundle getRes() {
		ResourceBundle resourceBundle = ResourceBundle
				.getBundle(_PROPERTIES_FILE);
		return resourceBundle;
	}

	public static String s(String key) {
		ResourceBundle resourceBundle = getRes();
		return resourceBundle.getString(key);
	}
	
	public static void main(String[] args) {
		System.out.println(DERes.s("JNDI_NAME"));
	}
	
}
