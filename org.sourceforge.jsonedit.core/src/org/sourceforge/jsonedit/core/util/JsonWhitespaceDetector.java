/**
 * 
 */
package org.sourceforge.jsonedit.core.util;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/**
 * Whitespace detector for JsonScanner.
 * @author Matt Garner
 *
 */
public class JsonWhitespaceDetector implements IWhitespaceDetector {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.rules.IWhitespaceDetector#isWhitespace(char)
	 */
	public boolean isWhitespace(char character) {
		return Character.isWhitespace(character);
	}

}
