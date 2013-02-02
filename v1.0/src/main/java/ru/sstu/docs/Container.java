package ru.sstu.docs;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * <code>Container</code> class represents abstract node container.
 *
 * @author Denis_Murashev
 *
 * @param <T> node type
 * @since Docs 1.0
 */
class Container<T extends Node> implements List<T> {

	/**
	 * List items.
	 */
	private final List<T> items;

	/**
	 * @param items list items
	 */
	protected Container(List<T> items) {
		this.items = items;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean add(T e) {
		return items.add(e);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addAll(Collection<? extends T> c) {
		return items.addAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		items.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(Object o) {
		return items.contains(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsAll(Collection<?> c) {
		return items.containsAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isEmpty() {
		return items.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterator<T> iterator() {
		return items.iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean remove(Object o) {
		return items.remove(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean removeAll(Collection<?> c) {
		return items.removeAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean retainAll(Collection<?> c) {
		return items.retainAll(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public int size() {
		return items.size();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] toArray() {
		return items.toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	public <U> U[] toArray(U[] a) {
		return items.toArray(a);
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(int index, T element) {
		items.add(index, element);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addAll(int index, Collection<? extends T> c) {
		return items.addAll(index, c);
	}

	/**
	 * {@inheritDoc}
	 */
	public T get(int index) {
		return items.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	public int indexOf(Object o) {
		return items.indexOf(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public int lastIndexOf(Object o) {
		return items.lastIndexOf(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public ListIterator<T> listIterator() {
		return items.listIterator();
	}

	/**
	 * {@inheritDoc}
	 */
	public ListIterator<T> listIterator(int index) {
		return items.listIterator();
	}

	/**
	 * {@inheritDoc}
	 */
	public T remove(int index) {
		return items.remove(index);
	}

	/**
	 * {@inheritDoc}
	 */
	public T set(int index, T element) {
		return items.set(index, element);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> subList(int fromIndex, int toIndex) {
		return items.subList(fromIndex, toIndex);
	}
}
