package ru.sstu.tables;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>FileTableReader</code> class is table data reader from file.
 *
 * @author Dmitry_Petrov
 * @author Denis_Murashev
 * @since Tables 1.0
 */
abstract class FileTableReader implements TableReader {

	/**
	 * Count of rows to be skipped.
	 * @deprecated use {@link Mapping#getSkipRows()} instead
	 */
	@Deprecated
	private int skipRows;

	/**
	 * General settings.
	 */
	private Settings settings = new Settings();

	/**
	 * {@inheritDoc}
	 */
	public <T> List<T> read(Mapping<T> mapping, InputStream input)
			throws TableException {
		return read(mapping, input, mapping.getTableIndex());
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> List<T> read(Mapping<T> mapping, InputStream input,
			int tableIndex) throws TableException {
		mapping.setTableReader(this);
		List<T> table = new ArrayList<T>();
		init(input, tableIndex);
		if (skipRows == 0) {
			skipRows = mapping.getSkipRows();
		}
		for (int i = 0; i < skipRows; i++) {
			nextLine();
		}
		while (hasNextLine()) {
			nextLine();
			T row = mapping.createObject();
			for (int i = 0; i <= mapping.getMaxIndex(); i++) {
				mapping.setValue(row, i, nextValue(i));
			}
			table.add(row);
		}
		return table;
	}

	/**
	 * {@inheritDoc}
	 */
	public void skip(int count) {
		this.skipRows = count;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addConverter(Class<?> fromClass, Class<?> toClass,
			Converter converter) {
		settings.addConverter(fromClass, toClass, converter);
	}

	/**
	 * {@inheritDoc}
	 */
	public Converter getConverter(Class<?> fromClass, Class<?> toClass)
			throws TableException {
		return settings.getConverter(fromClass, toClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDateFormat(String dateFormat) {
		settings.setDateFormat(dateFormat);
	}

	/**
	 * Data reader initialization.
	 *
	 * @param input input stream
	 * @param index table index
	 * @throws TableException if cannot initialize data reader
	 */
	protected abstract void init(InputStream input, int index)
			throws TableException;

	/**
	 * Moves reader to next line of data.
	 *
	 * @throws TableException if cannot move reader
	 */
	protected abstract void nextLine() throws TableException;

	/**
	 * @return <code>true</code> if next line of data can be read
	 */
	protected abstract boolean hasNextLine();

	/**
	 * Reads next value.
	 *
	 * @param index value index
	 * @return next value
	 * @throws TableException if cannot read value
	 */
	protected abstract Object nextValue(int index) throws TableException;
}
