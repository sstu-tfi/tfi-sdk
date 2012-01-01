package ru.sstu.images.filters.edges;

/**
 * <code>SobelFilter</code> class is edge detector based on Sobel method.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class SobelFilter extends EdgeDetector {

	private static final float[] MATRIX = new float[] {
			-1, 0, 1,
			-2, 0, 2,
			-1, 0, 1,
	};

	@Override
	protected float[] getVerticalMatrix() {
		return MATRIX;
	}

	@Override
	protected boolean isSymmetric() {
		return false;
	}
}
