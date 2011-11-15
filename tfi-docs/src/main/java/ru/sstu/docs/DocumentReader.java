package ru.sstu.docs;

import java.io.InputStream;
import java.util.Collection;

/**
 * <code>DocumentReader</code> interface represents abstract document reader.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
public interface DocumentReader {

	/**
	 * Reads document from input stream.
	 *
	 * @param input input stream
	 * @return document
	 * @throws DocumentException if cannot read file
	 */
	Document read(InputStream input) throws DocumentException;

	/**
	 * Provides images from document. This method should be called after
	 * {@link DocumentReader#read(InputStream)}.
	 *
	 * @return collection of images
	 */
	Collection<Image> getImages();

	/**
	 * Adds external image to document.
	 *
	 * @param image image
	 */
	void addImage(Image image);
}
