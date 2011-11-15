package ru.sstu.images.filters;

import ru.sstu.images.analysis.Image;

/**
 * <code>Filter</code> interface represents filter for raster images.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public interface Filter {

	/**
	 * Filters image.
	 * The new image will be created.
	 *
	 * @param image original image
	 * @return filtered image
	 */
	Image filter(Image image);
}
