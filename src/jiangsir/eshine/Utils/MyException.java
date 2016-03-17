/**
 * idv.jiangsir.utils - ZjException.java
 * 2010/9/15 下午1:30:49
 * nknush-001
 */
package jiangsir.eshine.Utils;

/**
 * @author nknush-001
 * 
 */
public class MyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4800234202163628817L;

	public MyException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param string
	 */
	public MyException(String string) {
		super(string);
	}

}
