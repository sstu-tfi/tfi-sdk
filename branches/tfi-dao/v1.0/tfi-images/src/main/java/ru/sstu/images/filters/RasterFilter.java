package ru.sstu.images.filters;

import java.awt.image.RasterOp;

import ru.sstu.images.analysis.Image;

/**
 * <code>RasterFilter</code> class is filter for raster objects using
 * {@link RasterOp}.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class RasterFilter implements Filter {

	private final RasterOp operation;

	/**
	 * Initializes raster filter.
	 *
	 * @param operation raster operation
	 */
	public RasterFilter(RasterOp operation) {
		this.operation = operation;
	}

	@Override
	public Image filter(Image image) {
		return new Image(operation.filter(image.getRaster(),
				image.getRaster().createCompatibleWritableRaster()));
	}
}
