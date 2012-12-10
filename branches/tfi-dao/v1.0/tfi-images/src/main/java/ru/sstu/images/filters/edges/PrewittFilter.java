package ru.sstu.images.filters.edges;

/**
 * <code>PrewittFilter</code> class is edge detector based on Prewitt method.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class PrewittFilter extends EdgeDetector {

	private static final float[] MATRIX = new float[] {
			-1, 0, 1,
			-1, 0, 1,
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
