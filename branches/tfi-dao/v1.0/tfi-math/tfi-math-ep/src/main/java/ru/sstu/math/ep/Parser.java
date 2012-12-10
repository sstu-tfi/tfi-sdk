package ru.sstu.math.ep;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math.MathException;

/**
 * <code>Parser</code> class parses string as mathematical expression, builds
 * tree and creates {@link Expression}.
 *
 * @author Denis A. Murashev
 * @since Math 1.0
 */
public class Parser {

	/**
	 * Closed bracket.
	 */
	private static final String CLOSED_BRACKET = ")";

	/**
	 * Open bracket.
	 */
	private static final String OPEN_BRACKET = "(";

	/**
	 * Compiles string into expression.
	 *
	 * @param expression expression
	 * @return compiled expression
	 * @throws MathException if cannot compile expression
	 */
	public Expression compile(String expression) throws MathException {
		if (expression == null || expression.trim().length() == 0) {
			throw new EmptyExpressionException();
		}
		List<Token> tokens = getTokens(expression.toLowerCase());
		final int offset = 4;
		int brackets = 0;
		for (Token token : tokens) {
			if (token.token.equals(OPEN_BRACKET)) {
				brackets++;
			} else if (token.token.equals(CLOSED_BRACKET)) {
				brackets--;
				if (brackets < 0) {
					throw new UnexpectedBracketException();
				}
			} else {
				processToken(token, brackets, offset);
			}
		}
		if (brackets > 0) {
			throw new NoBracketException();
		}
		return new Expression(createNode(tokens, 0, tokens.size() - 1));
	}

	/**
	 * Processing of token.
	 *
	 * @param token    token
	 * @param brackets count of brackets
	 * @param offset   offset
	 * @throws MathException if some error occurs
	 */
	private static void processToken(Token token, int brackets, int offset)
			throws MathException {
		AbstractNode node = BinaryNodeUtil.getNode(token.token);
		if (node != null) {
			token.setPriority(((BinaryNode) node).getPriority()
					+ offset * brackets);
			token.setNode(node);
			return;
		}
		node = FunctionNodeUtil.getNode(token.token);
		if (node != null) {
			token.setPriority(offset * (brackets + 1));
			token.setNode(node);
			return;
		}
		node = ValueNodeUtil.getNode(token.token);
		if (node != null) {
			token.setNode(node);
		}
	}

	/**
	 * Compiles expression.
	 *
	 * @param expression expression
	 * @param variables  variables
	 * @return compiled expression
	 * @throws MathException if cannot compile expression
	 */
	public Expression compile(String expression, Iterable<String> variables)
			throws MathException {
		ValueNodeUtil.setVariables(variables);
		return compile(expression);
	}

	/**
	 * Extracts tokens.
	 *
	 * @param expression expression
	 * @return list of tokens
	 */
	private static List<Token> getTokens(String expression) {
		List<Token> list = new ArrayList<Token>();
		int position = 0;
		while (position < expression.length()) {
			String current = expression.substring(position);
			Pattern pattern = Pattern.compile("[\\+\\-\\*/%\\^\\(\\) ]");
			Matcher matcher = pattern.matcher(current);
			if (matcher.find()) {
				String group = matcher.group();
				int index = current.indexOf(group);
				if (index > 0) {
					position += index;
					list.add(new Token(current.substring(0, index).trim()));
				}
				position += group.length();
				if (group.trim().length() != 0) {
					list.add(new Token(group.trim()));
				}
			} else {
				list.add(new Token(current.trim()));
				return list;
			}
		}
		return list;
	}

	/**
	 * Creates node.
	 *
	 * @param tokens list of tokens
	 * @param start  start index
	 * @param finish end index
	 * @return node
	 * @throws MathException if some error occurs
	 */
	private AbstractNode createNode(List<Token> tokens, int start, int finish)
			throws MathException {
		int begin = start;
		int end = finish;
		if (begin > end) {
			return null;
		}

		while (begin < tokens.size()
				&& tokens.get(begin).token.equals(OPEN_BRACKET)) {
			begin++;
		}
		while (end > 0 && tokens.get(end).token.equals(CLOSED_BRACKET)) {
			end--;
		}

		if (begin > end) {
			return Constants.ZERO_NODE;
		}

		int minIndex = begin;
		for (int i = begin + 1; i <= end; i++) {
			if (tokens.get(minIndex).compareTo(tokens.get(i)) >= 0) {
				minIndex = i;
			}
		}

		AbstractNode node = tokens.get(minIndex).node;
		AbstractNode left = createNode(tokens, begin, minIndex - 1);
		AbstractNode right = createNode(tokens, minIndex + 1, end);
		node.setChildren(new AbstractNode[]{left, right});
		return node;
	}

	/**
	 * Token wrapper.
	 *
	 * @author Denis_Murashev
	 */
	private static class Token implements Comparable<Token> {

		/**
		 * Priority.
		 */
		private Integer priority;

		/**
		 * Token.
		 */
		private String token;

		/**
		 * Parser node.
		 */
		private AbstractNode node;

		/**
		 * Initializes token wrapper.
		 *
		 * @param token token
		 */
		Token(String token) {
			this.token = token;
		}

		/**
		 * Sets priority.
		 *
		 * @param priority priority
		 */
		void setPriority(Integer priority) {
			this.priority = priority;
		}

		/**
		 * Sets node.
		 *
		 * @param node node
		 */
		void setNode(AbstractNode node) {
			this.node = node;
		}

		/**
		 * {@inheritDoc}
		 */
		public int compareTo(Token o) {
			if (priority == null) {
				return 1;
			}
			if (o.priority == null) {
				return -1;
			}
			return priority - o.priority;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			Token other = (Token) obj;
			return node != null && node.equals(other.node)
					&& priority != null && priority.equals(other.priority)
					&& token != null && token.equals(other.token);

		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = priority != null ? priority.hashCode() : 0;
			result = prime * result + (token != null ? token.hashCode() : 0);
			result = prime * result + (node != null ? node.hashCode() : 0);
			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return token;
		}
	}

	/**
	 * Cannot compile empty string.
	 *
	 * @author Denis_Murashev
	 */
	public static class EmptyExpressionException extends MathException {

		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = -5972989832446543113L;
	}

	/**
	 * Unexpected closing bracket.
	 *
	 * @author Denis_Murashev
	 */
	public static class UnexpectedBracketException extends MathException {

		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = -3062687007920719885L;
	}

	/**
	 * Closing bracket expected.
	 *
	 * @author Denis_Murashev
	 */
	public static class NoBracketException extends MathException {

		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = 6818532168478438870L;
	}
}
