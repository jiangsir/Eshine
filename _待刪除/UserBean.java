/**
 * idv.jiangsir.Beans - CourseBean.java
 * 2009/12/20 下午5:07:14
 * nknush-001
 */
package jiangsir.eshine.Beans;

import java.util.ArrayList;

import jiangsir.eshine.DAOs.UserDAO;
import jiangsir.eshine.Objects.*;


/**
 * @author nknush-001
 * 
 */
public class UserBean {
	private User user;
	private String account;

	public UserBean() {
	}

	public UserBean(String account) {
		this.account = account;
	}

	public void setAccount(String account) {
		this.account = account;
		this.user = new User(account);
	}

	// ===================================================================================

	public User getUser() {
		if (this.user == null) {
			return new User(this.account);
		}
		return this.user;
	}

	public ArrayList<User> getUsers() {
		return new UserDAO().getUsersByVisibleTrue();
	}
}
