package jiangsir.eshine.Objects;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
	private Timestamp postdate = new Timestamp(System.currentTimeMillis());
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

	public void setJobid(String jobid) {
		if (jobid == null || !jobid.matches("[0-9]+")) {
			return;
		}
		this.setJobid(Integer.parseInt(jobid));
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
		if (type == null)
			return;
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

	public Timestamp getPostdate() {
		return postdate;
	}

	public void setPostdate(Timestamp postdate) {
		this.postdate = postdate;
	}
	public void setPostdate(String postdate) {
		try {
			this.setPostdate(Timestamp.valueOf(postdate));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		if (email == null)
			return;
		this.email = email;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public void setGrade(String grade) {
		if (grade == null)
			return;
		try {
			this.setGrade(Integer.parseInt(grade));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
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
	public String getNianji() {

		int year = Calendar.getInstance().get(Calendar.YEAR);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(getPostdate().getTime()));
		int offset = year - cal.get(Calendar.YEAR);
		int grade = this.getGrade().intValue();
		String result = "";
		if (grade >= 7 && grade <= 9) {
			grade = grade - 6;
			if (grade + offset > 3) {
				result += "國中校友" + ((3 - grade) + year - 11) + " 級";
			} else {
				result += "國 " + (grade % 3 == 0 ? 3 : grade % 3);
			}
		} else if (grade >= 10 && grade <= 12) {
			grade = grade - 9;
			if (grade + offset > 3) {
				result += "高中校友" + ((3 - grade) + year - 11) + " 級";
			} else {
				result += "高 " + (grade % 3 == 0 ? 3 : grade % 3);
			}
		}
		return result;
	}

}
