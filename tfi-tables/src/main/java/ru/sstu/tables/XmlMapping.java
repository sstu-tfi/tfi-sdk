package ru.sstu.tables;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.sstu.xml.DomUtil;
import ru.sstu.xml.XPathUtil;
import ru.sstu.xml.XmlException;

/**
 * <code>XmlMapping</code> class is used for mapping between Java Bean
 * properties and columns of table using XML mapping.
 *
 * @author Denis_Murashev
 * @param <T> Java Bean type
 * @since Tables 1.0
 */
public class XmlMapping<T> extends Mapping<T> {

	/**
	 * @param mapping XML mapping
	 * @throws TableException if cannot create mapping
	 */
	public XmlMapping(InputStream mapping) throws TableException {
		init(mapping);
	}

	/**
	 * @param file configuration file
	 * @throws TableException if wrong configuration file or file not found
	 */
	public XmlMapping(File file) throws TableException {
		try {
			init(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new TableException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void init(InputStream mapping) throws TableException {
		try {
			Document document = DomUtil.open(mapping);
			String className = XPathUtil.getText(document, "/table/@class");
			Class<T> type = (Class<T>) Class.forName(className);
			setClass(type);
			String tableIndexPath = "/table/@tableIndex";
			if (XPathUtil.exists(document, tableIndexPath)) {
				setTableIndex(XPathUtil.getNumber(document, tableIndexPath)
						.intValue());
			}
			String skipRowsPath = "/table/@skipRows";
			if (XPathUtil.exists(document, skipRowsPath)) {
				setSkipRows(XPathUtil.getNumber(document, skipRowsPath)
						.intValue());
			}
			Element[] columns = XPathUtil.getElements(document,
					"/table/column");
			for (Element c : columns) {
				int index = XPathUtil.getNumber(c, "@index").intValue();
				String fieldName = XPathUtil.getText(c, "@field");
				Field field = type.getDeclaredField(fieldName);
				String value = XPathUtil.getText(c, "@default");
				if (value != null) {
					addField(index, field, value);
				} else {
					addField(index, field);
				}
				checkIndex(index);
			}
		} catch (XmlException e) {
			throw new TableException(e);
		} catch (ClassNotFoundException e) {
			throw new TableException(e);
		} catch (SecurityException e) {
			throw new TableException(e);
		} catch (NoSuchFieldException e) {
			throw new TableException(e);
		}
	}
}
