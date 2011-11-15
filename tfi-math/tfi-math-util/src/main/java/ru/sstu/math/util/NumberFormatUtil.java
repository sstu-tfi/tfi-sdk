package ru.sstu.math.util;

import java.util.Locale;

/**
 * <code>NumberFormatUtil</code> class helps to convert numbers to text.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
public final class NumberFormatUtil {

	/**
	 * Minimal normal style number.
	 */
	private static final double MIN_NORMAL_NUMBER = 1e-2;

	/**
	 * Maximal normal style number.
	 */
	private static final double MAX_NORMAL_NUMBER = 1e4;

	/**
	 * Normal number format.
	 */
	private static final String NORMAL_FORMAT = "%1$.5f";

	/**
	 * Exponential number format.
	 */
	private static final String EXPONENTIAL_FORMAT = "%1$.3e";

	/**
	 * No instances needed.
	 */
	private NumberFormatUtil() {
	}

	/**
	 * Converts given value to text.
	 *
	 * @param value value
	 * @return pretty string value
	 */
	public static String getPrettyNumber(Double value) {
		String format = NORMAL_FORMAT;
		if (Math.abs(value) > MAX_NORMAL_NUMBER
				|| Math.abs(value) < MIN_NORMAL_NUMBER) {
			format = EXPONENTIAL_FORMAT;
		}
		return String.format(Locale.ENGLISH, format, value);
	}
}
