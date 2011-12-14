package ru.sstu.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * <code>AbstractDao</code> class contains generic methods for data access.
 *
 * @author Denis_Murashev
 *
 * @param <T> concrete entity class
 * @since DAO 1.0
 */
public abstract class AbstractDao<T extends Serializable> implements Dao<T> {

	/**
	 * Concrete entity class.
	 */
	private final Class<T> type;

	/**
	 * Initializes DAO properties.
	 */
	@SuppressWarnings("unchecked")
	protected AbstractDao() {
		this.type = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Returns all entities from data source.
	 *
	 * @return all entities from data source.
	 */
	public List<T> find() {
		return (List<T>) getTemplate().loadAll(type);
	}

	/**
	 * Returns entity with given id.
	 *
	 * @param id entity's id
	 * @return entity
	 */
	public T findById(Serializable id) {
		return (T) getTemplate().get(type, id);
	}

	/**
	 * Saves entity.
	 *
	 * @param entity entity
	 */
	public void save(T entity) {
		getTemplate().saveOrUpdate(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(List<T> entities) {
		getTemplate().saveOrUpdateAll(entities);
	}

	/**
	 * Removes entity.
	 *
	 * @param entity entity
	 */
	public void delete(T entity) {
		getTemplate().delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(List<T> entities) {
		getTemplate().deleteAll(entities);
	}

	/**
	 * Provides search criteria which can be used for searches with criteria.
	 *
	 * @return detached criteria
	 */
	protected DetachedCriteria getCriteria() {
		return DetachedCriteria.forClass(type);
	}

	/**
	 * Provides unique entity with given criteria.
	 *
	 * @param criteria criteria
	 * @return unique entity
	 */
	@SuppressWarnings("unchecked")
	protected T unique(DetachedCriteria criteria) {
		List<T> list = getTemplate().findByCriteria(criteria);
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * Provides list of entities.
	 *
	 * @param criteria search criteria
	 * @return list of entities
	 */
	@SuppressWarnings("unchecked")
	protected List<T> list(DetachedCriteria criteria) {
		return getTemplate().findByCriteria(criteria);
	}

	/**
	 * Provides result of aggregate function in query.
	 *
	 * @param criteria criteria
	 * @return numeric result
	 */
	protected Number aggregate(DetachedCriteria criteria) {
		Number number = (Number) getTemplate().findByCriteria(criteria).get(0);
		return number != null ? number : 0;
	}

	/**
	 * Saves entity which is not <code>Identifiable</code>.
	 *
	 * @param entity entity to be saved
	 */
	protected void save(Object entity) {
		getTemplate().saveOrUpdate(entity);
	}

	/**
	 * Deletes entity which is not <code>Identifiable</code>.
	 *
	 * @param entity entity to be deleted
	 */
	protected void delete(Object entity) {
		getTemplate().delete(entity);
	}

	/**
	 * Deletes entities by criteria.
	 *
	 * @param criteria criteria for entities to be deleted
	 */
	protected void delete(DetachedCriteria criteria) {
		getTemplate().deleteAll(getTemplate().findByCriteria(criteria));
	}

	/**
	 * Provides actual HibernateTemplate object.
	 *
	 * @return HibernateTemplate object
	 */
	protected abstract HibernateTemplate getTemplate();
}
