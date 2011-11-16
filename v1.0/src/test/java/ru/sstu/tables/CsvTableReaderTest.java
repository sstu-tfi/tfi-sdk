package ru.sstu.tables;

/**
 * <code>CsvTableReaderTest</code> class is unit test for
 * {@link CsvTableReader}.
 *
 * @author Denis_Murashev
 * @since Tables 1.0
 */
public class CsvTableReaderTest extends TableReaderTest {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected TableReader getReader() {
		return new CsvTableReader();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPath() {
		return "/test.csv";
	}
}
