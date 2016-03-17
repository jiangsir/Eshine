/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.eshine.DAOs;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import jiangsir.eshine.Objects.Article;
import jiangsir.eshine.Utils.DataBase;

/**
 * @author jiangsir
 * 
 */
public class ArticleDAO extends GeneralDAO<Article> {

	public int insert_PSTMT(Article article) {
		String sql = "INSERT INTO articles (jobid, type, author, "
				+ "classname, grade, title, email, comment, filename, "
				+ "filesize, filetype, postdate, visible) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int articleid = 0;
		try {
			PreparedStatement pstmt = new DataBase().getConnection()
					.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, article.getJobid());
			pstmt.setString(2, article.getType());
			pstmt.setString(3, article.getAuthor());
			pstmt.setString(4, article.getClassname());
			// pstmt.setInt(5, article.getClassnum());
			pstmt.setInt(5, article.getGrade());
			pstmt.setString(6, article.getTitle());
			pstmt.setString(7, article.getEmail());
			pstmt.setString(8, article.getComment());
			pstmt.setString(9, article.getFilename());
			pstmt.setLong(10, article.getFilesize());
			pstmt.setString(11, article.getFiletype());
			pstmt.setTimestamp(12, new Timestamp(article.getPostdate()
					.getTime()));
			pstmt.setInt(13, article.getVisible());
			new DataBase().executeUpdate(pstmt, sql);
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			articleid = rs.getInt(1);
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articleid;
	}

	/**
	 * 將文章設成隱藏, 並不真的刪除
	 * 
	 * @param articleid
	 * @return
	 */
	public boolean delete(int articleid) {
		String sql = "UPDATE articles SET visible=" + Article.visible_HIDE
				+ " WHERE id=" + articleid;
		return new DataBase().execute(sql);
	}

	/**
	 * 列出有效期限內的 article
	 * 
	 * @param by
	 * @param page
	 * @return
	 */
	public ArrayList<Article> getArticles(int jobid, String type) {
		String sqltype = (type == null || type.equals("")) ? "" : " AND type='"
				+ type + "'";
		ArrayList<Article> articles = new ArrayList<Article>();
		String sql = "SELECT * FROM articles WHERE visible="
				+ Article.visible_OPEN + " " + sqltype + "AND jobid=" + jobid
				+ " ORDER BY id DESC";
		Iterator<?> it = new DataBase().executeQuery(sql).iterator();
		while (it.hasNext()) {
			articles.add(new Article((HashMap<?, ?>) it.next()));
		}
		return articles;
	}

	public Article getArticle(int articleid) {
		for (Article article : this.executeQuery(
				"SELECT * FROM articles WHERE id=" + articleid, Article.class)) {
			return article;
		}
		return null;
	}

	@Override
	public int insert(Article t) throws SQLException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Article t) throws SQLException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
