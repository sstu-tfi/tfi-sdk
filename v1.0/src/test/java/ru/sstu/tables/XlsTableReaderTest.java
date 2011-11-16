package ru.sstu.tables;

/**
 * <code>XlsTableReaderTest</code> class is unit test for
 * {@link XlsTableReader}.
 *
 * @author Denis_Murashev
 * @since Tables 1.0
 */
public class XlsTableReaderTest extends TableReaderTest {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected TableReader getReader() {
		return new XlsTableReader();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPath() {
		return "/test.xls";
	}
}
