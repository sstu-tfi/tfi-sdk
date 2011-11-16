package ru.sstu.tables;

import java.lang.reflect.Field;

/**
 * <code>AnnotationMapping</code> class is used for mapping between annotated
 * Java Bean properties and columns of table.
 *
 * @author Denis_Murashev
 * @param <T> Java Bean type
 * @since Tables 1.0
 */
public class AnnotationMapping<T> extends Mapping<T> {

	/**
	 * @param type class of Java Bean to be mapped.
	 */
	public AnnotationMapping(Class<T> type) {
		if (type.isAnnotationPresent(TableIndex.class)) {
			setSkipRows(type.getAnnotation(TableIndex.class).value());
		}
		if (type.isAnnotationPresent(SkipRows.class)) {
			setSkipRows(type.getAnnotation(SkipRows.class).value());
		}
		setClass(type);
		for (Field field : type.getDeclaredFields()) {
			if (field.isAnnotationPresent(ColumnIndex.class)) {
				int index = field.getAnnotation(ColumnIndex.class).value();
				addField(index, field);
				checkIndex(index);
			}
		}
	}
}
