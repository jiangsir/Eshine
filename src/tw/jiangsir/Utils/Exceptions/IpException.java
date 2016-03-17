package tw.jiangsir.Utils.Exceptions;

public class IpException extends RuntimeException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public IpException(String message) {
	super(message, new Cause(Cause.TYPE.EXCEPTION, message,
		IpException.class.getSimpleName()));
    }

    public IpException(Cause cause) {
	super(cause.getTitle(), cause);
    }

}
