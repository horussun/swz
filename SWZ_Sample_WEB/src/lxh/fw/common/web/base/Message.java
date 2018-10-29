package lxh.fw.common.web.base;

import java.io.Serializable;

import com.ibm.json.java.JSONObject;

/**
 * 消息类。用来向前台页面或ajax传递提示或错误信息
 * 
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1965252183079780684L;

	// 提示信息
	public static final int LEVEL_INFO = 1;

	// 警告信息
	public static final int LEVEL_WARN = 3;

	// 错误信息
	public static final int LEVEL_ERROR = 5;

	// 消息的级别
	private int level = LEVEL_INFO;

	// 消息的内容
	private String text;

	// 关联的异常堆栈信息
	private Throwable stack;

	public Message() {
	}

	public Message(int level, String text) {
		this.level = level;
		this.text = text;
	}

	public static Message INFO(String text) {
		return new Message(LEVEL_INFO, text);
	}

	public static Message WARN(String text) {
		return new Message(LEVEL_WARN, text);
	}

	public static Message ERROR(String text, Throwable stack) {
		Message msg = new Message(LEVEL_ERROR, text);
		msg.setStack(stack);
		return msg;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("level", getLevel());
		json.put("text", getText());
		if (getStack() != null) {
			StringBuffer str = new StringBuffer();

			str.append(getStack().toString());
			for (StackTraceElement trace : getStack().getStackTrace()) {
				str.append("\n\t " + trace);
			}
			json.put("stack", str.toString());
		}
		return json;
	}

	/** get,set */
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Throwable getStack() {
		return stack;
	}

	public void setStack(Throwable stack) {
		this.stack = stack;
	}

}
