package ru.sstu.xml;

/**
 * <code>XmlException</code> class is exception for TFI Common XML.
 *
 * @author Denis A. Murashev
 * @since XML 1.0
 */
public class XmlException extends Exception {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 3635139902286862639L;

	/**
	 * Constructs a new exception with the specified detail message.  The
	 * cause is not initialized, and may subsequently be initialized by
	 * a call to {@link #initCause(Throwable)}.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public XmlException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which
	 * typically contains the class and detail message of <tt>cause</tt>).
	 * This constructor is useful for exceptions that are little more than
	 * wrappers for other throwables.
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 *              {@link #getCause()} method).  (A <tt>null</tt> value is
	 *              permitted, and indicates that the cause is nonexistent or
	 *              unknown.)
	 */
	public XmlException(Throwable cause) {
		super(cause);
	}
}
