package ru.sstu.tables;

import java.lang.reflect.Array;

/**
 * <code>ArrayMapping</code> class is used for mapping between array elements
 * and columns of table.
 *
 * @author Denis_Murashev
 * @param <T> Java Bean type
 * @since Tables 1.0
 */
public class ArrayMapping<T> extends Mapping<T> {

	/**
	 * Array length.
	 */
	private int length;

	/**
	 * Initializes mapping.
	 *
	 * @param type   array type (String[].class, for example)
	 * @param length array length
	 * @throws TableException if cannot map array
	 */
	public ArrayMapping(Class<T> type, int length) throws TableException {
		if (!type.isArray()) {
			throw new TableException("Only array types supported");
		}
		this.length = length;
		setClass(type);
		checkIndex(length - 1);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T createObject() throws TableException {
		return (T) Array.newInstance(getType().getComponentType(), length);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(T object, int index, Object value)
			throws TableException {
		Class<?> fromClass = value.getClass();
		Class<?> toClass = getType().getComponentType();
		if (toClass == fromClass) {
			Array.set(object, index, value);
		} else {
			Converter converter = getTableReader().getConverter(fromClass,
					toClass);
			Array.set(object, index, converter.convert(value));
		}
	}
}
