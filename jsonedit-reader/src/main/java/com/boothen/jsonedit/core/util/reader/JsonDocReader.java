/*******************************************************************************
 * Copyright 2014 Boothen Technology Ltd.
 *
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   
 * https://eclipse.org/org/documents/epl-v10.html
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
