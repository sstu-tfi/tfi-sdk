package ru.sstu.xml;

import java.io.File;

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
	 * Tests creating, saving, opening and deleting of {@link Document}.
	 *
	 * @throws Exception if test fails
	 */
	public void testLifeCycle() throws Exception {
		Document document = DomUtil.create();
		assertNotNull(document);
		Element root = document.createElement("root");
		document.appendChild(root);
		final String fileName = "target/files/sample/data.xml";
		DomUtil.save(document, fileName);
		Document actual = DomUtil.open(fileName);
		assertNotNull(actual);
		assertEquals(document.getDocumentElement().getLocalName(),
				actual.getDocumentElement().getLocalName());
		File file = new File(fileName);
		assertTrue(file.delete());
		file = new File("target/files/sample");
		assertTrue(file.delete());
		file = new File("target/files");
		assertTrue(file.delete());
	}
}
