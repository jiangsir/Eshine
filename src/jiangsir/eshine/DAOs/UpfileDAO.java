/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.eshine.DAOs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jiangsir.eshine.Objects.Upfile;

/**
 * @author jiangsir
 * 
 */
public class UpfileDAO extends GeneralDAO<Upfile> {

	public synchronized int insert(Upfile upfile) throws SQLException,
			IOException {
		// code = utils.intoSQL(code);
		// errmsg 要預先給一個 "" 否則無法寫入資料庫
		String sql = "INSERT INTO upfiles (articleid, "
				+ "filename, filesize, filetype, `binary`, "
				+ "hitnum, visible) VALUES" + "(?,?,?,?,?,?,?)";
		int id = 0;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setInt(1, upfile.getArticleid());
		pstmt.setString(2, upfile.getFilename());
		pstmt.setLong(3, upfile.getFilesize());
		pstmt.setString(4, upfile.getFiletype());
		// InputStream is = new InputStream();

		pstmt.setBinaryStream(5, upfile.getBinary(), upfile.getBinary()
				.available());
		// pstmt.setBinaryStream(7, new FileInputStream(upfile.getBinary()),
		// (int) upfile.getBinary().length());
		pstmt.setInt(6, upfile.getHitnum());
		pstmt.setInt(7, upfile.getVisible());
		// this.executeUpdate(pstmt, sql);
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		rs.next();
		id = rs.getInt(1);
		rs.close();
		pstmt.close();
		upfile.getBinary().close();
		return id;
	}

	// public ArrayList<Upfile> executeQuery(String sql) {
	// return this.executeQuery(sql, Upfile.class);
	// }


	public boolean delete(int upfileid) {
		String sql = "UPDATE `upfiles` SET visible=0 WHERE id=" + upfileid
				+ ";";
		return this.execute(sql);
	}

	public synchronized boolean updateHitnum(int upfileid) {
		String sql = "UPDATE upfiles SET hitnum=hitnum+1 WHERE id=" + upfileid;
		return this.execute(sql);
	}

	public Upfile getUpfile(int upfileid) {
		String sql = "SELECT * FROM upfiles WHERE id=" + upfileid;
		for (Upfile upfile : this.executeQuery(sql, Upfile.class)) {
			return upfile;
		}
		return new Upfile();
	}

	public ArrayList<Upfile> getUpfiles(int articleid) {
		// 可以解決要 InsertArticle 卻會有莫名的檔案出現在 “上傳檔案” 的地方。
		// 原因就是有些 upfile 的 articleid 不知什麼原因變成 0，所以在這裡被讀出來了。
		// 現 將 ArticleDAO 跟 upfileDAO 都加上 synchronized 看看能否改善。
		// 所以暫時還是讓它出現。比較容易觀察。
		if (articleid <= 0) {
			return new ArrayList<Upfile>();
		}
		// System.out.println("getupfile, articleid=" + articleid);
		String sql = "SELECT * FROM upfiles WHERE visible="
				+ Upfile.visible_OPEN + " AND articleid=" + articleid;
		// Iterator<?> it = new BaseDAO().executeQuery(sql, Upfile.class)
		// .iterator();
		return this.executeQuery(sql, Upfile.class);
	}

	public InputStream getUpfileInputStream(int id) {
		String sql = "SELECT `binary` FROM upfiles WHERE id=" + id;
		InputStream is = null;
		ResultSet rs;
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// upfile.setBinary(rs.getBlob(8).getBinaryStream());
				is = rs.getBlob(1).getBinaryStream();
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return is;
	}

	// /**
	// * 取得擁有這個 upfile 的文章。
	// *
	// * @param upfileid
	// * @return
	// * @throws DataException
	// */
	// public Article getArticle(User session_user, int upfileid)
	// throws DataException {
	// Upfile upfile = this.getUpfile(upfileid);
	// return new ArticleDAO().getArticle(session_user, upfile.getArticleid());
	// }

	@Override
	public int update(Upfile t) {
		// TODO Auto-generated method stub
		return 0;
	}
}
