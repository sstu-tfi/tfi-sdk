package ru.sstu.docs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;

import ru.sstu.xml.XmlException;
import ru.sstu.xml.XslUtil;

/**
 * <code>DocXmlReader</code> class reads MS Word 2003 XML format.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
public class DocXmlReader implements DocumentReader {

	/**
	 * Error message.
	 */
	private static final String ERROR_NOT_READ = "Document is not read yet.";

	/**
	 * Path to XSL script.
	 */
	private static final String PATH = "/xsl/word2003.xsl";

	/**
	 * XML reader.
	 */
	private XmlReader reader;

	/**
	 * {@inheritDoc}
	 */
	public Document read(InputStream input) throws DocumentException {
		try {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			XslUtil.transform(input, getClass().getResourceAsStream(PATH),
					bytes);
			reader = new XmlReader();
			return reader.read(new ByteArrayInputStream(bytes.toByteArray()));
		} catch (XmlException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Image> getImages() {
		if (reader == null) {
			throw new IllegalStateException(ERROR_NOT_READ);
		}
		return reader.getImages();
	}

	/**
	 * {@inheritDoc}
	 */
	public void addImage(Image image) {
		if (reader == null) {
			throw new IllegalStateException(ERROR_NOT_READ);
		}
		reader.addImage(image);
	}
}
