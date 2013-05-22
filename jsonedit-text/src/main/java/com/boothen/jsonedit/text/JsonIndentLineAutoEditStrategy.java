/**
 *
 */
package com.boothen.jsonedit.text;

import static com.boothen.jsonedit.core.util.JsonCharUtility.closeCurly;
import static com.boothen.jsonedit.core.util.JsonCharUtility.closeSquare;
import static com.boothen.jsonedit.core.util.JsonCharUtility.openCurly;
import static com.boothen.jsonedit.core.util.JsonCharUtility.openSquare;
import static com.boothen.jsonedit.core.util.JsonCharUtility.space;
import static com.boothen.jsonedit.core.util.JsonCharUtility.tab;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;

/**
 * Auto indent strategy for Json Text format.
 *
 * @author Matt Garner
 *
 */
public class JsonIndentLineAutoEditStrategy extends DefaultIndentLineAutoEditStrategy {

	private String indent;

	public JsonIndentLineAutoEditStrategy(boolean spaces, int numSpaces) {
		initPreferences(spaces, numSpaces);
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

	private boolean isNextBracket(IDocument document, int offset, char bracket) throws BadLocationException {

		while(offset < document.getLength()) {
			char c = document.getChar(offset);
			// should check for whitespace, however this will cause a closing bracket
			// not to be created if there is already one there for the outer object
			if (c != space && c != tab) {
				if (c == bracket) {
					return true;
				} else {
					return false;
				}
			}
			offset++;
		}

		return false;
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

		// Create line ending based on document delimiters
		String lineEnding = Platform.getPreferencesService().getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, new IScopeContext[] { DefaultScope.INSTANCE });

		if (lineEnding == null) {
			lineEnding = Platform.getPreferencesService().getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, new IScopeContext[] {
					InstanceScope.INSTANCE});
		}

		System.out.println(lineEnding.length());
		try {
			// find start of line
			int start = findStartOfCurrentLine(d, c);

			// find white spaces at beginning of the line
			int end = findEndOfWhiteSpace(d, start, c.offset);

			StringBuffer buf = new StringBuffer(c.text);
			if (end > start) {
				// append to input
				buf.append(d.get(start, end - start));
			}

			// check if previous was a bracket
			int bracketPos = findPreviousBracket(d, c.offset - 1);
			if (bracketPos != -1) {

				buf.append(indent);

				char ch = determineBracketType(d, bracketPos);

				if (!isNextBracket(d, c.offset, ch)) {
					d.replace(c.offset, 0, lineEnding + d.get(start, end - start) + ch);
				}
			}

			c.text = buf.toString();

		} catch (BadLocationException excp) {

		}
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
		IRegion info = d.getLineInformationOfOffset(p);
		int start = info.getOffset();
		return start;
	}

	public void initPreferences(boolean spaces, int numSpaces) {

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
