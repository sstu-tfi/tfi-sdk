package ru.sstu.images.analysis;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import ru.sstu.images.filters.Filter;

/**
 * <code>Image</code> class represents raster image.
 * It is also can be part of another image.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class Image {

	/**
	 * Maximum value of color.
	 */
	public static final int MAX_COLOR = 255;

	private static final int TYPE = BufferedImage.TYPE_BYTE_GRAY;

	private WritableRaster raster;

	/**
	 * Initializes image using given buffered image.
	 * Its raster is grey scaled first.
	 *
	 * @param image buffered image
	 */
	public Image(BufferedImage image) {
		BufferedImage copy = new BufferedImage(image.getWidth(),
				image.getHeight(), TYPE);
		copy.createGraphics().drawImage(image, 0, 0, null);
		raster = copy.getRaster();
	}

	/**
	 * Initializes image using given raster.
	 *
	 * @param raster raster
	 */
	public Image(WritableRaster raster) {
		this.raster = raster;
	}

	/**
	 * Provides image raster.
	 *
	 * @return the raster
	 */
	public WritableRaster getRaster() {
		return raster;
	}

	/**
	 * Sets new raster for image.
	 *
	 * @param raster the raster to set
	 */
	public void setRaster(WritableRaster raster) {
		this.raster = raster;
	}

	/**
	 * Provides image width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return raster.getWidth();
	}

	/**
	 * Provides image height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return raster.getHeight();
	}

	/**
	 * Creates {@link BufferedImage} instance for the image.
	 *
	 * @return {@link BufferedImage} instance
	 */
	public BufferedImage createImage() {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), TYPE);
		image.setData(raster);
		return image;
	}

	/**
	 * Normalizes brightness of the image.
	 */
	public void normalize() {
		int min = MAX_COLOR;
		int max = 0;
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				int value = getColor(i, j);
				if (value < min) {
					min = value;
				}
				if (value > max) {
					max = value;
				}
			}
		}
		if (max != min) {
			int size = max - min;
			for (int i = 0; i < getWidth(); i++) {
				for (int j = 0; j < getHeight(); j++) {
					int value = MAX_COLOR * (getColor(i, j) - min) / size;
					setColor(i, j, value);
				}
			}
		}
	}

	/**
	 * Applies given filter to image.
	 *
	 * @param filter filter
	 */
	public void applyFilter(Filter filter) {
		Image image = filter.filter(this);
		setRaster(image.getRaster());
	}

	/**
	 * Creates new image as region with given borders.
	 *
	 * @param x1 left edge
	 * @param y1 top edge
	 * @param x2 right edge
	 * @param y2 bottom edge
	 * @return image region
	 */
	public Image getRegion(int x1, int y1, int x2, int y2) {
		return new Image(raster.createWritableChild(x1, y1, x2 - x1, y2 - y1,
				0, 0, null));
	}

	/**
	 * Provides color.
	 *
	 * @param x x
	 * @param y y
	 * @return color
	 */
	public int getColor(int x, int y) {
		return raster.getSample(x, y, 0);
	}

	/**
	 * Sets color.
	 *
	 * @param x     x
	 * @param y     y
	 * @param value new color
	 */
	public void setColor(int x, int y, int value) {
		raster.setSample(x, y, 0, value);
	}

	/**
	 * Provides brightness.
	 *
	 * @param x x
	 * @param y y
	 * @return brightness
	 */
	public float getBrightness(int x, int y) {
		return raster.getSampleFloat(x, y, 0) / MAX_COLOR;
	}

	/**
	 * Sets brightness.
	 *
	 * @param x     x
	 * @param y     y
	 * @param value new brightness
	 */
	public void setBrightness(int x, int y, float value) {
		raster.setSample(x, y, 0, (int) (value * MAX_COLOR));
	}
}
