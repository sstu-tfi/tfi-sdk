package ru.sstu.tables;

/**
 * <code>DefaultValuesTest</code> class is unit test for default values.
 *
 * @author Denis_Murashev
 * @since Tables 1.1
 */
public class DefaultValuesTest extends TableReaderTest {

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
		return "/default.csv";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected SampleEntity[] getExpected() {
		SampleEntity defaultEntity = new SampleEntity("unknown", 1, 1.0,
				getSampleDate());
		return new SampleEntity[] {
			defaultEntity,
			defaultEntity,
		};
	}
}
