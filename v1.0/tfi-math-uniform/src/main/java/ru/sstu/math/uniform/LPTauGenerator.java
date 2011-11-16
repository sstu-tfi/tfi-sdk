package ru.sstu.math.uniform;

import ru.sstu.math.util.BitsUtil;

/**
 * <code>LPTauGenerator</code> class represents implementation of LPTau
 * sequences for many dimensions.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
class LPTauGenerator extends UniformGenerator {

	/**
	 * Maximum possible space dimension.
	 */
	private static final int MAX_DIMENSION = 14;

	/**
	 * Guiding numbers.
	 */
	private static final int[][] GUIDING_NUMBERS = {
		{1, 1, 1,  1,  1,  1,   1,   1,   1,    1,    1,    1,    1,     1,
			    1, },
		{1, 3, 5, 15, 17, 51,  85, 255, 257,  771, 1285, 3855, 4369,  3107,
			21845, },
		{1, 1, 7, 11, 13, 61,  85,  79, 465,  721,  823, 4091, 4125,  4141,
			28723, },
		{1, 3, 7,  5,  7, 43,  49, 147, 439, 1013,  727,  987, 5889,  6915,
			16647, },
		{1, 1, 5,  3, 15, 51, 125, 141, 177,  759,  285, 3855, 4369,  3107,
			21845, },
		{1, 3, 5, 15, 17, 51,  85, 255, 257,  771,  267, 1839, 6929, 16241,
			16565, },
		{1, 3, 1,  1,  9, 59,  25,  89, 321,  835,  833, 4033, 3913, 11643,
			18777, },
		{1, 1, 3,  7, 31, 47, 109, 173, 181,  949,  471, 2515, 6211,  2147,
			 3169, },
		{1, 3, 3,  9,  9, 57,  43,  43, 225,  113, 1601,  579, 1731,  1197,
			 7241, },
		{1, 3, 7, 13,  3, 35,  89,   9, 235,  929,  471, 3855, 4369,  3147,
			 3169, },
	};

	/**
	 * Point's number.
	 */
	private int number;

	/**
	 * Creates new LPTau generator.
	 *
	 * @param dimension dimension
	 */
	LPTauGenerator(int dimension) {
		super(dimension);
		if (dimension > MAX_DIMENSION) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public double[] nextVector() {
		double[] point = new double[getDimension()];
		++number;
		int tmp = number;
		int m = 0;
		while (tmp != 0) {
			tmp >>= 1;
			m++;
		}
		for (int j = 0; j < getDimension(); j++) {
			double value = 0;
			for (int k = 0; k < m; k++) {
				int n2 = 0;
				for (int l = k; l < m; l++) {
					if (BitsUtil.testBit(GUIDING_NUMBERS[j][l], l - k)
							&& BitsUtil.testBit(number, l)) {
						n2++;
					}
				}
				if (BitsUtil.testBit(n2, 0)) {
					value += 1.0 / (1 << (k + 1));
				}
			}
			point[j] = value;
		}
		return point;
	}
}
