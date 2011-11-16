package ru.sstu.xml;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * <code>XslUtil</code> class is utility class for XSL Transformations.
 *
 * @author Denis Murashev
 * @since XML 1.0
 */
public final class XslUtil {

	/**
	 * No need to instantiate.
	 */
	private XslUtil() {
	}

	/**
	 * Transforms given <code>xml</code> using given <code>xsl</code> into
	 * <code>out</code> stream.
	 *
	 * @param xml input stream with XML
	 * @param xsl input stream with XSL
	 * @param out output stream
	 * @throws XmlException if cannot transform
	 */
	public static void transform(InputStream xml, InputStream xsl,
			OutputStream out) throws XmlException {
		transform(new StreamSource(xml), new StreamSource(xsl),
				new StreamResult(out));
	}

	/**
	 * Transforms given <code>xml</code> using given <code>xsl</code> into
	 * <code>out</code> stream.
	 *
	 * @param xml source with XML
	 * @param xsl source with XSL
	 * @param out transformation result
	 * @throws XmlException if cannot transform
	 */
	public static void transform(Source xml, Source xsl, Result out)
			throws XmlException {
		try {
			TransformerFactory.newInstance().newTransformer(xsl)
					.transform(xml, out);
		} catch (TransformerConfigurationException e) {
			throw new XmlException(e);
		} catch (TransformerException e) {
			throw new XmlException(e);
		}
	}
}
