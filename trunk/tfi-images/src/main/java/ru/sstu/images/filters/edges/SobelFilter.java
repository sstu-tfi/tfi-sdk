package ru.sstu.images.filters.edges;

/**
 * <code>SobelFilter</code> class is edge detector based on Sobel method.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class SobelFilter extends ConvolveEdgeDetector {

	@Override
	protected float[] getVerticalMatrix() {
		return new float[] {
				-1, 0, 1,
				-2, 0, 2,
				-1, 0, 1,
		};
	}

	@Override
	protected boolean isSymmetric() {
		return false;
	}
}
