package ru.sstu.math.uniform;

import java.util.Random;

/**
 * <code>RandomGenerator</code> class is a simple random generator.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
class RandomGenerator extends UniformGenerator {

	/**
	 * Random generator.
	 */
	private static final Random RANDOM = new Random();

	/**
	 * Creates random points generator.
	 *
	 * @param dimension dimension
	 */
	RandomGenerator(int dimension) {
		super(dimension);
	}

	/**
	 * {@inheritDoc}
	 */
	public double[] nextVector() {
		double[] point = new double[getDimension()];
		for (int i = 0; i < getDimension(); i++) {
			point[i] = RANDOM.nextDouble();
		}
		return point;
	}
}
