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
			setTableIndex(type.getAnnotation(TableIndex.class).value());
		}
		if (type.isAnnotationPresent(SkipRows.class)) {
			setSkipRows(type.getAnnotation(SkipRows.class).value());
		}
		setClass(type);
		for (Field field : type.getDeclaredFields()) {
			if (field.isAnnotationPresent(ColumnIndex.class)) {
				ColumnIndex annotation = field.getAnnotation(ColumnIndex.class);
				int index = annotation.value();
				String value = annotation.defaultValue();
				addField(index, field, value);
				checkIndex(index);
			}
		}
	}
}
