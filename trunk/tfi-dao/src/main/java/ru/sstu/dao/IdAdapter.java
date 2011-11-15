package ru.sstu.dao;

/**
 * <code>IdAdapter</code> interface describes objects which can be used for
 * dictionary data adaptation.
 *
 * @author Denis_Murashev
 * @since DAO 1.0
 */
public interface IdAdapter extends Identifiable {

	/**
	 * @return id of adaptee object
	 */
	Object getAdapteeId();
}
