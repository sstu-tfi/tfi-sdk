package ru.sstu.tables;

/**
 * <code>XlsxTableReaderTest</code> class is unit test for
 * {@link XlsxTableReader}.
 *
 * @author Denis_Murashev
 * @since Tables 1.0
 */
public class XlsxTableReaderTest extends TableReaderTest {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected TableReader getReader() {
		return new XlsxTableReader();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPath() {
		return "/test.xlsx";
	}
}
