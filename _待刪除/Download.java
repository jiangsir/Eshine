/**
 * idv.jiangsir.objects - Task.java
 * 2008/2/19 下午 04:32:20
 * jiangsir
 */
package jiangsir.eshine.Objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import jiangsir.eshine.Utils.DataBase;

/**
 * @author jiangsir
 * 
 */
public class Download {
	private Integer id;
	private String account = "";
	private String ipfrom = "";
	private Date downloadtime = new Date();
	private String url = "";

	public void setAccount(String account) {
		this.account = account;
	}

	public void setDownloadtime(Date downloadtime) {
		this.downloadtime = downloadtime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIpfrom(String ipfrom) {
		this.ipfrom = ipfrom;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Download() {

	}

	public Download(int id) {
		String sql = "SELECT * FROM downloads WHERE id=" + id;
		ArrayList list = new DataBase().executeQuery(sql);
		if (list.size() == 0) {
			return;
		}
		this.init((HashMap) list.get(0));
	}

	public Download(HashMap map) {
		if (map == null) {
			return;
		}
		this.init(map);
	}

	private void init(HashMap map) {
		this.id = (Integer) map.get("id");
		this.account = (String) map.get("account");
		this.ipfrom = (String) map.get("ipfrom");
		this.downloadtime = (Date) map.get("downloadtime");
		this.url = (String) map.get("url");
	}

	public String getAccount() {
		return account;
	}

	public Date getDownloadtime() {
		return downloadtime;
	}

	public Integer getId() {
		return id;
	}

	public String getIpfrom() {
		return ipfrom;
	}

	public String getUrl() {
		return url;
	}

}
