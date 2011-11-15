package ru.sstu.docs;

import java.util.List;

/**
 * <code>Block</code> interface represents block of the document.
 * The block consists of nodes.
 *
 * @param <T> node type
 * @author Denis A. Murashev
 * @author Alexander A. Shatunov
 * @since Docs 1.0
 */
public interface Block<T extends Node> extends List<T>, Node {
}
