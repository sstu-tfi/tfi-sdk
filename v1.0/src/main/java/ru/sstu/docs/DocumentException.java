package ru.sstu.docs;

/**
 * <code>DocumentException</code> class represents common exception class for
 * TFI Common Documents library.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
public class DocumentException extends Exception {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 2676924059825832875L;

	/**
	 * Constructs a new exception with the specified detail message.  The
	 * cause is not initialized, and may subsequently be initialized by
	 * a call to {@link #initCause(Throwable)}.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public DocumentException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which
	 * typically contains the class and detail message of <tt>cause</tt>).
	 * This constructor is useful for exceptions that are little more than
	 * wrappers for other throwables (for example, {@link
	 * java.security.PrivilegedActionException}).
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 *              {@link #getCause()} method).  (A <tt>null</tt> value is
	 *              permitted, and indicates that the cause is nonexistent or
	 *              unknown.)
	 */
	public DocumentException(Throwable cause) {
		super(cause);
	}
}
