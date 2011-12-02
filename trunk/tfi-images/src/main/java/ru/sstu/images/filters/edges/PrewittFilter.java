package ru.sstu.images.filters.edges;

/**
 * <code>PrewittFilter</code> class is edge detector based on Prewitt method.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class PrewittFilter extends ConvolveEdgeDetector {

	@Override
	protected float[] getVerticalMatrix() {
		return new float[] {
				-1, 0, 1,
				-1, 0, 1,
				-1, 0, 1,
		};
	}

	@Override
	protected boolean isSymmetric() {
		return false;
	}
}
