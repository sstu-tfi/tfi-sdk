package ru.sstu.ocr.core;

/**
 * <code>CharacterPattern</code> interface represents image pattern for
 * character recognition.
 *
 * @author denis_murashev
 */
public interface CharacterPattern {

	/**
	 * Provides character.
	 *
	 * @return the character
	 */
	char getCharacter();

	/**
	 * Provides recognition pattern for character.
	 *
	 * @return the pattern
	 */
	float[][] getPattern();

	/**
	 * Provides character type.
	 *
	 * @return character type
	 */
	CharacterType getType();
}
