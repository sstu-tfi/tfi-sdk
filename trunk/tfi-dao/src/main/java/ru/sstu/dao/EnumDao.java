package ru.sstu.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <code>EnumDao</code> class is most common DAO for enumerations.
 * It relies on object id is its number (beginning with 0) in enumeration.
 *
 * @author Denis_Murashev
 * @param <T> concrete entity class
 * @since DAO 1.0
 */
public abstract class EnumDao<T extends Serializable>
		implements ReadOnlyDao<T> {

	/**
	 * {@inheritDoc}
	 */
	public List<T> find() {
		return Arrays.asList(getAll());
	}

	/**
	 * {@inheritDoc}
	 */
	public T findById(Serializable id) {
		return getAll()[((Number) id).intValue()];
	}

	/**
	 * Provides array of all objects in enumeration.
	 *
	 * @return array of all objects
	 */
	public abstract T[] getAll();
}
