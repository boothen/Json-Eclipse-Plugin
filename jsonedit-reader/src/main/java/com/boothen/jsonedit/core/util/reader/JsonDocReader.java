/**
 *
 */
package com.boothen.jsonedit.core.util.reader;

import static com.boothen.jsonedit.core.util.JsonCharUtility.isWhiteSpace;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * JsonDocReader used to read an IDoc.
 *
 * @author Matt Garner
 *
 */
public class JsonDocReader implements JsonReader {

	private IDocument doc;

	private char previous;

	private char current;

	private int position;

	public JsonDocReader(IDocument doc) {
		super();
		this.doc = doc;
	}

	public char getCurrent() {
		return current;
	}

	public char getNextChar() throws JsonReaderException {
		char ch = next();
		previous = current;
		current = ch;
		return current;
	}

	private char next() throws JsonReaderException {

		try {
			if (position < doc.getLength()) {
				char ch = (char) doc.getChar(position++);

				return ch;
			} else {
				return (char) -1;
			}
		} catch (BadLocationException e) {
			throw new JsonReaderException();
		}
	}

	public char getNextClean() throws JsonReaderException {

		char ch = ' ';
		while (isWhiteSpace(ch)) {
			ch = next();
		}
		previous = current;
		current = ch;
		return current;
	}

	public int getPosition() {
		return position - 1;
	}

	public char getPrevious() {
		return previous;
	}
}
