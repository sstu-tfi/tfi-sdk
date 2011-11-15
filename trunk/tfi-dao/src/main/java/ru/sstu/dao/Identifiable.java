package ru.sstu.dao;

/**
 * Interface <code>Identifiable</code> represents any entity with id.
 *
 * @author Denis_Murashev
 * @since DAO 1.0
 */
public interface Identifiable {

	/**
	 * Provides entity's unique identifier.
	 *
	 * @return the id
	 */
	Object getId();
}
