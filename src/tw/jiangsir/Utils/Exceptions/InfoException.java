package tw.jiangsir.Utils.Exceptions;

public class InfoException extends RuntimeException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public InfoException(Cause cause) {
	super(cause.getTitle(), cause);
    }

    public InfoException(String message) {
	super(message, new Cause(Cause.TYPE.INFO, message,
		InfoException.class.getSimpleName()));
    }

}
