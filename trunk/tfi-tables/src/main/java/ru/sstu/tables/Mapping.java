package ru.sstu.tables;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	 * Converters.
	 */
	private static Map<Class<?>, Map<Class<?>, Converter>> converters
			= new HashMap<Class<?>, Map<Class<?>, Converter>>();

	/**
	 * Default date format. dd/MM/yyyy.
	 * TODO Probably make date format customizable.
	 */
	private static final DateFormat DATE_FORMAT
			= new SimpleDateFormat("dd.MM.yyyy");

	/**
	 * The class.
	 */
	private Class<T> type;

	/**
	 * Annotated fields of the Java Bean.
	 */
	private Map<Integer, Field> fields = new HashMap<Integer, Field>();

	/**
	 * Max of column index.
	 */
	private int maxColumnIndex;

	/**
	 * Index of table.
	 */
	private int tableIndex;

	/**
	 * Count of first rows to be skipped.
	 * @since Tables 1.1
	 */
	private int skipRows;

	/**
	 * Adds new converter.
	 *
	 * @param fromClass from class
	 * @param toClass   to class
	 * @param converter converter
	 */
	public static void addConverter(Class<?> fromClass, Class<?> toClass,
			Converter converter) {
		Map<Class<?>, Converter> map = converters.get(fromClass);
		if (map == null) {
			map = new HashMap<Class<?>, Converter>();
			converters.put(fromClass, map);
		}
		map.put(toClass, converter);
	}

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
		if (value == null) {
			return;
		}
		try {
			Field field = fields.get(index);
			if (field != null) {
				field.setAccessible(true);
				Class<?> fromClass = value.getClass();
				Class<?> toClass = field.getType();
				if (toClass == fromClass) {
					field.set(object, value);
				} else {
					Converter converter = getConverter(fromClass, toClass);
					field.set(object, converter.convert(value));
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
	 * Checks if given index is the greatest.
	 *
	 * @param index index
	 */
	protected void checkIndex(int index) {
		if (index > maxColumnIndex) {
			maxColumnIndex = index;
		}
	}

	/**
	 * Looking for converter.
	 *
	 * @param fromClass from class
	 * @param toClass   to class
	 * @return converter
	 * @throws TableException if cannot find converter
	 */
	protected static Converter getConverter(Class<?> fromClass,
			Class<?> toClass) throws TableException {
		Map<Class<?>, Converter> map = convertersMap(fromClass);
		if (map == null) {
			throw new TableException(getError(fromClass, toClass));
		}
		Converter converter = map.get(toClass);
		if (converter == null) {
			for (Class<?> c : map.keySet()) {
				if (toClass.isAssignableFrom(c)) {
					return map.get(c);
				}
			}
			throw new TableException(getError(fromClass, toClass));
		}
		return converter;
	}

	/**
	 * Provides converters map.
	 *
	 * @param fromClass from class
	 * @return map of converters
	 */
	private static Map<Class<?>, Converter> convertersMap(Class<?> fromClass) {
		Map<Class<?>, Converter> map = converters.get(fromClass);
		Class<?> tmp = fromClass;
		while (map == null && !Object.class.equals(tmp)) {
			tmp = fromClass.getSuperclass();
			map = converters.get(tmp);
		}
		if (Object.class.equals(tmp)) {
			for (Class<?> c : fromClass.getInterfaces()) {
				if (converters.containsKey(c)) {
					return converters.get(c);
				}
			}
		}
		return map;
	}

	/**
	 * Provides error message.
	 *
	 * @param fromClass from class
	 * @param toClass   to class
	 * @return error message
	 */
	private static String getError(Class<?> fromClass, Class<?> toClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("Cannot conver value from ").append(fromClass)
				.append(" to ").append(toClass);
		return builder.toString();
	}

	static {
		addConverter(Object.class, String.class, new Converter() {
			public String convert(Object value) {
				return value.toString();
			}
		});
		addConverter(String.class, short.class, new Converter() {
			public Short convert(Object value) {
				return Short.parseShort((String) value);
			}
		});
		addConverter(Number.class, short.class, new Converter() {
			public Short convert(Object value) {
				return ((Number) value).shortValue();
			}
		});
		addConverter(String.class, int.class, new Converter() {
			public Integer convert(Object value) {
				return Integer.parseInt((String) value);
			}
		});
		addConverter(Number.class, int.class, new Converter() {
			public Integer convert(Object value) {
				return ((Number) value).intValue();
			}
		});
		addConverter(String.class, long.class, new Converter() {
			public Long convert(Object value) {
				return Long.parseLong((String) value);
			}
		});
		addConverter(Number.class, long.class, new Converter() {
			public Long convert(Object value) {
				return ((Number) value).longValue();
			}
		});
		addConverter(String.class, float.class, new Converter() {
			public Float convert(Object value) {
				return Float.parseFloat((String) value);
			}
		});
		addConverter(Number.class, float.class, new Converter() {
			public Float convert(Object value) {
				return ((Number) value).floatValue();
			}
		});
		addConverter(String.class, double.class, new Converter() {
			public Double convert(Object value) {
				return Double.parseDouble((String) value);
			}
		});
		addConverter(Number.class, double.class, new Converter() {
			public Double convert(Object value) {
				return ((Number) value).doubleValue();
			}
		});
		addConverter(String.class, Date.class, new Converter() {
			public Date convert(Object value) {
				String delimeter = ".";
				String text = ((String) value).replaceAll("/", delimeter)
						.replace("-", delimeter).replaceAll(" ", delimeter);
				try {
					return DATE_FORMAT.parse(text);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}
