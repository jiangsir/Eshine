/**
 * idv.jiangsir.Exceptions - GeneralCause.java
 * 2011/8/13 下午2:52:00
 * nknush-001
 */
package tw.jiangsir.Utils.Exceptions;

import jiangsir.eshine.Objects.OnlineUser;

/**
 * @author nknush-001
 * 
 */
public class AccessCause extends Throwable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// public static final String TYPE_ALERT = "ALERT";
	// public static final String TYPE_INFORMATION = "INFORMATION";
	// public static final String TYPE_WARNING = "WARNING";
	// public static final String TYPE_ERROR = "ERROR";
	// public static final String TYPE_SEVERE = "SEVERE";

	public static enum TYPE {
		ALERT, INFORMATION, WARNING, ERROR, SEVERE
	}

	private TYPE type = TYPE.WARNING;
	private OnlineUser onlineUser;
	private String resource_message = "";
	private String text_message = "";
	private String debug_message = "";

	public AccessCause(TYPE type, OnlineUser onlineUser, String debug) {
		this.setType(type);
		this.setOnlineUser(onlineUser);
		this.setDebug_message(debug);
	}

	@Override
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}

	public OnlineUser getOnlineUser() {
		return onlineUser;
	}

	public void setOnlineUser(OnlineUser onlineUser) {
		this.onlineUser = onlineUser;
	}

	public String getResource_message() {
		return resource_message;
	}

	public void setResource_message(String resourceMessage) {
		resource_message = resourceMessage;
	}

	public String getText_message() {
		return text_message;
	}

	public void setText_message(String textMessage) {
		text_message = textMessage;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public String getDebug_message() {
		return debug_message;
	}

	public void setDebug_message(String debugMessage) {
		debug_message = debugMessage;
	}

}
