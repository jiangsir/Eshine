package tw.jiangsir.Utils.Exceptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import jiangsir.eshine.Objects.OnlineUser;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import tw.jiangsir.Utils.Annotations.Persistent;

public class Cause extends Throwable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public static enum TYPE {
	INFO, // 處理一些一般訊息的呈現。
	WARNING, // 顯示使用者填報錯誤，或違規行為。
	EXCEPTION, // 系統丟出的例外。
	SQL_EXCEPTION, // 系統丟出 sql exception,包裝成自定的 exception
	ERROR // 顯示一些錯誤訊息。
    };

    @Persistent(name = "type")
    private TYPE type = TYPE.INFO;
    @Persistent(name = "title")
    private String title = "";
    @Persistent(name = "subtitle")
    private String subtitle = "";
    @Persistent(name = "content")
    private String content = "";
    @Persistent(name = "debugs")
    private HashSet<String> debugs = new HashSet<String>();
    @Persistent(name = "contentlist")
    private ArrayList<String> contentlist = new ArrayList<String>();
    @Persistent(name = "uris")
    private HashMap<String, URI> uris = new HashMap<String, URI>();
    @Persistent(name = "onlineuser")
    private OnlineUser onlineUser = null;

    public Cause(TYPE type, String title, String subtitle) {
	this.setType(type);
	this.setTitle(title);
	this.setSubtitle(subtitle);
    }

    public Cause(String title, Throwable throwable) {
	this.setType(TYPE.EXCEPTION);
	this.setTitle(title);
	this.setSubtitle(throwable.getClass().getName());
	this.setStackTrace(throwable.getStackTrace());
    }

    public Cause(Throwable throwable) {
	this.setType(TYPE.EXCEPTION);
	if (throwable instanceof JsonParseException) {
	    this.setTitle("JSON 資料格式不符合！(" + throwable.getLocalizedMessage()
		    + ")");
	} else if (throwable instanceof JsonMappingException) {
	    this.setTitle("JSON 欄位對應不正確！(" + throwable.getLocalizedMessage()
		    + ")");
	} else if (throwable instanceof MalformedURLException) {
	    this.setTitle("URL 位址有誤！(" + throwable.getLocalizedMessage() + ")");
	} else {
	    this.setTitle(throwable.getLocalizedMessage());
	}
	this.setSubtitle(throwable.getClass().getName());
	this.setStackTrace(throwable.getStackTrace());
    }

    public TYPE getType() {
	return type;
    }

    public void setType(TYPE type) {
	this.type = type;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getSubtitle() {
	return subtitle;
    }

    public void setSubtitle(String subtitle) {
	this.subtitle = subtitle;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public HashSet<String> getDebugs() {
	return debugs;
    }

    public void setDebugs(HashSet<String> debugs) {
	this.debugs = debugs;
    }

    public ArrayList<String> getContentlist() {
	return contentlist;
    }

    public void setContentlist(ArrayList<String> contentlist) {
	this.contentlist = contentlist;
    }

    public HashMap<String, URI> getUris() {
	return uris;
    }

    public void setUris(HashMap<String, URI> uris) {
	this.uris = uris;
    }

    public OnlineUser getOnlineUser() {
	return onlineUser;
    }

    public void setOnlineUser(OnlineUser onlineUser) {
	this.onlineUser = onlineUser;
    }

}
