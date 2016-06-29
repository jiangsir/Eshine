package tw.jiangsir.Utils.Exceptions;

public class RoleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoleException(String message) {
		super(message, new Cause(Cause.TYPE.ERROR, message, RoleException.class.getSimpleName()));
	}

	public RoleException(Cause cause) {
		super(cause.getTitle() + ", Debugs:" + cause.getDebugs(), cause);
	}

}
