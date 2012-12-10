package ru.sstu.properties.core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <code>PropertyManager</code> class provides mechanisms for properties
 * loading into settings objects and vice versa.
 *
 * The subclass of <code>AbstractSettings</code> should contain fields
 * marked with {@link Property} annotation.
 *
 * @author Denis_Murashev
 * @since Properties 1.0
 */
public class PropertyHelper {

	private static final Map<Class<?>, Converter<?>> CONVERTERS
			= new HashMap<Class<?>, Converter<?>>();
	static {
		CONVERTERS.put(int.class, new IntegerConverter());
		CONVERTERS.put(long.class, new LongConverter());
		CONVERTERS.put(float.class, new FloatConverter());
		CONVERTERS.put(double.class, new DoubleConverter());
		CONVERTERS.put(String.class, new StringConverter());
	}

	/**
	 * Loads given properties into settings object.
	 *
	 * @param object settings holder
	 * @param input  input stream
	 * @param <T>    type of settings holder
	 * @return settings holder with updated values
	 * @throws PropertyException if properties cannot be loaded
	 */
	public static <T> T load(T object, InputStream input)
			throws PropertyException {
		try {
			Properties properties = new Properties();
			properties.load(input);
			return load(object, properties);
		} catch (IOException e) {
			throw new PropertyException(e);
		}
	}

	/**
	 * Loads given properties into settings object.
	 *
	 * @param object     settings holder
	 * @param properties properties
	 * @param <T>        type of settings holder
	 * @return settings holder with updated values
	 * @throws PropertyException if properties cannot be loaded
	 */
	public static <T> T load(T object, Map<Object, Object> properties)
			throws PropertyException {
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field f : fields) {
				Property annotation = f.getAnnotation(Property.class);
				if (annotation != null) {
					String text = properties.get(annotation.value()).toString();
					Converter<?> converter = CONVERTERS.get(f.getType());
					if (converter != null) {
						Object value = converter.convert(text);
						f.setAccessible(true);
						f.set(object, value);
					}
				}
			}
			return object;
		} catch (IllegalArgumentException e) {
			throw new PropertyException(e);
		} catch (IllegalAccessException e) {
			throw new PropertyException(e);
		}
	}

	/**
	 * Provides properties as {@link Map} object for given settings object.
	 *
	 * @param object settings holder
	 * @return properties
	 * @throws PropertyException if properties cannot be generated
	 */
	public static Map<Object, Object> toProperties(Object object)
			throws PropertyException {
		try {
			Map<Object, Object> properties
					= new LinkedHashMap<Object, Object>();
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field f : fields) {
				Property annotation = f.getAnnotation(Property.class);
				if (annotation != null) {
					String name = annotation.value();
					f.setAccessible(true);
					String value = f.get(object).toString();
					properties.put(name, value);
				}
			}
			return properties;
		} catch (IllegalArgumentException e) {
			throw new PropertyException(e);
		} catch (IllegalAccessException e) {
			throw new PropertyException(e);
		}
	}

	/**
	 * Adds converter for given class.
	 *
	 * @param type      class
	 * @param converter converter
	 */
	protected void setConverter(Class<?> type, Converter<?> converter) {
		CONVERTERS.put(type, converter);
	}

	/**
	 * <code>Converter</code> interface represents converter from {@link String}
	 * to given type <code>T</code>.
	 *
	 * @author Denis_Murashev
	 *
	 * @param <T> type of converter
	 */
	public interface Converter<T> {

		/**
		 * Converts given String value to specified type.
		 *
		 * @param value string value
		 * @return converted value
		 */
		T convert(String value);
	}

	private static class IntegerConverter implements Converter<Integer> {
		@Override
		public Integer convert(String value) {
			return Integer.valueOf(value);
		}
	}

	private static class LongConverter implements Converter<Long> {
		@Override
		public Long convert(String value) {
			return Long.valueOf(value);
		}
	}

	private static class FloatConverter implements Converter<Float> {
		@Override
		public Float convert(String value) {
			return Float.valueOf(value);
		}
	}

	private static class DoubleConverter implements Converter<Double> {
		@Override
		public Double convert(String value) {
			return Double.valueOf(value);
		}
	}

	private static class StringConverter implements Converter<String> {
		@Override
		public String convert(String value) {
			return value;
		}
	}
}
