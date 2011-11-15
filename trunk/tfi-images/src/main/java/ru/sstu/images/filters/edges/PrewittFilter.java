package ru.sstu.images.filters.edges;

import java.awt.image.Kernel;

import ru.sstu.images.filters.ConvolveFilter;
import ru.sstu.images.filters.Filter;

/**
 * <code>PrewittFilter</code> class is edge detector based on Prewitt method.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class PrewittFilter extends DoubleEdgeDetector {

	private static final int SIZE = 3;

	private static final float[] LEFT_MATRIX = new float[] {
			-1, 0, 1,
			-1, 0, 1,
			-1, 0, 1,
	};

	private static final float[] TOP_MATRIX = new float[] {
			-1, -1, -1,
			 0,  0,  0,
			 1,  1,  1,
	};

	private static final Filter LEFT_FILTER = new ConvolveFilter(
			new Kernel(SIZE, SIZE, LEFT_MATRIX));

	private static final Filter RIGHT_FILTER = new ConvolveFilter(
			new Kernel(SIZE, SIZE, invert(LEFT_MATRIX)));

	private static final Filter TOP_FILTER = new ConvolveFilter(
			new Kernel(SIZE, SIZE, TOP_MATRIX));

	private static final Filter BOTTOM_FILTER = new ConvolveFilter(
			new Kernel(SIZE, SIZE, invert(TOP_MATRIX)));

	@Override
	public Filter getLeft() {
		return LEFT_FILTER;
	}

	@Override
	public Filter getRight() {
		return RIGHT_FILTER;
	}

	@Override
	public Filter getTop() {
		return TOP_FILTER;
	}

	@Override
	public Filter getBottom() {
		return BOTTOM_FILTER;
	}
}
