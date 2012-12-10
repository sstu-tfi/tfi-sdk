package ru.sstu.tables;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import au.com.bytecode.opencsv.CSVReader;

/**
 * <code>CsvTableReader</code> class provides data read from CSV file.
 *
 * @author Denis_Murashev
 * @since Tables 1.0
 */
public class CsvTableReader extends FileTableReader {

	/**
	 * CSV reader.
	 */
	private CSVReader reader;

	/**
	 * Current table row.
	 */
	private String[] currentRow;

	/**
	 * Next table row.
	 */
	private String[] nextRow;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void init(InputStream input, int index) throws TableException {
		try {
			reader = new CSVReader(new InputStreamReader(input));
			nextRow = reader.readNext();
		} catch (IOException e) {
			throw new TableException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void nextLine() throws TableException {
		try {
			currentRow = nextRow;
			nextRow = reader.readNext();
		} catch (IOException e) {
			throw new TableException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean hasNextLine() {
		return nextRow != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object nextValue(int index) throws TableException {
		if (index < currentRow.length) {
			return currentRow[index];
		}
		return null;
	}
}
