/**
 * 
 */
package org.sourceforge.jsonedit.core.text;

import static org.sourceforge.jsonedit.core.util.JsonCharUtility.closeCurly;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.closeSquare;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.openCurly;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.openSquare;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.space;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.tab;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;
import org.sourceforge.jsonedit.core.JsonEditorPlugin;

/**
 * Auto indent strategy for Json Text format.
 * 
 * @author Matt Garner
 *
 */
public class JsonIndentLineAutoEditStrategy extends DefaultIndentLineAutoEditStrategy {
	
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
	protected int findEndOfWhiteSpace(IDocument document, int offset, int end) throws BadLocationException {
		while (offset < end) {
			char c= document.getChar(offset);
			if (c != ' ' && c != '\t') {
				return offset;
			}
			offset++;
		}
		return end;
	}
	
	private int findPreviousBracket(IDocument document, int offset) throws BadLocationException {
		while(offset >= 0) {
			char c = document.getChar(offset);
			if (c != space && c != tab) {
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
		
		if (c.offset == -1 || d.getLength() == 0)
			return;
		
		try {
			// find start of line
			int p= (c.offset == d.getLength() ? c.offset  - 1 : c.offset);
			IRegion info= d.getLineInformationOfOffset(p);
			int start= info.getOffset();

			// find white spaces
			int end= findEndOfWhiteSpace(d, start, c.offset);

			StringBuffer buf= new StringBuffer(c.text);
			if (end > start) {
				// append to input
				buf.append(d.get(start, end - start));
				
				int bracketPos = findPreviousBracket(d, c.offset - 1);
				if (bracketPos != -1) {
					char ch = closeSquare;
					if (d.getChar(bracketPos) == openCurly) {
						ch = closeCurly;
					}
					
					if (!isNextBracket(d, c.offset, ch)) {
						buf.append(initPreferences());
						d.replace(c.offset, 0, "\n" + d.get(start, end - start) + ch);
						
					}
				}
			}
			
			c.text= buf.toString();

		} catch (BadLocationException excp) {
			// stop work
		}
	}
	
	private String initPreferences() {
		
		IPreferenceStore store = JsonEditorPlugin.getJsonPreferenceStore();
		boolean spaces = store.getBoolean(JsonEditorPlugin.SPACES_FOR_TABS);
		int numSpaces = store.getInt(JsonEditorPlugin.NUM_SPACES);
		
		String indent = null;
		if (spaces) {
			StringBuffer strBuf = new StringBuffer();
			for (int i = 0; i < numSpaces; i++) {
				strBuf.append(" ");
			}
			indent = strBuf.toString();
		} else {
			indent = "\t";
		}
		
		return indent;
		
	}
	
	/*
	 * @see org.eclipse.jface.text.IAutoEditStrategy#customizeDocumentCommand(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.DocumentCommand)
	 */
	public void customizeDocumentCommand(IDocument d, DocumentCommand c) {
		
		// TODO: Added in perferences to enable/disable.
		//super.customizeDocumentCommand(d, c);
		
		if (c.length == 0 && c.text != null && TextUtilities.endsWith(d.getLegalLineDelimiters(), c.text) != -1) {
			autoIndentAfterNewLine(d, c);
		}
	}
}
