package ru.sstu.images.filters.edges;

import ru.sstu.images.analysis.Image;
import ru.sstu.images.filters.AddFilter;
import ru.sstu.images.filters.Filter;

/**
 * <code>EdgeDetector</code> class is used for edge detection in images.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public abstract class EdgeDetector implements Filter {

	@Override
	public Image filter(Image image) {
		Filter addFilter = new AddFilter(vertical(), horizontal());
		return addFilter.filter(image);
	}

	/**
	 * Provides filter for vertical edge detection.
	 *
	 * @return {@link Filter}
	 */
	public abstract Filter vertical();

	/**
	 * Provides filter for horizontal edge detection.
	 *
	 * @return {@link Filter}
	 */
	public abstract Filter horizontal();
}
