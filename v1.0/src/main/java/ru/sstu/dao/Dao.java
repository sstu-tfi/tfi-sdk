package ru.sstu.dao;

import java.io.Serializable;
import java.util.List;

/**
 * <code>Dao</code> interface is most common DAO interface.
 *
 * @author Denis_Murashev
 * @param <T> concrete entity class
 * @since DAO 1.0
 */
public interface Dao<T extends Serializable> extends ReadOnlyDao<T> {

	/**
	 * <code>save</code> method saves entity.
	 *
	 * @param entity entity for saving
	 */
	void save(T entity);

	/**
	 * <code>save</code> method saves list of entities.
	 *
	 * @param entities entities for saving
	 */
	void save(List<T> entities);

	/**
	 * <code>delete</code> method removes existing entity.
	 *
	 * @param entity entity to be deleted
	 */
	void delete(T entity);

	/**
	 * <code>delete</code> method removes existing entities.
	 *
	 * @param entities entities to be deleted
	 */
	void delete(List<T> entities);
}
