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
import java.util.Date;
import jiangsir.eshine.Objects.Job;
import jiangsir.eshine.Utils.DataBase;
import jiangsir.eshine.Utils.Utils;
import tw.jiangsir.Utils.DAO.SuperDAO;

/**
 * @author jiangsir
 * 
 */
public class JobDAO extends SuperDAO<Job> {

	/**
	 * 每次只允許一個 job 接受投稿。理論上，經由 starttime, finishtime 就可以確保這件事。<br>
	 * 但仍不可以同時出現兩個 job, 因此只取一個。<br>
	 * 原則：如果兩個 job 時間上發生重疊，則取後到者，也就是 id 大的。
	 */
	public Job getActiveJob() {
		String sql = "SELECT * FROM jobs WHERE starttime<'" + Utils.parseDatetime(new Date().getTime())
				+ "' AND finishtime>'" + Utils.parseDatetime(new Date().getTime()) + "' ORDER BY id DESC LIMIT 1";
		for (Job job : this.executeQuery(sql, Job.class)) {
			return job;
		}
		return new Job();
	}

	/**
	 * 如果沒有任何 job 在進行的話，就秀出最有一個 job 的投稿內容，供事後查看
	 * 
	 * @return
	 */
	public Job getLatestJob() {
		String sql = "SELECT * FROM jobs ORDER BY id DESC LIMIT 1";
		for (Job job : this.executeQuery(sql, Job.class)) {
			return job;
		}
		return new Job();
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public Job getJobById(int jobid) {
		String sql = "SELECT * FROM jobs WHERE id=" + jobid;
		for (Job job : this.executeQuery(sql, Job.class)) {
			return job;
		}
		return new Job();
	}

	/**
	 * 讀取已結束的 jobs
	 * 
	 * @return
	 */
	public ArrayList<Job> getFinishedJobs() {
		String sql = "SELECT * FROM jobs WHERE finishtime<'" + Utils.parseDatetime(new Date().getTime())
				+ "' ORDER BY id DESC";
		return this.executeQuery(sql, Job.class);
	}

	/**
	 * 讀取所有 jobs
	 * 
	 * @return
	 */
	public ArrayList<Job> getJobs() {
		String sql = "SELECT * FROM jobs ORDER BY id DESC";
		return this.executeQuery(sql, Job.class);
	}

	@Override
	public int insert(Job job) throws SQLException {
		String sql = "INSERT INTO `jobs` (niandu, title, comment, starttime, finishtime) VALUES (?,?,?,?,?);";
		int jobid = 0;
		try {
			PreparedStatement pstmt = new DataBase().getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, job.getNiandu());
			pstmt.setString(2, job.getTitle());
			pstmt.setString(3, job.getComment());
			pstmt.setTimestamp(4, new Timestamp(job.getStarttime().getTime()));
			pstmt.setTimestamp(5, new Timestamp(job.getFinishtime().getTime()));
			this.executeUpdate(pstmt);
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			jobid = rs.getInt(1);
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobid;
	}

	@Override
	public int update(Job job) throws SQLException {
		String sql = "UPDATE `jobs` SET niandu=?, title=?, comment=?, starttime=?, " + "finishtime=? WHERE id="
				+ job.getId() + ";";
		int result = -1;
		try {
			PreparedStatement pstmt = new DataBase().getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, job.getNiandu());
			pstmt.setString(2, job.getTitle());
			pstmt.setString(3, job.getComment());
			pstmt.setTimestamp(4, new Timestamp(job.getStarttime().getTime()));
			pstmt.setTimestamp(5, new Timestamp(job.getFinishtime().getTime()));
			result = this.executeUpdate(pstmt);
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean delete(long jobid) throws SQLException {
		String sql = "DELETE FROM `jobs` WHERE id=" + jobid + ";";
		boolean result = false;
		try {
			PreparedStatement pstmt = new DataBase().getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			result = this.executeDelete(pstmt);
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
