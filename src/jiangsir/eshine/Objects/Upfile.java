package jiangsir.eshine.Objects;

import java.io.InputStream;
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
public class Upfile {

	private Integer id = 0;
	private Integer articleid = 0;
	private String filename = "";
	private Long filesize = 0L;
	private String filetype = "";
	private InputStream binary;
	private Integer hitnum = 0;
	public static final Integer visible_OPEN = 1;
	public static final Integer visible_HIDE = 0;
	private Integer visible = visible_OPEN;

	// =================================

	// 指定 zerobb 3.0 by JSP 之前的 upfileid, 必須要用不同的方式存取
	public static final int OLD_UPFILDID = 5980;
	// 在 下面這個 upfileid 之後，zerobb 的附加檔案改用 blob 來儲存
	public static final int FILE_UPFILDID = 10323;

	public Upfile() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticleid() {
		return articleid;
	}

	public void setArticleid(Integer articleid) {
		this.articleid = articleid;
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
		this.filetype = filetype;
	}

	public Integer getHitnum() {
		return hitnum;
	}

	public void setHitnum(Integer hitnum) {
		this.hitnum = hitnum;
	}

	public InputStream getBinary() {
		return binary;
	}

	public void setBinary(InputStream binary) {
		this.binary = binary;
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	public String getINNER_PATH() {
		return ENV.APP_REAL_PATH + "upfiles"
				+ System.getProperty("file.separator");
	}

	// public String getINNER_FILENAME() {
	// String sub = filename.substring(filename.lastIndexOf("."));
	// return this.getArticleid() + "_" + this.getId() + "_"
	// + new ArticleDAO().getArticle(this.getArticleid()).getAccount()
	// + sub;
	// }

	// =====================================================================

}
