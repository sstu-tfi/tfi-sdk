package ru.sstu.tables;

/**
 * <code>Converter</code> interface describes object for converting from one
 * type to another.
 *
 * @author Denis_Murashev
 *
 */
public interface Converter {

	/**
	 * Converts from one type to another.
	 *
	 * @param value value to be converted
	 * @return converted value
	 */
	Object convert(Object value);
}
