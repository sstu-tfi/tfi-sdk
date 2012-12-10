package ru.sstu.images.filters;

import ru.sstu.images.analysis.Image;

/**
 * <code>AddFilter</code> class is used for combining filters together.
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class AddFilter implements Filter {

	private final Filter first;
	private final Filter second;

	/**
	 * Initializes combination filter.
	 *
	 * @param first  first filter
	 * @param second second filter
	 */
	public AddFilter(Filter first, Filter second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Image filter(Image image) {
		Image firstImage = first.filter(image);
		Image secondImage = second.filter(image);
		int width = image.getWidth();
		int height = image.getHeight();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int value = firstImage.getColor(i, j)
						+ secondImage.getColor(i, j);
				if (value > Image.MAX_COLOR) {
					value = Image.MAX_COLOR;
				}
				firstImage.setColor(i, j, value);
			}
		}
		return firstImage;
	}
}
