package tw.jiangsir.Utils.Exceptions;

/**
 * @author jiangsir
 * 
 */
public class DataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataException(Cause cause) {
		super(cause.getTitle(), cause);
	}

	public DataException(String message) {
		super(message, new Cause(Cause.TYPE.EXCEPTION, message, DataException.class.getSimpleName()));
	}

	public DataException(String message, Cause cause) {
		super(message, cause);
	}

	public DataException(Throwable cause) {
		super(cause);
	}

}
