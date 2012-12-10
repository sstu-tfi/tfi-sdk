package ru.sstu.docs;

import static ru.sstu.xml.XPathUtil.exists;
import static ru.sstu.xml.XPathUtil.getElements;
import static ru.sstu.xml.XPathUtil.getText;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.context.Parameter;
import net.sourceforge.jeuclid.converter.ConverterRegistry;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import ru.sstu.xml.DomUtil;
import ru.sstu.xml.XmlException;
import ru.sstu.xml.XslUtil;

/**
 * <code>DocxReader</code> class reads MS Word 2007 format.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
public class DocxReader implements DocumentReader {

	/**
	 * &lt;t&gt; tag.
	 */
	private static final String TAG_T = "t";

	/**
	 * URI for word processing markup language.
	 */
	private static final String URI_W
		= "http://schemas.openxmlformats.org/wordprocessingml/2006/main";

	/**
	 * URI for office document math markup language.
	 */
	private static final String URI_M
		= "http://schemas.openxmlformats.org/officeDocument/2006/math";

	/**
	 * Chunk size.
	 */
	private static final int CHUNK_SIZE = 32 * 1024;

	/**
	 * XSL script for converting from Open Math Markup Language
	 * to Math Markup Language.
	 */
	private static final String XSL_OMML2MML = "/xsl/omml2mml.xsl";

	/**
	 * Document.
	 */
	private Document document;

	/**
	 * Document processors.
	 */
	private final Map<String, FileProcessor> processors
		= new HashMap<String, FileProcessor>();
	{
		processors.put("word/_rels/document.xml.rels", new RelsProcessor());
		processors.put("word/document.xml", new DocumentProcessor());
	}

	/**
	 * Document resources.
	 */
	private final Map<String, String> resources = new HashMap<String, String>();

	/**
	 * Images.
	 */
	private List<Image> images = new ArrayList<Image>();

	/**
	 * Index for math images.
	 */
	private int mathImageIndex;

	/**
	 * {@inheritDoc}
	 */
	public Document read(InputStream input) throws DocumentException {
		try {
			ZipInputStream zip = new ZipInputStream(input);
			ZipEntry entry = zip.getNextEntry();
			while (entry != null) {
				FileProcessor processor = processors.get(entry.getName());
				if (processor != null) {
					processor.process(zip);
				}
				entry = zip.getNextEntry();
			}
			if (document != null) {
				document.postProcess(this);
			}
			zip.close();
			return document;
		} catch (IOException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Image> getImages() {
		return images;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addImage(Image image) {
		images.add(image);
	}

	/**
	 * Extracts data from ZIP data stream.
	 *
	 * @param zip ZIP input stream
	 * @return extracted input stream
	 * @throws DocumentException if some error occurs
	 */
	private InputStream getInputStream(ZipInputStream zip)
			throws DocumentException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		copy(zip, bytes);
		return new ByteArrayInputStream(bytes.toByteArray());
	}

	/**
	 * Provides root node of given data stream.
	 *
	 * @param zip ZIP input stream
	 * @return root node
	 * @throws DocumentException if some error occurs
	 */
	private Node getContext(ZipInputStream zip) throws DocumentException {
		try {
			return DomUtil.open(getInputStream(zip));
		} catch (XmlException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * Copies one stream to another.
	 *
	 * @param input  input stream
	 * @param output output stream
	 * @throws DocumentException if some error occurs
	 */
	private void copy(InputStream input, OutputStream output)
			throws DocumentException {
		try {
			BufferedInputStream in = new BufferedInputStream(input);
			byte[] buffer = new byte[CHUNK_SIZE];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
			output.flush();
		} catch (IOException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * Processes block.
	 *
	 * @param element XML element
	 * @param block   block as part of document structure
	 * @throws DocumentException if some error occurs
	 */
	private void processBlock(Element element, Block<Atom> block)
			throws DocumentException {
		try {
			Element[] elements = getElements(element, "r | oMath");
			for (Element e : elements) {
				if (exists(e, TAG_T)) {
					processText(e, block);
				} else if (exists(e, "drawing")) {
					processImage(e, block);
				} else if ("m:oMath".equals(e.getNodeName())) {
					processMath(e, block);
				}
			}
		} catch (XmlException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * Processes text node.
	 *
	 * @param element text node parent
	 * @param block   parent block
	 * @throws DocumentException if some error occurs
	 */
	private void processText(Element element, Block<Atom> block)
			throws DocumentException {
		try {
			TextNode node = new TextNode();
			node.setText(getText(element, TAG_T));
			node.setBold(exists(element, "rPr/b"));
			node.setItalic(exists(element, "rPr/i"));
			node.setUnderlined(exists(element, "rPr/u"));
			String vertAlign = getText(element, "rPr/vertAlign/@val");
			node.setSuperscript("superscript".equals(vertAlign));
			node.setSubscript("subscript".equals(vertAlign));
			// TODO add font support
			block.add(node);
		} catch (XmlException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * Processes image data.
	 *
	 * @param element image element
	 * @param block   parent block
	 * @throws DocumentException if some error occurs
	 */
	private void processImage(Element element, Block<Atom> block)
			throws DocumentException {
		try {
			String path = "drawing/inline/graphic/graphicData/pic/blipFill/"
					+ "blip/@embed";
			String id = getText(element, path);
			String resource = "word/" + resources.get(id);
			Image image = new Image();
			String text = getText(element, "drawing/inline/docPr/@descr");
			image.setAlt(text);
			image.setUrl(resource);
			DocxReader.this.processors.put(resource, new ImageProcessor(image));
			block.add(image);
		} catch (XmlException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * Processes MathML data.
	 *
	 * @param element MathML data element
	 * @param block   parent block
	 * @throws DocumentException if some error occurs
	 */
	private void processMath(Element element, Block<Atom> block)
			throws DocumentException {
		try {
			element.setAttribute("xmlns:m", URI_M);
			element.setAttribute("xmlns:w", URI_W);
			ByteArrayOutputStream omml = new ByteArrayOutputStream();
			DomUtil.save(element, omml);
			Source xsl = new StreamSource(getClass()
					.getResourceAsStream(XSL_OMML2MML));
			Source xml = new StreamSource(new ByteArrayInputStream(omml
					.toByteArray()));
			DOMResult mml = new DOMResult();
			XslUtil.transform(xml, xsl, mml);
			LayoutContextImpl context = new LayoutContextImpl(LayoutContextImpl
					.getDefaultLayoutContext());
			Float fontSize = (Float) context.getParameter(Parameter.MATHSIZE);
			final float factor = 1.5F;
			context.setParameter(Parameter.MATHSIZE, factor * fontSize);
			context.setParameter(Parameter.MFRAC_KEEP_SCRIPTLEVEL,
					Boolean.FALSE);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ConverterRegistry.getInstance().getConverter("image/png")
					.convert(mml.getNode(), context, out);
			Image image = new Image();
			mathImageIndex++;
			final String imageName = "mml-" + mathImageIndex + ".png";
			image.setUrl(imageName);
			image.setAlt(imageName);
			image.setData(out.toByteArray());
			block.add(image);
		} catch (XmlException e) {
			throw new DocumentException(e);
		} catch (IOException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * File processor.
	 *
	 * @author Denis_Murashev
	 */
	private interface FileProcessor {

		/**
		 * @param zip ZIP input stream
		 * @throws DocumentException if some error occurs
		 */
		void process(ZipInputStream zip) throws DocumentException;
	}

	/**
	 * Document processor.
	 *
	 * @author Denis_Murashev
	 */
	private final class DocumentProcessor implements FileProcessor {

		/**
		 * Processors.
		 */
		private Map<String, ElementProcessor> processors
			= new HashMap<String, ElementProcessor>();
		{
			processors.put("w:p", new ElementProcessor() {
				public void process(Element parent)
						throws DocumentException {
					Paragraph paragraph = new Paragraph();
					processBlock(parent, paragraph);
					document.add(paragraph);
				}
			});
			processors.put("w:tbl", new ElementProcessor() {
				public void process(Element parent)
						throws DocumentException {
					try {
						TableBlock table = new TableBlock();
						for (Element element : getElements(parent, "tr")) {
							TableRow row = new TableRow();
							for (Element e : getElements(element, "tc/p")) {
								TableCell cell = new TableCell();
								processBlock(e, cell);
								row.add(cell);
							}
							table.add(row);
						}
						document.add(table);
					} catch (XmlException e) {
						throw new DocumentException(e);
					}
				}
			});
		}

		/**
		 * {@inheritDoc}
		 */
		public void process(ZipInputStream zip) throws DocumentException {
			Node context = getContext(zip);
			try {
				Element[] elements = getElements(context,
						"/document/body/*");
				document = new Document();
				for (Element element : elements) {
					ElementProcessor processor = processors
							.get(element.getNodeName());
					if (processor != null) {
						processor.process(element);
					}
				}
			} catch (XmlException e) {
				throw new DocumentException(e);
			}
		}
	}

	/**
	 * Rels processor.
	 *
	 * @author Denis_Murashev
	 */
	private final class RelsProcessor implements FileProcessor {

		/**
		 * {@inheritDoc}
		 */
		public void process(ZipInputStream zip) throws DocumentException {
			Node context = getContext(zip);
			try {
				Element[] elements = getElements(context,
								"/Relationships/Relationship");
				for (Element element : elements) {
					resources.put(getText(element, "@Id"),
							getText(element, "@Target"));
				}
			} catch (XmlException e) {
				throw new DocumentException(e);
			}
		}
	}

	/**
	 * Element processor.
	 *
	 * @author Denis_Murashev
	 */
	private interface ElementProcessor {

		/**
		 * @param parent element to be processed
		 * @throws DocumentException if some error occurs
		 */
		void process(Element parent) throws DocumentException;
	}

	/**
	 * Image processor.
	 *
	 * @author Denis_Murashev
	 */
	private class ImageProcessor implements FileProcessor {

		/**
		 * Image.
		 */
		private Image image;

		/**
		 * @param image image
		 */
		public ImageProcessor(Image image) {
			this.image = image;
		}

		/**
		 * {@inheritDoc}
		 */
		public void process(ZipInputStream zip) throws DocumentException {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			copy(zip, bytes);
			image.setData(bytes.toByteArray());
		}
	}
}
