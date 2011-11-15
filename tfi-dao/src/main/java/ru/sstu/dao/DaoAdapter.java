package ru.sstu.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>DaoAdapter</code> class is used for DAO adaptation.
 * It suppose that adaptee objects cannot be changed.
 *
 * @author Denis_Murashev
 *
 * @param <T> adapter type
 * @param <U> adaptee type
 * @since DAO 1.0
 */
public abstract class DaoAdapter<T extends Identifiable, U extends Identifiable>
		implements Dao<T> {

	/**
	 * Adapter DAO in case of adapter objects cannot be changed.
	 */
	private final Dao<T> adapterDao = new AdapterDao();

	/**
	 * {@inheritDoc}
	 */
	public List<T> find() {
		List<T> entities = new ArrayList<T>();
		for (U entity : getAdapteeDao().find()) {
			entities.add(convert(entity));
		}
		entities.addAll(getAdapterDao().find());
		return entities;
	}

	/**
	 * {@inheritDoc}
	 */
	public T find(long id) {
		IdAdapter adapter = getIdAdapterDao().find(id);
		if (adapter == null) {
			return null;
		}
		if (adapter.getAdapteeId() == null) {
			return getAdapterDao().find(id);
		}
		return convert(getAdapteeDao().find((Long) adapter.getAdapteeId()));
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(T entity) {
		getAdapterDao().save(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(T entity) {
		getAdapterDao().delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(long id) {
		getAdapterDao().delete(id);
	}

	/**
	 * This method should be overridden if adapter objects is persistent.
	 *
	 * @return adapter DAO which should never be <code>null</code>
	 */
	protected Dao<T> getAdapterDao() {
		return adapterDao;
	}

	/**
	 * @return adaptee DAO
	 */
	protected abstract Dao<U> getAdapteeDao();

	/**
	 * @return DAO for objects that can link ids of adapter and adaptee
	 */
	protected abstract Dao<? extends IdAdapter> getIdAdapterDao();

	/**
	 * Converts adaptee entity to adaptor entity.
	 *
	 * @param entity adaptee
	 * @return adapter
	 */
	protected abstract T convert(U entity);

	/**
	 * Adapter DAO for objects which cannot be changed.
	 *
	 * @author Denis_Murashev
	 * @since DAO 1.1
	 */
	private final class AdapterDao implements Dao<T> {

		/**
		 * {@inheritDoc}
		 */
		public List<T> find() {
			return new ArrayList<T>();
		}

		/**
		 * {@inheritDoc}
		 */
		public T find(long id) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public void save(T entity) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void save(List<T> entities) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void delete(T entity) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void delete(long id) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void delete(List<T> entities) {
		}
	}
}
