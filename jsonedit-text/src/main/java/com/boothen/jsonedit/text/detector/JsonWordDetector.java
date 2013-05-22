/**
 *
 */
package com.boothen.jsonedit.text.detector;

import org.eclipse.jface.text.rules.IWordDetector;

/**
 * Word detector for JsonScanner
 *
 * @author Matt Garner
 *
 */
public class JsonWordDetector implements IWordDetector {

	@Override
	public boolean isWordPart(char character) {
		return Character.isJavaIdentifierPart(character);
	}

	@Override
	public boolean isWordStart(char character) {
		return Character.isJavaIdentifierPart(character);
	}

}
