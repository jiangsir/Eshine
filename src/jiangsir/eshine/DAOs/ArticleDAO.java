/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.eshine.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import jiangsir.eshine.Objects.Article;
import tw.jiangsir.Utils.DAO.SuperDAO;

/**
 * @author jiangsir
 * 
 */
public class ArticleDAO extends SuperDAO<Article> {

	/**
	 * 列出有效期限內的 article
	 * 
	 * @param by
	 * @param page
	 * @return
	 */
	public ArrayList<Article> getArticles(int jobid, String type) {
		String sqltype = (type == null || type.equals("")) ? "" : " AND type='" + type + "'";
		String sql = "SELECT * FROM articles WHERE visible=" + Article.visible_OPEN + " " + sqltype + "AND jobid="
				+ jobid + " ORDER BY id DESC";
		return this.executeQuery(sql, Article.class);
		// Iterator<?> it = new DataBexecuteQuery(sql).iterator();
		// while (it.hasNext()) {
		// articles.add(new Article((HashMap<?, ?>) it.next()));
		// }
		// return articles;
	}

	public Article getArticleById(int articleid) {
		String sql = "SELECT * FROM articles WHERE id=" + articleid;
		for (Article article : this.executeQuery(sql, Article.class)) {
			return article;
		}
		return new Article();
	}

	@Override
	public int insert(Article article) throws SQLException {
		String sql = "INSERT INTO articles (jobid, type, author, "
				+ "classname, grade, title, email, comment, filename, "
				+ "filesize, filetype, postdate, visible) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int articleid = 0;
		try {
			PreparedStatement pstmt = this.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
			pstmt.setTimestamp(12, new Timestamp(article.getPostdate().getTime()));
			pstmt.setInt(13, article.getVisible());
			articleid = this.executeInsert(pstmt);
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articleid;
	}

	@Override
	public int update(Article article) {
		return 0;
	}

	/**
	 * 將文章設成隱藏, 並不真的刪除
	 * 
	 * @param articleid
	 * @return
	 */
	@Override
	protected boolean delete(long articleid) throws SQLException {
		String sql = "UPDATE articles SET visible=" + Article.visible_HIDE + " WHERE id=" + articleid;
		return this.execute(sql);
	}

}
