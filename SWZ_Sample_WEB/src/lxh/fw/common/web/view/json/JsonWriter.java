package lxh.fw.common.web.view.json;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * json对象的输出类。负责将json转换成字符流输出到客户端
 * 
 */
public interface JsonWriter {

	public void write(Map<?, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
