package ru.sstu.properties.core;

/**
 * <code>PropertyException</code> class is exception for tools for working
 * with properties.
 *
 * @author Denis_Murashev
 * @since Properties 1.0
 */
public class PropertyException extends Exception {

	private static final long serialVersionUID = 7418733492109817987L;

	/**
	 * Constructs a new exception with {@code null} as its detail message.
	 *
	 * @see Exception#Exception()
	 */
	public PropertyException() {
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 *
	 * @param message   the detail message
	 * @see Exception#Exception(String)
	 */
	public PropertyException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and
	 * cause.
	 *
	 * @param  message the detail message
	 * @param  cause   the cause
	 * @see Exception#Exception(String, Throwable)
	 */
	public PropertyException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail
	 * message of <tt>(cause == null ? null : cause.toString())</tt>.
	 *
	 * @param  cause the cause
	 * @see Exception#Exception(Throwable)
	 */
	public PropertyException(Throwable cause) {
		super(cause);
	}
}
