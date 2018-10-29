package lxh.fw.common.web.view.json;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxh.fw.common.web.base.BaseController;
import lxh.fw.common.web.base.Message;

import com.ibm.json.java.JSONObject;

/**
 * 采用IBM json4j输出json流
 * 
 */
public class IBMJsonWriter implements JsonWriter {

	public static int HTTP_ERROR_STATUS = 500; // 内部错误的异常代码

	public void write(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (model == null || model.isEmpty()) {
			return;
		}
		String result;

		if (model.containsKey(BaseController.PARAM_MESSAGE)) { // 出现消息对象
			Message msg = (Message) model.get(BaseController.PARAM_MESSAGE);
			//如果包含级别为错误的消息，设置HTTP响应为异常
			if (msg.getLevel() >= Message.LEVEL_ERROR) {
				response.setStatus(HTTP_ERROR_STATUS);
			}
			model.remove(BaseController.PARAM_MESSAGE);
			JSONObject json = model.isEmpty()? new JSONObject() : toJSONObject(model);
			json.put(BaseController.PARAM_MESSAGE, msg.toJSON());
			result = json.toString();
		} else if (model.containsKey(JsonView.DIRECT_OUT_NAME)) {
			result = model.get(JsonView.DIRECT_OUT_NAME).toString();
		} else {
			result = toJSONObject(model).toString();
		}
		response.getWriter().write(result);
		return;

	}

	/**
	 * 将Map对象转换为JSON
	 * 
	 * @param data
	 * @return
	 */
	private JSONObject toJSONObject(Map data) {
		JSONObject object = new JSONObject();
		for (Object key : data.keySet()) {
			object.put(key, data.get(key));
		}

		return object;
	}

}
