package ru.sstu.tables;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>Settings</code> class contains general {@link TableReader settings}.
 *
 * @author Denis_Murashev
 * @since Tables 1.1
 */
final class Settings {

	/**
	 * Registered converters.
	 */
	private final Map<ConverterType, Converter> converters
			= new HashMap<ConverterType, Converter>();

	/**
	 * Date format string.
	 */
	private String dateFormat = "dd.MM.yyyy";

	/**
	 * Adds new converter.
	 *
	 * @param fromClass from class
	 * @param toClass   to class
	 * @param converter converter
	 */
	public void addConverter(Class<?> fromClass, Class<?> toClass,
			Converter converter) {
		converters.put(new ConverterType(fromClass, toClass), converter);
	}

	/**
	 * Provides desired converter.
	 *
	 * @param fromClass from class
	 * @param toClass   to class
	 * @return converter
	 * @throws TableException if converter does not exist
	 */
	public Converter getConverter(Class<?> fromClass, Class<?> toClass)
			throws TableException {
		ConverterType type = new ConverterType(fromClass, toClass);
		Converter converter = converters.get(type);
		if (converter != null) {
			return converter;
		}
		Class<?> superClass = fromClass.getSuperclass();
		while (!Object.class.equals(superClass)) {
			type = new ConverterType(superClass, toClass);
			converter = converters.get(type);
			if (converter != null) {
				return converter;
			}
			superClass = superClass.getSuperclass();
		}
		for (Class<?> i : fromClass.getInterfaces()) {
			type = new ConverterType(i, toClass);
			converter = converters.get(type);
			if (converter != null) {
				return converter;
			}
		}
		throw new TableException("Cannot conver value from " + fromClass
				+ " to " + toClass);
	}

	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * <code>ConverterType</code> class describes converter type.
	 *
	 * @author Denis_Murashev
	 * @since Tables 1.1
	 */
	private static final class ConverterType {

		/**
		 * From class.
		 */
		private final Class<?> fromClass;

		/**
		 * To class.
		 */
		private final Class<?> toClass;

		/**
		 * @param fromClass from class
		 * @param toClass   to class
		 */
		private ConverterType(Class<?> fromClass, Class<?> toClass) {
			this.fromClass = fromClass;
			this.toClass = toClass;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = prime + fromClass.hashCode();
			return prime * result + toClass.hashCode();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ConverterType other = (ConverterType) obj;
			return fromClass.equals(other.fromClass)
					&& toClass.equals(other.toClass);
		}
	}

	{
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
				DateFormat format = new SimpleDateFormat(dateFormat);
				String delimeter = ".";
				String text = ((String) value).replaceAll("/", delimeter)
						.replace("-", delimeter).replaceAll(" ", delimeter);
				try {
					return format.parse(text);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}
