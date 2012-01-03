package ru.sstu.properties.core;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <code>Property</code> annotation marks bean properties.
 *
 * @author Denis_Murashev
 * @since Properties 1.0
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Property {

	/**
	 * Property name.
	 */
	String value();
}
