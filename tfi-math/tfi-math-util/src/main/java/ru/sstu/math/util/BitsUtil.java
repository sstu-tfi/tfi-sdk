package ru.sstu.math.util;

/**
 * Class <code>BitsUtil</code> contains of utility methods for working with
 * bits in integer numbers.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
public final class BitsUtil {

	/**
	 * No instance needed.
	 */
	private BitsUtil() {
	}

	/**
	 * Checks if number is power of 2.
	 *
	 * @param number integer number to be tested
	 * @return true if given number is rower of 2
	 */
	public static boolean isPowerOf2(int number) {
		return (number & number - 1) == 0;
	}

	/**
	 * Provides most significant bit for given number.
	 *
	 * @param number integer number
	 * @return most significant bit
	 */
	public static int getMostSignificantBit(int number) {
		if (number == 0) {
			return 0;
		}
		final int max = 31;
		return 1 << (max - Integer.numberOfLeadingZeros(number));
	}

	/**
	 * Test if given bit is set.
	 *
	 * @param number integer number to be tested
	 * @param bit    index of bit to be tested beginning from 0
	 * @return true if bit is set
	 */
	public static boolean testBit(int number, int bit) {
		int mask = 1 << bit;
		return (number & mask) != 0;
	}

	/**
	 * Clears leading bits. Only <code>base</code> bits remains unchanged.
	 *
	 * @param number initial number
	 * @param base   number of bits that won't be changed
	 * @return number with dropped leading bits
	 */
	public static int clearLeadingBits(int number, int base) {
		int mask = (1 << base) - 1;
		return number & mask;
	}

	/**
	 * @param number integer number to be tested
	 * @return true if given number is rower of 2
	 */
	public static boolean isPowerOf2(long number) {
		return (number & number - 1) == 0;
	}

	/**
	 * Provides most significant bit for given number.
	 *
	 * @param number integer number
	 * @return most significant bit
	 */
	public static long getMostSignificantBit(long number) {
		if (number == 0) {
			return 0;
		}
		final int max = 63;
		return 1L << (max - Long.numberOfLeadingZeros(number));
	}

	/**
	 * Test if given bit is set.
	 *
	 * @param number integer number to be tested
	 * @param bit    index of bit to be tested beginning from 0
	 * @return true if bit is set
	 */
	public static boolean testBit(long number, int bit) {
		long mask = 1L << bit;
		return (number & mask) != 0;
	}

	/**
	 * Clears leading bits. Only <code>base</code> bits remains unchanged.
	 *
	 * @param number initial number
	 * @param base   number of bits that won't be changed
	 * @return number with dropped leading bits
	 */
	public static long clearLeadingBits(long number, int base) {
		long mask = (1L << base) - 1L;
		return number & mask;
	}
}
