package ru.sstu.math.uniform;

import org.apache.commons.math.random.RandomVectorGenerator;

/**
 * <code>UniformGenerator</code> class generates uniformly distributed sequences
 * for many dimensional spaces.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
public abstract class UniformGenerator implements RandomVectorGenerator {

	/**
	 * Dimension of points to be generated.
	 */
	private int dimension;

	/**
	 * Protected constructor.
	 *
	 * @param dimension dimension
	 */
	protected UniformGenerator(int dimension) {
		this.dimension = dimension;
	}

	/**
	 * @param dimension generated points dimension
	 * @return new instance of RandomGenerator
	 */
	public static UniformGenerator getRandomGenerator(int dimension) {
		return new RandomGenerator(dimension);
	}

	/**
	 * @param dimension generated points dimension
	 * @return new instance of LPTauGenerator
	 */
	public static UniformGenerator getLPTauGenerator(int dimension) {
		return new LPTauGenerator(dimension);
	}

	/**
	 * @return dimension of generated points
	 */
	protected int getDimension() {
		return dimension;
	}
}
