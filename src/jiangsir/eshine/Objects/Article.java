package jiangsir.eshine.Objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jiangsir.eshine.DAOs.UpfileDAO;
import jiangsir.eshine.Utils.DataBase;
import jiangsir.eshine.Utils.ENV;

/**
 *  - User.java
 * 2008/4/29 下午 05:41:54
 * jiangsir
 */

/**
 * @author jiangsir
 * 
 */
public class Article {

	private Integer id = 0;
	private Integer jobid = 0;
	private String author = "";
	private String classname = "";
	private Integer grade = 0;
	private String title = "";
	private String type = "";
	private String email = "";
	private String comment = "";
	private String filename = "";
	private Long filesize = 0L;
	private String filetype = "";
	private Date postdate = new Date();
	public static final int visible_OPEN = 1;
	public static final int visible_HIDE = 0;
	private Integer visible = Article.visible_OPEN;

	public Article() {

	}

	public Article(Integer id) {
		String sql = "SELECT * FROM articles WHERE id=" + id;
		ArrayList<?> list = new DataBase().executeQuery(sql);
		if (list.size() == 0) {
			return;
		}
		this.init((HashMap<?, ?>) list.get(0));
	}

	public Article(HashMap<?, ?> map) {
		if (map == null) {
			return;
		}
		this.init(map);
	}

	private void init(Map<?, ?> map) {
		this.setId((Integer) map.get("id"));
		this.setJobid((Integer) map.get("jobid"));
		this.setType((String) map.get("type"));
		this.setAuthor((String) map.get("author"));
		this.setClassname((String) map.get("classname"));
		// this.setClassnum((Integer) map.get("classnum"));
		this.setGrade((Integer) map.get("grade"));
		this.setTitle((String) map.get("title"));
		this.setEmail((String) map.get("email"));
		this.setComment((String) map.get("comment"));
		this.setFilename((String) map.get("filename"));
		this.setFilesize((Long) map.get("filesize"));
		this.setFiletype((String) map.get("filetype"));
		this.setPostdate((Date) map.get("postdate"));
		this.setVisible((Integer) map.get("visible"));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getJobid() {
		return jobid;
	}

	public void setJobid(Integer jobid) {
		this.jobid = jobid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	// public Integer getClassnum() {
	// return classnum;
	// }
	//
	// public void setClassnum(Integer classnum) {
	// this.classnum = classnum;
	// }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		if (filetype == null) {
			return;
		}
		this.filetype = filetype;
	}

	public Date getPostdate() {
		return postdate;
	}

	public void setPostdate(Date postdate) {
		this.postdate = postdate;
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	// =====================================================================

	public String getINNER_PATH() {
		return ENV.APP_REAL_PATH + "upfiles"
				+ System.getProperty("file.separator");
	}

	public String getINNER_FILENAME() {
		// String sub = filename.substring(filename.lastIndexOf("."));
		Job job = new Job(this.getJobid());
		// return job.getNiandu() + "_" + this.getId() + "_" + this.getTitle()
		// + "_" + this.getFilename();
		return job.getNiandu()
				+ "_"
				+ this.getId()
				+ this.getFilename().substring(
						this.getFilename().lastIndexOf('.'));
	}

	public ArrayList<Upfile> getUpfiles() {
		return new UpfileDAO().getUpfiles(id);
	}
}
