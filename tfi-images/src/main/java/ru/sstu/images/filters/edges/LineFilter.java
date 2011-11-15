package ru.sstu.images.filters.edges;

import java.awt.image.Kernel;

import ru.sstu.images.filters.ConvolveFilter;
import ru.sstu.images.filters.Filter;

/**
 * <code>LineFilter</code> class is edge detector based on line model.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class LineFilter extends EdgeDetector {

	private static final int SIZE = 3;

	private static final float[] VERTICAL_MATRIX = new float[] {
			-1, 2, -1,
			-1, 2, -1,
			-1, 2, -1,
	};

	private static final float[] HORIZONTAL_MATRIX = new float[] {
			-1, -1, -1,
			 2,  2,  2,
			-1, -1, -1,
	};

	private static final Filter VERTICAL_FILTER = new ConvolveFilter(
			new Kernel(SIZE, SIZE, VERTICAL_MATRIX));

	private static final Filter HORIZONTAL_FILTER = new ConvolveFilter(
			new Kernel(SIZE, SIZE, HORIZONTAL_MATRIX));

	@Override
	public Filter vertical() {
		return VERTICAL_FILTER;
	}

	@Override
	public Filter horizontal() {
		return HORIZONTAL_FILTER;
	}
}
