package ru.sstu.tables;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <code>TableIndex</code> annotation sets index of table in multiple tables
 * source to be used. Indexes start with 0.
 *
 * @author Denis_Murashev
 * @since Tables 1.1
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TableIndex {

	/**
	 * Index of table to be used.
	 */
	int value() default 0;
}
