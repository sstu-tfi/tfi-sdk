package ru.sstu.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import junit.framework.TestCase;

/**
 * <code>DomUtilTest</code> class contains unit tests for {@link DomUtil} class.
 *
 * @author Denis_Murashev
 * @since XML 1.0.1
 */
public class DomUtilTest extends TestCase {

	/**
	 * Tests {@link DomUtil#save(org.w3c.dom.Node, String)} method.
	 *
	 * @throws Exception if test fails
	 */
	public void testSave() throws Exception {
		Document document = DomUtil.create();
		Element root = document.createElement("root");
		document.appendChild(root);
		DomUtil.save(document, "target/files/sample/data.xml");
	}
}
