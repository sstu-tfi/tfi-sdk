package ru.sstu.docs;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import ru.sstu.xml.DomUtil;
import ru.sstu.xml.XPathUtil;
import ru.sstu.xml.XmlException;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

/**
 * <code>FontManager</code> class manages fonts.
 *
 * @author Denis A. Murashev
 * @since Docs 1.0
 */
final class FontManager {

	/**
	 * Single instance.
	 */
	private static FontManager instance;

	/**
	 * Font properties file.
	 */
	private final InputStream properties
		= getClass().getResourceAsStream("/fonts.xml");

	/**
	 * Default font.
	 */
	private String defaultFont;

	/**
	 * Fonts.
	 */
	private final Map<String, String> fonts = new HashMap<String, String>();

	/**
	 * Initializes font manager.
	 *
	 * @throws DocumentException if cannot initialize font manager
	 */
	private FontManager() throws DocumentException {
		try {
			org.w3c.dom.Document dom = DomUtil.open(properties);
			for (Element element : XPathUtil.getElements(dom, "fonts/font")) {
				String name = XPathUtil.getText(element, "name");
				String path = XPathUtil.getText(element, "path");
				fonts.put(name, path);
				if (defaultFont == null) {
					defaultFont = path;
				}
			}
		} catch (XmlException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * @return single instance of <code>FontManager</code>.
	 * @throws DocumentException if cannot initialize instance
	 */
	static FontManager getInstance() throws DocumentException {
		if (instance == null) {
			instance = new FontManager();
		}
		return instance;
	}

	/**
	 * Provides font.
	 *
	 * @param name font name
	 * @return font
	 * @throws DocumentException if cannot get font
	 */
	Font getFont(String name) throws DocumentException {
		try {
			String path = fonts.get(name);
			if (path == null) {
				path = defaultFont;
			}
			return new Font(BaseFont.createFont(path, "Cp1251", true));
		} catch (IOException e) {
			throw new DocumentException(e);
		} catch (com.lowagie.text.DocumentException e) {
			throw new DocumentException(e);
		}
	}
}
