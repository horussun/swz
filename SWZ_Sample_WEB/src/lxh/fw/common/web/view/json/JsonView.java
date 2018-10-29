package lxh.fw.common.web.view.json;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.AbstractView;

/**
 *  包装数据为json格式输出
 *
 */
public class JsonView extends AbstractView {

	private static final String DEFAULT_ENCODING = "UTF-8";

	private static final String DEFAULT_JSON_CONTENT_TYPE = "application/json";

	// 直接输出变量名称。一般，输出会是{name:value}的形式。如果用该名称，则直接是value
	public static final String DIRECT_OUT_NAME = "JSON-DIRECT-OBJECT";

	// 指示是否需要深度序列化。只要model里有该变量，则表示需要
	public static final String DEEP_SERIALIZE = "need_deep_serialize";
	
	// 强制要求序列化的属性，用逗号或分号隔开
	public static final String INCLUDE_FIELDS = "json_include_fields";
	
	// 不要求序列化的属性，用逗号或分号隔开
	public static final String EXCLUDE_FIELDS = "json_exclude_fields";
	
	private String encoding;

	private JsonWriter jsonWriter = new IBMJsonWriter();

	public JsonView() {
		super();
		setContentType(DEFAULT_JSON_CONTENT_TYPE);
		setEncoding(DEFAULT_ENCODING);
	}

	public void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding(encoding);
		//如果设定了内容类型，则不再重复设置。(有文件上传的表单提交时，需要指定内容类型为html/text)
		if (response.getContentType() == null) {
			response.setContentType(getContentType());
		}

		jsonWriter.write(model, request, response);
	}

	protected RequestContext getRequestContext(Map model) {
		return (RequestContext) model.remove(getRequestContextAttribute());
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public JsonWriter getJsonWriter() {
		return jsonWriter;
	}

	public void setJsonWriter(JsonWriter jsonWriter) {
		this.jsonWriter = jsonWriter;
	}

}
