/**
 *
 */
package com.boothen.jsonedit.core.util;

import org.eclipse.jface.text.rules.IWordDetector;

/**
 * Word detector for JsonScanner
 *
 * @author Matt Garner
 *
 */
public class JsonWordDetector implements IWordDetector {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.rules.IWordDetector#isWordPart(char)
	 */
	public boolean isWordPart(char character) {
		return Character.isJavaIdentifierPart(character);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.rules.IWordDetector#isWordStart(char)
	 */
	public boolean isWordStart(char character) {
		return Character.isJavaIdentifierPart(character);
	}

}
