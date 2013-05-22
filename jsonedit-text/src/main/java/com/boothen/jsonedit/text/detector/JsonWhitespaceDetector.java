/**
 *
 */
package com.boothen.jsonedit.text.detector;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/**
 * Whitespace detector for JsonScanner.
 * @author Matt Garner
 *
 */
public class JsonWhitespaceDetector implements IWhitespaceDetector {

	@Override
	public boolean isWhitespace(char character) {
		return Character.isWhitespace(character);
	}

}
