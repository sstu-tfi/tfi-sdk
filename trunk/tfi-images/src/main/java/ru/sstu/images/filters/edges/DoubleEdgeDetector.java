package ru.sstu.images.filters.edges;

import ru.sstu.images.filters.AddFilter;
import ru.sstu.images.filters.Filter;

/**
 * <code>DoubleEdgeDetector</code> class adapts {@link EdgeDetector} for
 * asymmetric convolution matrices.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public abstract class DoubleEdgeDetector extends EdgeDetector {

	@Override
	public final Filter vertical() {
		return new AddFilter(getLeft(), getRight());
	}

	@Override
	public final Filter horizontal() {
		return new AddFilter(getTop(), getBottom());
	}

	/**
	 * @return filter for left edges detector
	 */
	protected abstract Filter getLeft();

	/**
	 * @return filter for right edges detector
	 */
	protected abstract Filter getRight();

	/**
	 * @return filter for top edges detector
	 */
	protected abstract Filter getTop();

	/**
	 * @return filter for bottom edges detector
	 */
	protected abstract Filter getBottom();

	/**
	 * Provides inverted matrix.
	 *
	 * @param matrix initial matrix
	 * @return inverted matrix
	 */
	protected static float[] invert(float[] matrix) {
		float[] inverted = new float[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			inverted[i] = -matrix[i];
		}
		return inverted;
	}
}
