package ru.sstu.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * <code>DomUtil</code> class helps working with XML Documents.
 *
 * @author Denis A. Murashev
 * @author Victor Lushchenko
 * @author Alexey Maletz
 * @author Kirill V. Ovchinnikov
 * @since XML 1.0
 */
public final class DomUtil {

	/**
	 * No need to instantiate.
	 */
	private DomUtil() {
	}

	/**
	 * Creates new XML document.
	 *
	 * @return created document
	 * @throws XmlException if cannot create document
	 */
	public static Document create() throws XmlException {
		try {
			DocumentBuilderFactory factory =
					DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.newDocument();
		} catch (ParserConfigurationException e) {
			throw new XmlException(e);
		}
	}

	/**
	 * Generates DOM from given InputStream.
	 *
	 * @param input InputStream to read from
	 * @return Document generated from file
	 * @throws XmlException if cannot open document
	 */
	public static Document open(InputStream input) throws XmlException {
		try {
			DocumentBuilderFactory factory =
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(input);
		} catch (SAXException sxe) {
			throw new XmlException(sxe);
		} catch (ParserConfigurationException pce) {
			throw new XmlException(pce);
		} catch (IOException ioe) {
			throw new XmlException(ioe);
		}
	}

	/**
	 * Generates DOM from given file.
	 *
	 * @param fileName name of file to read
	 * @return Document generated from file
	 * @throws XmlException if cannot open document
	 */
	public static Document open(String fileName) throws XmlException {
		try {
			return open(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			throw new XmlException(e);
		}
	}

	/**
	 * Writes document to output stream.
	 *
	 * @param document document to be saved
	 * @param output   output stream to save to
	 * @throws XmlException if cannot save document
	 */
	public static void save(Node document, OutputStream output)
			throws XmlException {
		Source source = new DOMSource(document);
		Result result = new StreamResult(output);
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		} catch (TransformerException e) {
			throw new XmlException(e);
		}
	}

	/**
	 * Saves document to a file.
	 *
	 * @param document document to be saved
	 * @param fileName file to save to
	 * @throws XmlException if cannot save document
	 */
	public static void save(Node document, String fileName)
			throws XmlException {
		File file = new File(fileName);
		File dir = file.getParentFile();
		if (dir != null && !dir.exists()) {
			dir.mkdirs();
		}
		try {
			save(document, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new XmlException(e);
		}
	}
}
