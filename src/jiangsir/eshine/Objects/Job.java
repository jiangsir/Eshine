package jiangsir.eshine.Objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jiangsir.eshine.Utils.DataBase;

/**
 *  - User.java
 * 2008/4/29 下午 05:41:54
 * jiangsir
 */

/**
 * @author jiangsir
 * 
 */
public class Job {

	private Integer id = 0;
	private Integer niandu = 0;
	private String title = "";
	private String comment = "";
	private Date starttime = new Date();
	private Date finishtime = new Date();

	public Job() {

	}

	public Job(Integer id) {
		String sql = "SELECT * FROM jobs WHERE id=" + id;
		ArrayList<?> list = new DataBase().executeQuery(sql);
		if (list.size() == 0) {
			return;
		}
		this.init((HashMap<?, ?>) list.get(0));
	}

	public Job(HashMap<?, ?> map) {
		if (map == null) {
			return;
		}
		this.init(map);
	}

	private void init(Map<?, ?> map) {
		this.setId((Integer) map.get("id"));
		this.setNiandu((Integer) map.get("niandu"));
		this.setTitle((String) map.get("title"));
		this.setComment((String) map.get("comment"));
		this.setStarttime((Date) map.get("starttime"));
		this.setFinishtime((Date) map.get("finishtime"));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// =====================================================================

	public Integer getNiandu() {
		return niandu;
	}

	public void setNiandu(Integer niandu) {
		this.niandu = niandu;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment == null ? "" : comment;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}

	/**
	 * 顯示有效期限還有多久
	 */
	public String getCountdown() {
		long countdown = this.getFinishtime().getTime() - new Date().getTime();
		long min = (countdown - (countdown % 60000)) / 60000;
		long hour = (min - (min % 60)) / 60;
		long day = (hour - (hour % 24)) / 24;
		String result = "";
		if (day > 0) {
			result += day + "天";
		}
		if (hour > 0) {
			result += hour % 24 + "小時";
		}
		if (min > 0) {
			result += min % 60 + "分";
		}
		return result;
	}

}
