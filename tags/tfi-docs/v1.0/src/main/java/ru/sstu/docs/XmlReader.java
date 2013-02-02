package ru.sstu.docs;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.sstu.xml.DomUtil;
import ru.sstu.xml.XPathUtil;
import ru.sstu.xml.XmlException;

/**
 * <code>XmlReader</code> class parses XML file into {@link Document}.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
class XmlReader implements DocumentReader {

	/**
	 * Images.
	 */
	private Map<String, Image> images = new HashMap<String, Image>();

	/**
	 * {@inheritDoc}
	 */
	public Document read(InputStream input) throws DocumentException {
		try {
			Document document = new Document();
			org.w3c.dom.Document dom = DomUtil.open(input);
			Element content = XPathUtil.getElement(dom, "/doc");
			return readContent(content, document);
		} catch (XmlException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Image> getImages() {
		return images.values();
	}

	/**
	 * {@inheritDoc}
	 */
	public void addImage(Image image) {
		images.put(image.getUrl(), image);
	}

	/**
	 * Reads content.
	 *
	 * @param content content element
	 * @param parent  parent document
	 * @return parent document
	 * @throws DocumentException if some error occurs
	 * @throws XmlException      if some error occurs
	 */
	private Document readContent(Element content, Document parent)
			throws DocumentException, XmlException {
		NodeList nodes = content.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if ("p".equals(element.getNodeName())) {
					parent.add(readParagraph(element));
				} else if ("table".equals(element.getNodeName())) {
					parent.add(readTable(element));
				} else if ("list".equals(element.getNodeName())) {
					parent.add(readList(element));
				}
			}
		}
		return parent;
	}

	/**
	 * Reads paragraph.
	 *
	 * @param element source element
	 * @return paragraph
	 * @throws DocumentException if some error occurs
	 * @throws XmlException      if some error occurs
	 */
	private Paragraph readParagraph(Element element)
			throws DocumentException, XmlException {
		return (Paragraph) readBlock(element, new Paragraph());
	}

	/**
	 * Reads table.
	 *
	 * @param element source element
	 * @return table
	 * @throws DocumentException if some error occurs
	 * @throws XmlException      if some error occurs
	 */
	private TableBlock readTable(Element element)
			throws DocumentException, XmlException {
		TableBlock table = new TableBlock();
		Element[] rows = XPathUtil.getElements(element, "tr");
		for (Element row : rows) {
			TableRow tr = new TableRow();
			Element[] cells = XPathUtil.getElements(row, "td");
			for (Element cell : cells) {
				TableCell td = new TableCell();
				String colspan = cell.getAttribute("colspan");
				if (colspan != null && !"".equals(colspan)) {
					td.setColspan(Integer.valueOf(colspan));
				}
				tr.add((TableCell) readBlock(cell, td));
			}
			table.add(tr);
		}
		return table;
	}

	/**
	 * Reads list.
	 *
	 * @param element source element
	 * @return list
	 * @throws DocumentException if some error occurs
	 * @throws XmlException      if some error occurs
	 */
	private ListBlock readList(Element element)
			throws DocumentException, XmlException {
		ListBlock list = new ListBlock();
		Element[] items = XPathUtil.getElements(element, "li");
		for (Element item : items) {
			ListItem li = new ListItem();
			list.add((ListItem) readBlock(item, li));
		}
		return list;
	}

	/**
	 * Reads paragraph.
	 *
	 * @param element source element
	 * @param parent  parent list
	 * @return list
	 * @throws DocumentException if some error occurs
	 * @throws XmlException      if some error occurs
	 */
	private List<Atom> readBlock(Element element, List<Atom> parent)
			throws DocumentException, XmlException {
		NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.TEXT_NODE) {
				parent.add(new TextNode(node.getTextContent()));
			} else if (node.getNodeType() == Node.ELEMENT_NODE) {
				final String srcAttribute = "@src";
				final String slash = "/";
				if ("img".equals(node.getNodeName())) {
					Image image = new Image();
					String src = XPathUtil.getText(node, srcAttribute);
					int index = src.lastIndexOf(slash);
					src = src.substring(index + 1);
					image.setUrl(src);
					image.setAlt(src);
					image = readImage((Element) node, image);
					parent.add(image);
					images.put(src, image);
				} else if ("imgRef".equals(node.getNodeName())) {
					String src = XPathUtil.getText(node, srcAttribute);
					int index = src.lastIndexOf(slash);
					src = src.substring(index + 1);
					Image image = images.get(src);
					if (image == null) {
						throw new DocumentException("No Image for URL: " + src);
					}
					parent.add(image);
				} else {
					parent.add(readText((Element) node, new TextNode()));
				}
			}
		}
		return parent;
	}

	/**
	 * Reads text.
	 *
	 * @param element parent element
	 * @param node    text node
	 * @return text node
	 * @throws DocumentException if cannot read
	 */
	private TextNode readText(Element element, TextNode node)
			throws DocumentException {
		if ("b".equals(element.getNodeName())) {
			node.setBold(true);
		} else if ("i".equals(element.getNodeName())) {
			node.setItalic(true);
		} else if ("u".equals(element.getNodeName())) {
			node.setUnderlined(true);
		} else if ("sup".equals(element.getNodeName())) {
			node.setSuperscript(true);
		} else if ("sub".equals(element.getNodeName())) {
			node.setSubscript(true);
		} else if ("span".equals(element.getNodeName())) {
			node.setFont(element.getAttribute("font"));
		}
		Node child = element.getFirstChild();
		if (child == null) {
			return node;
		} else if (child.getNodeType() == Node.TEXT_NODE) {
			node.setText(child.getTextContent());
			return node;
		} else if (child.getNodeType() == Node.ELEMENT_NODE) {
			return readText((Element) child, node);
		} else {
			throw new DocumentException("Unsupported XML structure");
		}
	}

	/**
	 * Reads image.
	 *
	 * @param element parent element
	 * @param image   image
	 * @return image
	 * @throws DocumentException if some error occurs
	 */
	private Image readImage(Element element, Image image)
			throws DocumentException {
		String text = element.getTextContent();
		byte[] data = new Base64().decode(text.getBytes());
		final String wmz = ".wmz";
		if (image.getUrl().toLowerCase().endsWith(wmz)) {
			data = ImageUtil.readWMZ(image.getUrl(), data);
			String name = image.getUrl().replaceAll(wmz, ".wmf");
			image.setUrl(name);
			image.setAlt(name);
		}
		image.setData(data);
		return image;
	}
}
