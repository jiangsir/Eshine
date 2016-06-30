package jiangsir.eshine.Objects;

import java.sql.Timestamp;
import java.util.Date;
import tw.jiangsir.Utils.Annotations.Persistent;

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

	@Persistent(name = "id")
	private Integer id = 0;
	@Persistent(name = "niandu")
	private Integer niandu = 0;
	@Persistent(name = "title")
	private String title = "";
	@Persistent(name = "comment")
	private String comment = "";
	@Persistent(name = "starttime")
	private Timestamp starttime = new Timestamp(System.currentTimeMillis());
	@Persistent(name = "finishtime")
	private Timestamp finishtime = new Timestamp(System.currentTimeMillis());

	public Job() {

	}

	// public Job(Integer id) {
	// String sql = "SELECT * FROM jobs WHERE id=" + id;
	// ArrayList<?> list = new DataBase().executeQuery(sql);
	// if (list.size() == 0) {
	// return;
	// }
	// this.init((HashMap<?, ?>) list.get(0));
	// }

	// public Job(HashMap<?, ?> map) {
	// if (map == null) {
	// return;
	// }
	// this.init(map);
	// }
	//
	// private void init(Map<?, ?> map) {
	// this.setId((Integer) map.get("id"));
	// this.setNiandu((Integer) map.get("niandu"));
	// this.setTitle((String) map.get("title"));
	// this.setComment((String) map.get("comment"));
	// this.setStarttime((Date) map.get("starttime"));
	// this.setFinishtime((Date) map.get("finishtime"));
	// }

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

	public void setNiandu(String niandu) {
		try {
			this.setNiandu(Integer.parseInt(niandu));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title == null)
			return;
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment == null ? "" : comment;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public void setFinishtime(Timestamp finishtime) {
		this.finishtime = finishtime;
	}

	public void setStarttime(String starttime) {
		if (starttime == null) {
			return;
		}
		this.setStarttime(Timestamp.valueOf(starttime));
	}

	public Timestamp getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(String finishtime) {
		if (finishtime == null) {
			return;
		}
		this.setFinishtime(Timestamp.valueOf(finishtime));
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
