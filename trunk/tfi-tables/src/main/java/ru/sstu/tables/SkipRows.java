package ru.sstu.tables;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <code>SkipRows</code> annotation sets count of table rows to be skipped.
 *
 * @author Denis_Murashev
 * @since Tables 1.1
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipRows {

	/**
	 * Rows to be skipped.
	 */
	int value() default 0;
}
