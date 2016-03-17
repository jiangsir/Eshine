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
import java.util.HashMap;
import java.util.Iterator;

import jiangsir.eshine.Objects.Job;
import jiangsir.eshine.Utils.DataBase;
import jiangsir.eshine.Utils.ENV;
import jiangsir.eshine.Utils.Utils;

/**
 * @author jiangsir
 * 
 */
public class JobDAO {

	/**
	 * 處理 pstmt 的 executeQuery 並計算時間
	 * 
	 * @param pstmt
	 * @param SQL
	 * @return
	 * @throws SQLException
	 */
	private ResultSet executeQuery(PreparedStatement pstmt, String SQL) {
		long starttime = System.currentTimeMillis();
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			new ExceptionDAO().insert_PSTMT("DataBase.java", "unknown",
					"unknown IP", e.getLocalizedMessage(), Utils
							.printStackTrace(e, SQL));
			e.printStackTrace();
		}
		System.out.println(ENV.logHeader() + "PSTMT_SQL=" + SQL + " 共耗時 "
				+ (System.currentTimeMillis() - starttime) + " ms");
		return rs;
	}

	private int executeUpdate(PreparedStatement pstmt) {
		long starttime = System.currentTimeMillis();
		int result = -1;
		try {
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			new ExceptionDAO().insert_PSTMT("DataBase.java", "unknown",
					"unknown IP", e.getLocalizedMessage(), Utils
							.printStackTrace(e, pstmt.toString()));
			e.printStackTrace();
		}
		System.out.println(ENV.logHeader() + "PSTMT=" + pstmt.toString()
				+ " 共耗時 " + (System.currentTimeMillis() - starttime) + " ms");
		return result;
	}

	public Integer insert_PSTMT(Job job) {
		String sql = "INSERT INTO `jobs` (niandu, title, comment, starttime, "
				+ "finishtime) VALUES (?,?,?,?,?);";
		int jobid = 0;
		try {
			PreparedStatement pstmt = new DataBase().getConnection()
					.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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

	public int update_PSTMT(Job job) {
		String sql = "UPDATE `jobs` SET niandu=?, title=?, comment=?, starttime=?, "
				+ "finishtime=? WHERE id=" + job.getId() + ";";
		int result = -1;
		try {
			PreparedStatement pstmt = new DataBase().getConnection()
					.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, job.getNiandu());
			pstmt.setString(2, job.getTitle());
			pstmt.setString(3, job.getComment());
			pstmt.setTimestamp(4, new Timestamp(job.getStarttime().getTime()));
			pstmt.setTimestamp(5, new Timestamp(job.getFinishtime().getTime()));
			result = this.executeUpdate(pstmt);
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int jobid) {
		String sql = "DELETE FROM `jobs` WHERE id=" + jobid + ";";
		int result = -1;
		try {
			PreparedStatement pstmt = new DataBase().getConnection()
					.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			result = this.executeUpdate(pstmt);
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public Integer insert_OLD(Job job) {
		String sql = "INSERT INTO `jobs` (niandu, title, comment, starttime, "
				+ "finishtime) VALUES (" + job.getNiandu() + ", '"
				+ job.getTitle() + "','" + job.getComment() + "','"
				+ Utils.parseDatetime(job.getStarttime().getTime()) + "','"
				+ Utils.parseDatetime(job.getFinishtime().getTime()) + "');";
		return new DataBase().executeInsert(sql);
	}

	/**
	 * 每次只允許一個 job 接受投稿。理論上，經由 starttime, finishtime 就可以確保這件事。<br>
	 * 但仍不可以同時出現兩個 job, 因此只取一個。<br>
	 * 原則：如果兩個 job 時間上發生重疊，則取後到者，也就是 id 大的。
	 */
	public Job getActiveJob() {
		String sql = "SELECT * FROM jobs WHERE starttime<'"
				+ Utils.parseDatetime(new Date().getTime())
				+ "' AND finishtime>'"
				+ Utils.parseDatetime(new Date().getTime())
				+ "' ORDER BY id DESC LIMIT 1";
		Iterator<?> it = new DataBase().executeQuery(sql).iterator();
		if (it.hasNext()) {
			return new Job((HashMap<?, ?>) it.next());
		} else {
			return new Job();
		}
	}

	/**
	 * 如果沒有任何 job 在進行的話，就秀出最有一個 job 的投稿內容，供事後查看
	 * 
	 * @return
	 */
	public Job getLatestJob() {
		String sql = "SELECT * FROM jobs ORDER BY id DESC LIMIT 1";
		Iterator<?> it = new DataBase().executeQuery(sql).iterator();
		if (it.hasNext()) {
			return new Job((HashMap<?, ?>) it.next());
		} else {
			return new Job();
		}
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public Job getJob(int jobid) {
		String sql = "SELECT * FROM jobs WHERE id=" + jobid;
		Iterator<?> it = new DataBase().executeQuery(sql).iterator();
		if (it.hasNext()) {
			return new Job((HashMap<?, ?>) it.next());
		} else {
			return new Job();
		}
	}

	/**
	 * 讀取已結束的 jobs
	 * 
	 * @return
	 */
	public ArrayList<Job> getFinishedJobs() {
		ArrayList<Job> jobs = new ArrayList<Job>();
		String sql = "SELECT * FROM jobs WHERE finishtime<'"
				+ Utils.parseDatetime(new Date().getTime())
				+ "' ORDER BY id DESC";
		Iterator<?> it = new DataBase().executeQuery(sql).iterator();
		while (it.hasNext()) {
			jobs.add(new Job((HashMap<?, ?>) it.next()));
		}
		return jobs;
	}

	/**
	 * 讀取所有 jobs
	 * 
	 * @return
	 */
	public ArrayList<Job> getJobs() {
		ArrayList<Job> jobs = new ArrayList<Job>();
		String sql = "SELECT * FROM jobs ORDER BY id DESC";
		Iterator<?> it = new DataBase().executeQuery(sql).iterator();
		while (it.hasNext()) {
			jobs.add(new Job((HashMap<?, ?>) it.next()));
		}
		return jobs;
	}

}
