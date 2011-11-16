package ru.sstu.tables;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

/**
 * <code>TableReaderTest</code> class is common unit test for different
 * {@link TableReader} implementations.
 *
 * @author Denis_Murashev
 * @since Tables 1.0
 */
public abstract class TableReaderTest extends TestCase {

	/**
	 * Path to mapping.
	 */
	private static final String MAPPING_PATH = "/SampleEntity.map.xml";

	/**
	 * Expected values.
	 */
	private static final SampleEntity[] EXPECTED = {
		new SampleEntity("text", 1, 1.0, getSampleDate()),
		new SampleEntity("next text", 2, 2.0, getSampleDate()),
		new SampleEntity("12.0", 3, 3.0, getSampleDate()),
	};

	/**
	 * Table reader.
	 */
	private TableReader reader;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		reader = getReader();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Tests {@link TableReader#read(Class, InputStream)} method.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testReadAnnotated() throws Exception {
		InputStream input = getClass().getResourceAsStream(getPath());
		Mapping<SampleEntity> mapping
				= new AnnotationMapping<SampleEntity>(SampleEntity.class);
		checkValues(reader.read(mapping, input));
	}

	/**
	 * Tests {@link TableReader#read(InputStream, InputStream)} method.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testReadXml() throws Exception {
		InputStream input = getClass().getResourceAsStream(getPath());
		Mapping<SampleEntity> mapping = new XmlMapping<SampleEntity>(getClass()
				.getResourceAsStream(MAPPING_PATH));
		checkValues(reader.read(mapping, input));
	}

	/**
	 * Tests {@link TableReader#read(Class, InputStream)} method.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testSkipAnnotated() throws Exception {
		InputStream input = getClass().getResourceAsStream(getPath());
		AnnotationMapping<SampleEntity> mapping
				= new AnnotationMapping<SampleEntity>(SampleEntity.class);
		final int skipRows = 1;
		mapping.setSkipRows(skipRows);
		checkValues(reader.read(mapping, input), skipRows);
	}

	/**
	 * Tests {@link TableReader#read(InputStream, InputStream)} method.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testSkipXml() throws Exception {
		InputStream input = getClass().getResourceAsStream(getPath());
		Mapping<SampleEntity> mapping = new XmlMapping<SampleEntity>(getClass()
				.getResourceAsStream(MAPPING_PATH));
		final int skipRows = 1;
		mapping.setSkipRows(skipRows);
		checkValues(reader.read(mapping, input), skipRows);
	}

	/**
	 * @return concrete reader
	 */
	protected abstract TableReader getReader();

	/**
	 * @return path to data file
	 */
	protected abstract String getPath();

	/**
	 * Checks actual values.
	 *
	 * @param table actual table values
	 */
	private void checkValues(List<SampleEntity> table) {
		checkValues(table, 0);
	}

	/**
	 * Checks actual values.
	 *
	 * @param table actual table values
	 * @param skip  rows to be skipped
	 */
	private void checkValues(List<SampleEntity> table, int skip) {
		assertEquals(EXPECTED.length - skip, table.size());
		for (int i = 0; i < EXPECTED.length - skip; i++) {
			assertEquals(EXPECTED[i + skip], table.get(i));
		}
	}

	/**
	 * Checks if two entities are equal.
	 *
	 * @param expected expected entity
	 * @param actual   actual entity
	 */
	private void assertEquals(SampleEntity expected, SampleEntity actual) {
		assertEquals(expected.getText(), actual.getText());
		assertEquals(expected.getNumber(), actual.getNumber());
		assertEquals(expected.getValue(), actual.getValue());
		assertEquals(expected.getDate(), actual.getDate());
	}

	/**
	 * Provides sample date.
	 *
	 * @return sample date
	 */
	private static Date getSampleDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		final int day = 10;
		final int month = 10;
		final int year = 2010;
		calendar.set(year, month - 1, day);
		return calendar.getTime();
	}
}
