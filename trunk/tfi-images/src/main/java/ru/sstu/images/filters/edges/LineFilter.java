package ru.sstu.images.filters.edges;

/**
 * <code>LineFilter</code> class is edge detector based on line model.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class LineFilter extends EdgeDetector {

	private static final float[] MATRIX = new float[] {
			-1, 2, -1,
			-1, 2, -1,
			-1, 2, -1,
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
