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

	public String getNianji() {

		// Calendar.get(Calendar.YEAR);
		int year = new Date().getYear();
		int offset = new Date().getYear()
				- this.article.getPostdate().getYear();
		int grade = this.article.getGrade().intValue();
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

	public String getMaxFilesize() {
		return (Uploader.MAX_FILESIZE / 1024 / 1024) + " MB";
	}

}
