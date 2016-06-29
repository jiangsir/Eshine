/**
 * jiangsir.zerobb.Interfaces - IAccessible.java
 * 2012/4/17 下午4:28:49
 * nknush-001
 */
package jiangsir.eshine.Utils.Filters;

import javax.servlet.http.HttpServletRequest;

import tw.jiangsir.Utils.Exceptions.AccessException;

/**
 * @author nknush-001
 * 
 */
public interface IAccessFilter {
	public void AccessFilter(HttpServletRequest request) throws AccessException;
}
