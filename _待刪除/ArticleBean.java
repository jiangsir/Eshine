/**
 * idv.jiangsir.Beans - CourseBean.java
 * 2009/12/20 下午5:07:14
 * nknush-001
 */
package jiangsir.eshine.Beans;

import java.util.Calendar;
import java.util.Date;

import jiangsir.eshine.Objects.*;
import jiangsir.eshine.Utils.Uploader;

/**
 * @author nknush-001
 * 
 */
public class ArticleBean {
	private Article article;
	private int id;

	public ArticleBean() {
	}

	public ArticleBean(int id) {
		this.id = id;
	}

	public void setId(int id) {
		this.id = id;
		this.article = new Article(id);
	}

	// ===================================================================================

	public Article getArticle() {
		if (this.article == null) {
			return new Article(this.id);
		}
		return this.article;
	}

	public String getMaxFilesize() {
		return (Uploader.MAX_FILESIZE / 1024 / 1024) + " MB";
	}

}
