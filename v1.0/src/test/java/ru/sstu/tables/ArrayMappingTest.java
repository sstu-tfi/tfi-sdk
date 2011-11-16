package ru.sstu.tables;

import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

/**
 * <code>ArrayMappingTest</code> class is unit test for array mappings.
 *
 * @author Denis_Murashev
 * @since Tables 1.0
 */
public class ArrayMappingTest extends TestCase {

	/**
	 * Expected data.
	 */
	private static final String[][] EXPECTED = {
			{"1",   "2",   "3",     "4",    "5"},
			{"one", "two", "three", "four", "five"},
	};

	/**
	 * Tests reading data from file.
	 *
	 * @throws Exception if some error occurs
	 */
	public void testRead() throws Exception {
		TableReader reader = new CsvTableReader();
		Mapping<String[]> mapping = new ArrayMapping<String[]>(String[].class,
				EXPECTED[0].length);
		InputStream input = getClass().getResourceAsStream("/array.csv");
		List<String[]> actual = reader.read(mapping, input);
		for (int i = 0; i < EXPECTED.length; i++) {
			for (int j = 0; j < EXPECTED[0].length; j++) {
				assertEquals(EXPECTED[i][j], actual.get(i)[j]);
			}
		}
	}
}
