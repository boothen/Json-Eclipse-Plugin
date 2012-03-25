package org.sourceforge.jsonedit.core.util;

import org.sourceforge.jsonedit.core.model.node.Type;



/**
 * Utility class to determine if a character is a special Json character.
 * 
 * @author Matt Garner
 *
 */
public final class JsonCharUtility {
	
	public static final char openCurly = '{';
	public static final char closeCurly = '}';
	public static final char openSquare = '[';
	public static final char closeSquare = ']';
	public static final char comma = ',';
	public static final char quote = '"';
	public static final char colon = ':';
	public static final char slash = '\\';
	public static final char minus = '-';
	public static final char point = '.';
	public static final char n = 'n';
	public static final char u = 'u';
	public static final char l = 'l';
	public static final char f = 'f';
	public static final char a = 'a';
	public static final char s = 's';
	public static final char e = 'e';
	public static final char t = 't';
	public static final char r = 'r';
	public static final char eof = (char) -1;
	public static final int space = ' ';
	public static final int eol = '\n';
	public static final int tab = '\t';
	public static final int carriageReturn = '\r';
	
	public static boolean isWhiteSpace(char ch) {
		if (ch == space || ch == eol || ch == tab || ch == carriageReturn) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isNotWhiteSpace(char ch) {
		if (ch != space && ch != eol && ch != tab && ch != carriageReturn) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isClosed(char ch) {
		if (ch == comma || ch == closeCurly || ch == closeSquare) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isNotClosed(char ch) {
		if (ch != comma && ch != closeCurly && ch != closeSquare) {
			return true;
		}
		return false;
	}
	
	public static boolean isJsonChar(char ch, char previous) {
		if (ch == comma || ch == openCurly || ch == closeCurly || 
				ch == openSquare || ch == closeSquare || ch == colon ||
				(ch == quote && previous != slash)) {
			return true;
		}
		
		return false;
	}
	
	public static Type getJsonCharType(char ch) {
		
		if (ch == comma) {
			
			return Type.Comma;  
		}
		
		if (ch == openCurly) {
			return Type.OpenObject;
		}
		
		if (ch == closeCurly) {
			return Type.CloseObject;
		}
				
		if (ch == openSquare) {
			return Type.OpenArray;
		}
		
		if (ch == closeSquare) {
			return Type.CloseArray;
		}
		 
		
		if (ch == colon) {	
			return Type.Colon;
		}
		
		if (ch == quote) {	
			return Type.Quote;
		}
		
		return null;
	}
}
