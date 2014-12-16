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
package com.boothen.jsonedit.core.text;

import static com.boothen.jsonedit.core.util.JsonCharUtility.closeCurly;
import static com.boothen.jsonedit.core.util.JsonCharUtility.closeSquare;
import static com.boothen.jsonedit.core.util.JsonCharUtility.openCurly;
import static com.boothen.jsonedit.core.util.JsonCharUtility.openSquare;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextUtilities;

import com.boothen.jsonedit.type.JsonDocumentType;

/**
 * Auto indent strategy for Json Text format.
 *
 * @author Matt Garner
 *
 */
public class JsonIndentLineAutoEditStrategy extends DefaultIndentLineAutoEditStrategy {

	private String indent;
	private String lineEnding;

	public JsonIndentLineAutoEditStrategy(boolean spaces, int numSpaces, String lineEnding) {
		initPreferences(spaces, numSpaces, lineEnding);
	}

	/**
	 * Returns the first offset greater than <code>offset</code> and smaller than
	 * <code>end</code> whose character is not a space or tab character. If no such
	 * offset is found, <code>end</code> is returned.
	 *
	 * @param document the document to search in
	 * @param offset the offset at which searching start
	 * @param end the offset at which searching stops
	 * @return the offset in the specified range whose character is not a space or tab
	 * @exception BadLocationException if position is an invalid range in the given document
	 */
	@Override
	protected int findEndOfWhiteSpace(IDocument document, int offset, int end) throws BadLocationException {
		while (offset < end) {
			char c = document.getChar(offset);
			if (!Character.isWhitespace(c)) {
				return offset;
			}
			offset++;
		}
		return end;
	}

	private int findPreviousBracket(IDocument document, int offset) throws BadLocationException {
		while(offset >= 0) {
			char c = document.getChar(offset);
			if (!Character.isWhitespace(c)) {
				if (c == openCurly || c == openSquare ) {
					return offset;
				} else {
					return -1;
				}
			}
			offset--;
		}

		return -1;
	}

	private boolean createClosingBracket(IDocument document, int offset, char bracket) throws BadLocationException {

		String openCategory = bracket == closeCurly ? JsonDocumentType.JSON_OBJECT_OPEN : JsonDocumentType.JSON_ARRAY_OPEN;
		String closeCategory = bracket == closeCurly ? JsonDocumentType.JSON_OBJECT_CLOSE : JsonDocumentType.JSON_ARRAY_CLOSE;
		
		ITypedRegion[] partitions;
		try {
			partitions = document.computePartitioning(0, document.getLength());
			int count = 0;
			for (ITypedRegion partition : partitions) {
				if (openCategory.equals(partition.getType())) {
					count++;
				}
				
				if (closeCategory.equals(partition.getType())) {
					count--;
				}
			}
			return count > 0;
		} catch (BadLocationException e) {
			return false;
		}
		
	}



	/**
	 * Copies the indentation of the previous line.
	 *
	 * @param d the document to work on
	 * @param c the command to deal with
	 */
	private void autoIndentAfterNewLine(IDocument d, DocumentCommand c) {
		if (c.offset == -1 || d.getLength() == 0) {
			return;
		}

		try {

			// check if previous was a bracket
			int bracketPos = findPreviousBracket(d, c.offset - 1);
			if (bracketPos != -1) {

				int start = getStartOfLineOffset(d, bracketPos);
				
				int end = findEndOfWhiteSpace(d, start, c.offset);
				
				StringBuffer buf = new StringBuffer(c.text);
				if (end > start) {
					// append to input
					buf.append(d.get(start, end - start));
				}
				buf.append(indent);
				c.text = buf.toString();
				
				char ch = determineBracketType(d, bracketPos);
				if (createClosingBracket(d, c.offset, ch)) {
					d.replace(c.offset, 0, lineEnding + d.get(start, end - start) + ch);
				}
				return;
			} 

			c.text = maintainDefaultLineIndent(d, c);

		} catch (BadLocationException excp) {

		}
	}

	private String maintainDefaultLineIndent(IDocument d,
			DocumentCommand c) throws BadLocationException {
		// find start of line
		int start = findStartOfCurrentLine(d, c);

		// find white spaces at beginning of the line
		int end = findEndOfWhiteSpace(d, start, c.offset);

		StringBuffer buf = new StringBuffer(c.text);
		if (end > start) {
			// append to input
			buf.append(d.get(start, end - start));
		}
		return buf.toString();
	}

	private char determineBracketType(IDocument d, int bracketPos)
			throws BadLocationException {
		char ch = closeSquare;
		if (d.getChar(bracketPos) == openCurly) {
			ch = closeCurly;
		}
		return ch;
	}

	private int findStartOfCurrentLine(IDocument d, DocumentCommand c)
			throws BadLocationException {
		int p = (c.offset == d.getLength() ? c.offset  - 1 : c.offset);
		int start = getStartOfLineOffset(d, p);
		return start;
	}

	private int getStartOfLineOffset(IDocument d, int p)
			throws BadLocationException {
		IRegion info = d.getLineInformationOfOffset(p);
		int start = info.getOffset();
		return start;
	}

	public void initPreferences(boolean spaces, int numSpaces, String lineEnding) {
		this.lineEnding = lineEnding;
		if (spaces) {
			StringBuffer strBuf = new StringBuffer();
			for (int i = 0; i < numSpaces; i++) {
				strBuf.append(" ");
			}
			indent = strBuf.toString();
		} else {
			indent = "\t";
		}
	}



	@Override
	public void customizeDocumentCommand(IDocument d, DocumentCommand c) {

		if (c.length == 0 && c.text != null && TextUtilities.endsWith(d.getLegalLineDelimiters(), c.text) != -1) {
			autoIndentAfterNewLine(d, c);
		}
	}
}
