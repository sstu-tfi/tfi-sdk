package ru.sstu.tables;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>Mapping</code> class is used for mapping between Java Bean properties
 * and columns of table.
 *
 * @author Denis_Murashev
 * @param <T> Java Bean type
 * @since Tables 1.0
 */
public abstract class Mapping<T> {

	/**
	 * Current table reader.
	 */
	private TableReader reader;

	/**
	 * The class.
	 */
	private Class<T> type;

	/**
	 * Mapped fields of the Java Bean.
	 */
	private Map<Integer, Field> fields = new HashMap<Integer, Field>();

	/**
	 * Default values of the Java Bean fields.
	 */
	private Map<Integer, String> defaultValues = new HashMap<Integer, String>();

	/**
	 * Max of column index.
	 */
	private int maxColumnIndex = 0;

	/**
	 * Index of table.
	 */
	private int tableIndex = 0;

	/**
	 * Count of first rows to be skipped.
	 * @since Tables 1.1
	 */
	private int skipRows = 0;

	/**
	 * @return the tableIndex
	 * @since Tables 1.1
	 */
	public int getTableIndex() {
		return tableIndex;
	}

	/**
	 * @param tableIndex the tableIndex to set
	 * @since Tables 1.1
	 */
	public void setTableIndex(int tableIndex) {
		this.tableIndex = tableIndex;
	}

	/**
	 * @return the skipRows
	 * @since Tables 1.1
	 */
	public int getSkipRows() {
		return skipRows;
	}

	/**
	 * @param skipRows the skipRows to set
	 * @since Tables 1.1
	 */
	public void setSkipRows(int skipRows) {
		this.skipRows = skipRows;
	}

	/**
	 * Provides table reader.
	 *
	 * @return table reader
	 */
	protected TableReader getTableReader() {
		return reader;
	}

	/**
	 * Sets current table reader.
	 *
	 * @param reader table reader
	 */
	protected void setTableReader(TableReader reader) {
		this.reader = reader;
	}

	/**
	 * Creates new mapped object instance.
	 *
	 * @return new object instance
	 * @throws TableException if cannot create object
	 */
	protected T createObject() throws TableException {
		try {
			return type.newInstance();
		} catch (InstantiationException e) {
			throw new TableException(e);
		} catch (IllegalAccessException e) {
			throw new TableException(e);
		}
	}

	/**
	 * Sets value to the Java Bean field.
	 *
	 * @param object Java Bean object
	 * @param index  table column index
	 * @param value  value to be set
	 * @throws TableException if cannot set value
	 */
	protected void setValue(T object, int index, Object value)
			throws TableException {
		try {
			Field field = fields.get(index);
			if (field != null) {
				field.setAccessible(true);
				if (value == null || "".equals(value.toString())) {
					Class<?> fromClass = String.class;
					Class<?> toClass = field.getType();
					String defaultValue = defaultValues.get(index);
					if (toClass == fromClass) {
						field.set(object, defaultValue);
					} else {
						Converter converter = reader.getConverter(fromClass,
								toClass);
						field.set(object, converter.convert(defaultValue));
					}
				} else {
					Class<?> fromClass = value.getClass();
					Class<?> toClass = field.getType();
					if (toClass == fromClass) {
						field.set(object, value);
					} else {
						Converter converter = reader.getConverter(fromClass,
								toClass);
						field.set(object, converter.convert(value));
					}
				}
			}
		} catch (IllegalArgumentException e) {
			throw new TableException(e);
		} catch (IllegalAccessException e) {
			throw new TableException(e);
		}
	}

	/**
	 * @return max column index
	 */
	protected int getMaxIndex() {
		return maxColumnIndex;
	}

	/**
	 * @return class
	 */
	protected Class<T> getType() {
		return type;
	}

	/**
	 * @param type class type
	 */
	protected void setClass(Class<T> type) {
		this.type = type;
	}

	/**
	 * Adds mapping.
	 *
	 * @param index column index
	 * @param field field
	 */
	protected void addField(int index, Field field) {
		fields.put(index, field);
	}

	/**
	 * Adds mapping.
	 *
	 * @param index column index
	 * @param field field
	 * @param value default value
	 * @since Tables 1.1
	 */
	protected void addField(int index, Field field, String value) {
		fields.put(index, field);
		defaultValues.put(index, value);
	}

	/**
	 * Checks if given index is the greatest.
	 *
	 * @param index index
	 */
	protected void checkIndex(int index) {
		if (index > maxColumnIndex) {
			maxColumnIndex = index;
		}
	}
}
