package ru.sstu.tables;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <code>ColumnIndex</code> annotation is used for marking Java Bean fields
 * as columns in table.
 *
 * @author Denis_Murashev
 * @since Tables 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnIndex {

	/**
	 * Index of column.
	 */
	int value();
}
