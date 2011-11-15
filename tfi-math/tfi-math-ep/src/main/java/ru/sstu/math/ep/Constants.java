package ru.sstu.math.ep;

import java.util.HashSet;
import java.util.Set;

/**
 * <code>Constants</code> class provides constants for ru.sstu.math.ep package.
 *
 * @author Denis_Murashev
 * @since Math 2.0
 */
final class Constants {

	/**
	 * Just empty set.
	 */
	static final Set<String> EMPTY_SET = new HashSet<String>();

	/**
	 * Zero node.
	 */
	static final AbstractNode ZERO_NODE = new ConstantNode();

	/**
	 * No instances needed.
	 */
	private Constants() {
	}
}
