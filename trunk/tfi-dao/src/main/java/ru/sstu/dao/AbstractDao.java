package ru.sstu.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
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

	private Order[] orders;

	/**
	 * Initializes DAO properties.
	 */
	protected AbstractDao() {
		this(new Order[0]);
	}

	/**
	 * Initializes DAO properties.
	 *
	 * @param orders sort orders
	 */
	@SuppressWarnings("unchecked")
	protected AbstractDao(Order... orders) {
		this.type = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.orders = orders;
	}

	/**
	 * Returns all entities from data source.
	 *
	 * @return all entities from data source.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find() {
		if (orders.length == 0) {
			return getTemplate().loadAll(type);
		}
		DetachedCriteria criteria = getCriteria();
		for (Order o : orders) {
			criteria.addOrder(o);
		}
		return getTemplate().findByCriteria(criteria);
	}

	/**
	 * Returns entity with given id.
	 *
	 * @param id entity's id
	 * @return entity
	 */
	@Override
	public T findById(Serializable id) {
		return getTemplate().get(type, id);
	}

	/**
	 * Saves entity.
	 *
	 * @param entity entity
	 */
	@Override
	public void save(T entity) {
		getTemplate().saveOrUpdate(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(List<T> entities) {
		getTemplate().saveOrUpdateAll(entities);
	}

	/**
	 * Removes entity.
	 *
	 * @param entity entity
	 */
	@Override
	public void delete(T entity) {
		getTemplate().delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
	 * @param <U> concrete entity class
	 * @param criteria criteria
	 * @return unique entity
	 */
	@SuppressWarnings("unchecked")
	protected <U> U unique(DetachedCriteria criteria) {
		List<U> list = getTemplate().findByCriteria(criteria);
		return list.size() == 1 ? list.get(0) : null;
	}

	/**
	 * Provides list of entities.
	 *
	 * @param <U> concrete entity class
	 * @param criteria search criteria
	 * @return list of entities
	 */
	@SuppressWarnings("unchecked")
	protected <U> List<U> list(DetachedCriteria criteria) {
		for (Order o : orders) {
			criteria.addOrder(o);
		}
		return getTemplate().findByCriteria(criteria);
	}

	/**
	 * Provides result of aggregate function in query.
	 *
	 * @param criteria criteria
	 * @return numeric result
	 */
	protected Number aggregate(DetachedCriteria criteria) {
		return (Number) getTemplate().findByCriteria(criteria).get(0);
	}

	/**
	 * Executes HQL query.
	 *
	 * @param query query
	 * @return list of found entities
	 */
	@SuppressWarnings("unchecked")
	protected List<T> hql(String query) {
		return getTemplate().find(query);
	}

	/**
	 * Executes HQL query.
	 *
	 * @param query query
	 * @param value value
	 * @return list of found entities
	 */
	@SuppressWarnings("unchecked")
	protected List<T> hql(String query, Object value) {
		return getTemplate().find(query, value);
	}

	/**
	 * Executes HQL query.
	 *
	 * @param query  query
	 * @param values values
	 * @return list of found entities
	 */
	@SuppressWarnings("unchecked")
	protected List<T> hql(String query, Object... values) {
		return getTemplate().find(query, values);
	}

	/**
	 * Saves entity which is not of type <code>T</code>.
	 *
	 * @param entity entity to be saved
	 */
	protected void save(Object entity) {
		getTemplate().saveOrUpdate(entity);
	}

	/**
	 * Deletes entity which is not of type <code>T</code>.
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
