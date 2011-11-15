package ru.sstu.math.util;

import junit.framework.TestCase;

/**
 * Class <code>BitsUtilTest</code> is unit test for {@link BitsUtil} class.
 *
 * @author Denis Murashev
 * @since Math 1.0
 */
public class BitsUtilTest extends TestCase {

	/**
	 * Samples count.
	 */
	private static final int SAMPLES = 20;

	/**
	 * Tests {@link BitsUtil#isPowerOf2(int)} method.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testIsPowerOf2() throws Exception {
		assertTrue(BitsUtil.isPowerOf2(0));
		assertTrue(BitsUtil.isPowerOf2(1));
		for (int i = 1; i < SAMPLES; i++) {
			int value = 1 << i;
			assertTrue(BitsUtil.isPowerOf2(value));
			value++;
			assertFalse(BitsUtil.isPowerOf2(value));
		}
	}

	/**
	 * Tests {@link BitsUtil#getMostSignificantBit(int)} method.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testGetMostSignificantBit() throws Exception {
		assertEquals(0, BitsUtil.getMostSignificantBit(0));
		assertEquals(Integer.MIN_VALUE, BitsUtil.getMostSignificantBit(-1));
		assertEquals(1, BitsUtil.getMostSignificantBit(1));
		for (int i = 1; i < SAMPLES; i++) {
			int expected = 1 << i;
			for (int value = expected;
					value < expected + SAMPLES && value < expected << 1;
					value++) {
				assertEquals(expected, BitsUtil.getMostSignificantBit(value));
			}
		}
	}

	/**
	 * Tests {@link BitsUtil#testBit(int, int)} method.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testTestBit() throws Exception {
		final int maxBit = 31;
		assertFalse(BitsUtil.testBit(0, 0));
		assertFalse(BitsUtil.testBit(1, 1));
		assertTrue(BitsUtil.testBit(1, 0));
		assertFalse(BitsUtil.testBit(2, 0));
		assertTrue(BitsUtil.testBit(2, 1));
		assertTrue(BitsUtil.testBit(-1, 0));
		assertTrue(BitsUtil.testBit(-1, maxBit));
		assertFalse(BitsUtil.testBit(Integer.MIN_VALUE, 0));
		assertTrue(BitsUtil.testBit(Integer.MIN_VALUE, maxBit));
	}

	/**
	 * Tests {@link BitsUtil#clearLeadingBits(int, int)} method.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testClearLeadingBits() throws Exception {
		for (int base = 1; base < SAMPLES; base++) {
			for (int value = 1; value < SAMPLES; value++) {
				int expected = value % (1 << base);
				assertEquals(expected, BitsUtil.clearLeadingBits(value, base));
			}
		}
	}
}
