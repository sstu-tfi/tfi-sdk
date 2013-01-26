package ru.sstu.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * {@code EnumDao} class is most common DAO for enumerations.
 * It relies on object id is its number (starting with 0) in enumeration.
 *
 * @author Denis_Murashev
 * @param <T> concrete entity class
 * @since DAO 1.0
 */
public abstract class EnumDao<T extends Serializable>
		implements ReadOnlyDao<T> {

	@Override
	public List<T> find() {
		return Arrays.asList(findAll());
	}

	@Override
	public T findById(Serializable id) {
		return findAll()[((Number) id).intValue()];
	}

	/**
	 * Provides array of all objects in enumeration.
	 *
	 * @return array of all objects
	 */
	public abstract T[] findAll();
}
