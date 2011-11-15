package ru.sstu.ocr.core;

import java.util.List;

/**
 * <code>CharacterRecognizer</code> class is used for character recognition.
 * @author Denis_Murashev
 */
public class CharacterRecognizer {

	private static final CharacterPattern EMPTY = new CharacterPattern() {
		@Override
		public char getCharacter() {
			return ' ';
		}
		@Override
		public float[][] getPattern() {
			return new float[0][0];
		}
		@Override
		public CharacterType getType() {
			return null;
		}
	};

	private final List<CharacterPattern> patterns;

	/**
	 * Initializes recognizer.
	 *
	 * @param patterns list of character patterns
	 */
	public CharacterRecognizer(List<CharacterPattern> patterns) {
		this.patterns = patterns;
	}

	/**
	 * Recognizes given sample.
	 *
	 * @param sample sample to be recognized
	 * @return recognized pattern
	 */
	public CharacterPattern recognize(CharacterSample sample) {
		float max = 0.0f;
		CharacterPattern best = EMPTY;
		for (CharacterPattern pattern : patterns) {
			CharacterType hint = sample.getHint();
			if (hint == null || hint.equals(pattern.getType())) {
				float match = sample.match(pattern);
				if (match > max) {
					max = match;
					best = pattern;
				}
			}
		}
		return best;
	}
}
