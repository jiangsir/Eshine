package jiangsir.eshine.Objects;

import java.util.ArrayList;
import java.util.Date;
import jiangsir.eshine.DAOs.JobDAO;
import jiangsir.eshine.DAOs.UpfileDAO;
import jiangsir.eshine.Utils.ENV;
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
public class Article {

	@Persistent(name = "id")
	private Integer id = 0;
	@Persistent(name = "jobid")
	private Integer jobid = 0;
	@Persistent(name = "author")
	private String author = "";
	@Persistent(name = "classname")
	private String classname = "";
	@Persistent(name = "grade")
	private Integer grade = 0;
	@Persistent(name = "title")
	private String title = "";
	@Persistent(name = "type")
	private String type = "";
	@Persistent(name = "email")
	private String email = "";
	@Persistent(name = "comment")
	private String comment = "";
	@Persistent(name = "filename")
	private String filename = "";
	@Persistent(name = "filesize")
	private Long filesize = 0L;
	@Persistent(name = "filetype")
	private String filetype = "";
	@Persistent(name = "postdate")
	private Date postdate = new Date();
	public static final int visible_OPEN = 1;
	public static final int visible_HIDE = 0;
	@Persistent(name = "visible")
	private Integer visible = Article.visible_OPEN;

	public Article() {

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
		if (author == null)
			return;
		this.author = author;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		if (classname == null)
			return;
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
		if (title == null)
			return;
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
		if (comment == null)
			return;
		this.comment = comment;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		if (filename == null)
			return;
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
		return ENV.APP_REAL_PATH + "upfiles" + System.getProperty("file.separator");
	}

	public String getINNER_FILENAME() {
		// String sub = filename.substring(filename.lastIndexOf("."));
		Job job = new JobDAO().getJobById(this.getJobid());
		// return job.getNiandu() + "_" + this.getId() + "_" + this.getTitle()
		// + "_" + this.getFilename();
		return job.getNiandu() + "_" + this.getId() + this.getFilename().substring(this.getFilename().lastIndexOf('.'));
	}

	public ArrayList<Upfile> getUpfiles() {
		return new UpfileDAO().getUpfiles(id);
	}

	public String getAuthor_replaced() {
		String author = this.getAuthor();
		if (author.length() > 1)
			return author.replace(author.charAt(1), 'O');
		return author;
	}
}
