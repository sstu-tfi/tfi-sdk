package ru.sstu.images.filters;

import java.util.Arrays;

import ru.sstu.images.analysis.Image;

/**
 * <code>MedianFilter</code> class represents median filter.
 * Important! The class is not thread safe!
 *
 * @author Denis_Murashev
 * @since Images 1.0
 */
public class MedianFilter implements Filter {

	private static final int MEDIAN_INDEX = 5;

	private byte[] values = new byte[Image.MAX_COLOR + 1];

	@Override
	public Image filter(Image image) {
		Image result = new Image(image.getRaster());
		for (int i = 1; i < image.getWidth() - 1; i++) {
			for (int j = 1; j < image.getHeight() - 1; j++) {
				result.setColor(i, j, getMedian(image, i, j));
			}
		}
		return result;
	}

	private int getMedian(Image image, int x, int y) {
		Arrays.fill(values, (byte) 0);
		int min = Image.MAX_COLOR;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				int index = image.getColor(i, j);
				values[index]++;
				if (index < min) {
					min = index;
				}
			}
		}
		int median = min;
		int sum = values[min];
		while (sum < MEDIAN_INDEX) {
			median++;
			sum += values[median];
		}
		return median;
	}
}
