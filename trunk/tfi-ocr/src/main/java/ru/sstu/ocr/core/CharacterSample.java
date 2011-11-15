package ru.sstu.ocr.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import ru.sstu.images.analysis.Image;
import ru.sstu.images.filters.RasterFilter;

/**
 * <code>CharacterSample</code> class represents character sample
 * to be recognized.
 *
 * @author Denis_Murashev
 */
public class CharacterSample {

	private final Image image;
	private final CharacterType hint;
	private float[][] sample;

	/**
	 * @param image sample image
	 * @param hint  character type hint
	 */
	public CharacterSample(Image image, CharacterType hint) {
		this.image = image;
		this.hint = hint;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @return the hint
	 */
	public CharacterType getHint() {
		return hint;
	}

	/**
	 * Provides match factor for given test data.
	 *
	 * @param pattern pattern to be matched
	 * @return match factor
	 */
	public float match(CharacterPattern pattern) {
		float[][] patternMatrix = pattern.getPattern();
		int height = patternMatrix.length;
		if (height == 0) {
			throw new RuntimeException("Character Pattern cannot be empty");
		}
		int width = patternMatrix[0].length;
		float[][] sampleMatrix = getSample(width, height);
		float value = 0.0f;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				value += sampleMatrix[i][j] * patternMatrix[i][j];
			}
		}
		return value;
	}

	/**
	 * Provides buffered image representation of sample data.
	 *
	 * @param size size of sample item in pixels
	 * @return image
	 */
	public BufferedImage toImage(int size) {
		if (sample == null) {
			return null;
		}
		int width = sample[0].length;
		int height = sample.length;
		BufferedImage bufferedImage = new BufferedImage(width * size,
				height * size, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = bufferedImage.createGraphics();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				float value = 1.0f - (sample[j][i] + 1) / 2;
				g.setColor(new Color(value, value, value));
				g.fillRect(i * size, j * size, size, size);
			}
		}
		g.dispose();
		return bufferedImage;
	}

	private float[][] getSample(int width, int height) {
		if (sample != null && sample.length == height
				&& sample[0].length == width) {
			return sample;
		}
		AffineTransform transform = new AffineTransform();
		transform.setToScale((float) width / image.getWidth(),
				(float) height / image.getHeight());
		Image scaled = new RasterFilter(new AffineTransformOp(transform, null))
				.filter(image);
		sample = new float[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				sample[y][x] = 2 * scaled.getBrightness(x, y) - 1;
			}
		}
		return sample;
	}
}
