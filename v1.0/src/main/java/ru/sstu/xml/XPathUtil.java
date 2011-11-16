package ru.sstu.xml;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <code>XPathUtil</code> class is utility class for XPath Expressions.
 *
 * @author Denis Murashev
 * @since XML 1.0
 */
public final class XPathUtil {

	/**
	 * No need to instantiate.
	 */
	private XPathUtil() {
	}

	/**
	 * Search for element for given XPath in given context.
	 *
	 * @param context context <code>Node</code>
	 * @param xPath   XPath expression
	 * @return <code>Element</code> found
	 * @throws XmlException if some error occurs
	 */
	public static Element getElement(Node context, String xPath)
			throws XmlException {
		try {
			return (Element) prepare(xPath)
					.evaluate(context, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			throw new XmlException(e);
		}
	}

	/**
	 * Search for node for given XPath in given context.
	 *
	 * @param context context <code>Node</code>
	 * @param xPath   XPath expression
	 * @return <code>Node</code> found
	 * @throws XmlException if some error occurs
	 */
	public static Node getNode(Node context, String xPath)
			throws XmlException {
		try {
			return (Node) prepare(xPath).evaluate(context, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			throw new XmlException(e);
		}
	}

	/**
	 * Search for elements for given XPath in given context.
	 *
	 * @param context context <code>Node</code>
	 * @param xPath   XPath expression
	 * @return array of <code>Element</code>s found
	 * @throws XmlException if some error occurs
	 */
	public static Element[] getElements(Node context, String xPath)
			throws XmlException {
		try {
			NodeList list = (NodeList) prepare(xPath)
					.evaluate(context, XPathConstants.NODESET);
			int length = list.getLength();
			Element[] elements = new Element[length];
			for (int i = 0; i < length; i++) {
				elements[i] = (Element) list.item(i);
			}
			return elements;
		} catch (XPathExpressionException e) {
			throw new XmlException(e);
		}
	}

	/**
	 * Search for nodes for given XPath in given context.
	 *
	 * @param context context <code>Node</code>
	 * @param xPath   XPath expression
	 * @return array of <code>Node</code>s found
	 * @throws XmlException if some error occurs
	 */
	public static Node[] getNodes(Node context, String xPath)
			throws XmlException {
		try {
			NodeList list = (NodeList) prepare(xPath)
					.evaluate(context, XPathConstants.NODESET);
			int length = list.getLength();
			Node[] nodes = new Node[length];
			for (int i = 0; i < length; i++) {
				nodes[i] = list.item(i);
			}
			return nodes;
		} catch (XPathExpressionException e) {
			throw new XmlException(e);
		}
	}

	/**
	 * Search for text for given XPath in given context.
	 *
	 * @param context context <code>Node</code>
	 * @param xPath   XPath expression
	 * @return text found
	 * @throws XmlException if some error occurs
	 */
	public static String getText(Node context, String xPath)
			throws XmlException {
		try {
			return (String) prepare(xPath)
					.evaluate(context, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			throw new XmlException(e);
		}
	}

	/**
	 * Search for number for given XPath in given context.
	 *
	 * @param context context <code>Node</code>
	 * @param xPath   XPath expression
	 * @return number found
	 * @throws XmlException if some error occurs
	 */
	public static Number getNumber(Node context, String xPath)
			throws XmlException {
		try {
			return (Number) prepare(xPath)
					.evaluate(context, XPathConstants.NUMBER);
		} catch (XPathExpressionException e) {
			throw new XmlException(e);
		}
	}

	/**
	 * Checks if given XPath exists in given context.
	 *
	 * @param context context <code>Node</code>
	 * @param xPath   XPath expression
	 * @return true if XPath exists
	 * @throws XmlException if some error occurs
	 */
	public static boolean exists(Node context, String xPath)
			throws XmlException {
		try {
			return (Boolean) prepare(xPath)
					.evaluate(context, XPathConstants.BOOLEAN);
		} catch (XPathExpressionException e) {
			throw new XmlException(e);
		}
	}

	/**
	 * Prepares expression.
	 *
	 * @param xPath XPath
	 * @return expression
	 * @throws XPathExpressionException if some error occurs.
	 */
	private static XPathExpression prepare(String xPath)
			throws XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		return factory.newXPath().compile(xPath);
	}
}
