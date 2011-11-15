package ru.sstu.xml;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class <code>XPathUtilTest</code> is unit test for <code>XPathUtil</code>.
 *
 * @author Denis A. Murashev
 * @since 1.1
 */
public class XPathUtilTest extends TestCase {

	/**
	 * Tests {@link XPathUtil} class.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testXPath() throws Exception {
		Document document = DomUtil.open(getClass()
				.getResourceAsStream("/test.xml"));
		Element[] elements = XPathUtil.getElements(document, "/root/node");
		assertEquals(2, elements.length);
		Element element = XPathUtil.getElement(document, "/root/node[@id='1']");
		String text = XPathUtil.getText(element, "subnode");
		assertEquals("Some text", text);
	}
}
