package ru.sstu.tables;

import java.io.InputStream;
import java.util.List;

/**
 * <code>TableReader</code> interface represents abstract table data reader.
 *
 * @author Denis A. Murashev
 * @since Tables 1.0
 */
public interface TableReader {

	/**
	 * Reads table data from given input stream.
	 *
	 * @param <T> type of table row
	 * @param mapping mapping for class and table
	 * @param input   input stream for table data
	 * @return list of object read from input stream
	 * @throws TableException if cannot read data
	 */
	<T> List<T> read(Mapping<T> mapping, InputStream input)
			throws TableException;

	/**
	 * Reads table data from given input stream.
	 *
	 * @param <T> type of table row
	 * @param mapping    mapping for class and table
	 * @param input      input stream for table data
	 * @param tableIndex index of table to be read (if input stream has more
	 *                   then 1 table)
	 * @return list of objects read from input stream
	 * @throws TableException if cannot read data
	 */
	<T> List<T> read(Mapping<T> mapping, InputStream input, int tableIndex)
			throws TableException;

	/**
	 * Skips data of size <code>count</code>.
	 *
	 * @param count data to be skipped
	 * @throws TableException if cannot skip data
	 * @deprecated use {@link Mapping#setSkipRows(int)} method
	 */
	@Deprecated
	void skip(int count) throws TableException;

	/**
	 * Adds support for converting values from <code>fromClass</code> to
	 * <code>toClass</code>.
	 *
	 * @param fromClass from class
	 * @param toClass   to class
	 * @param converter converter
	 */
	void addConverter(Class<?> fromClass, Class<?> toClass,
			Converter converter);

	/**
	 * Provides converter for given types.
	 *
	 * @param fromClass from class
	 * @param toClass   to class
	 * @return converter
	 * @throws TableException if desired converter does not exist
	 * @see TableReader#addConverter(Class, Class, Converter)
	 * @since Tables 1.1
	 */
	Converter getConverter(Class<?> fromClass, Class<?> toClass)
			throws TableException;

	/**
	 * Sets used date format.
	 *
	 * @param dateFormat date format
	 * @see java.text.SimpleDateFormat
	 * @since Tables 1.1
	 */
	void setDateFormat(String dateFormat);
}
