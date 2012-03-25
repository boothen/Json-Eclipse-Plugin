package org.sourceforge.jsonedit.core.outline;

import static org.sourceforge.jsonedit.core.util.JsonCharUtility.a;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.closeCurly;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.closeSquare;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.colon;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.comma;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.e;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.eof;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.f;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.isClosed;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.isNotClosed;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.isNotWhiteSpace;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.l;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.minus;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.n;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.openCurly;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.openSquare;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.point;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.quote;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.r;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.s;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.slash;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.t;
import static org.sourceforge.jsonedit.core.util.JsonCharUtility.u;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.sourceforge.jsonedit.core.outline.elements.JsonArray;
import org.sourceforge.jsonedit.core.outline.elements.JsonBoolean;
import org.sourceforge.jsonedit.core.outline.elements.JsonElement;
import org.sourceforge.jsonedit.core.outline.elements.JsonError;
import org.sourceforge.jsonedit.core.outline.elements.JsonNull;
import org.sourceforge.jsonedit.core.outline.elements.JsonNumber;
import org.sourceforge.jsonedit.core.outline.elements.JsonObject;
import org.sourceforge.jsonedit.core.outline.elements.JsonParent;
import org.sourceforge.jsonedit.core.outline.elements.JsonString;
import org.sourceforge.jsonedit.core.util.reader.JsonDocReader;
import org.sourceforge.jsonedit.core.util.reader.JsonReader;
import org.sourceforge.jsonedit.core.util.reader.JsonReaderException;

/**
 * JsonTextOutlineParser parses the Json text and returns a tree structure
 * representing the Json Text. Used by the outline view.
 * 
 * @author Matt Garner
 *
 */
public class JsonTextOutlineParser {

	private JsonReader parser;
	
	private JsonParent root;
	
	private JsonParent parent;
	
	private IDocument doc;
	
	/**
	 * Constructor taking the document to parse.
	 * 
	 * @param doc
	 */
	public JsonTextOutlineParser(IDocument doc) {
		super();
		parser = new JsonDocReader(doc);
		this.doc = doc;
	}
	
	/**
	 * Parses the text in the document returning a tree structure.
	 *  
	 * @return
	 */
	public JsonParent parse() {
		
		//System.out.println("Parse Text");
		try {

			char current = parser.getNextClean();

			if (current == openCurly) {
				doJsonObject("", parser.getPosition());
			} else if (current == openSquare){
				doJsonArray("", parser.getPosition());
			} else {
				JsonError jsonError = new JsonError(parent, "JSON should begin with { or [");
				root = new JsonObject(parent, "");
				root.addChild(jsonError);
				throw new JsonTextOutlineParserException();
			}
			
		} catch (Exception e) {
			//JsonLog.logError("Read exception: ", e);
		}
		
		return root;
	}
	
	/**
	 * Parse a Json Object.
	 * 
	 * @param key
	 * @param startPos
	 * @throws JsonReaderException
	 * @throws JsonTextOutlineParserException
	 * @throws BadLocationException
	 * @throws BadPositionCategoryException
	 */
	private void doJsonObject(String key, int startPos) throws JsonReaderException, JsonTextOutlineParserException, BadLocationException, BadPositionCategoryException {
		
		JsonObject jsonObject = new JsonObject(parent, key);
		if (root == null) {
			root = jsonObject;
			parent = root;
		} else {
			parent.addChild(jsonObject);
			parent = jsonObject;
		}
		
		jsonObject.setPosition(startPos, parser.getPosition() - startPos + 1, doc);
		
		char ch;
		do {
			ch = parser.getNextClean();
			
			// Check for empty object.
			if (ch == closeCurly) {
				parser.getNextClean();
				parent = ((JsonElement)parent).getParent();
				break;
			}
			
			if (ch != quote) {
				JsonError jsonError = new JsonError(parent, "JSON key should begin with \"");
				parent.addChild(jsonError);
				throw new JsonTextOutlineParserException();
			}
			
			int start = parser.getPosition();
			
			String attributeKey = doJsonKey();

			ch = parser.getNextClean();
			if (ch != colon) {
				JsonError jsonError = new JsonError(parent, "Expected colon key/value delimitor");
				parent.addChild(jsonError);
				throw new JsonTextOutlineParserException();
			}

			ch = parser.getNextClean();
			if (ch == openCurly) {
				doJsonObject(attributeKey, start);
			} else if (ch == openSquare) {
				doJsonArray(attributeKey, start);
			} else if (ch == n) {
				doJsonNull(attributeKey, start);
			} else if (ch == quote) {
				doJsonValue(attributeKey, start);
			}	else if (ch == t) {
				doJsonTrueValue(attributeKey, start);
			} else if (ch == f) {
				doJsonFalseValue(attributeKey, start);
			} else if (Character.isDigit(ch) || ch == minus) {
				doJsonNumber(attributeKey, start);
			} else {
				JsonError jsonError = new JsonError(parent, "Expected org.sourceforge.jsonedit.core.core value");
				parent.addChild(jsonError);
				throw new JsonTextOutlineParserException();
			}
			
			ch = parser.getCurrent();
			if (ch == comma) {
				continue;
			}

			if (ch == closeCurly) {
				parser.getNextClean();
				parent = ((JsonElement)parent).getParent();
				break;
			}
			
			JsonError jsonError = new JsonError(parent, "Unexpected object character:" + ch);
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		} while (ch != eof);
	}
	
	/**
	 * Parse a Json Array.
	 * 
	 * @param key
	 * @param startPos
	 * @throws JsonReaderException
	 * @throws JsonTextOutlineParserException
	 * @throws BadLocationException
	 * @throws BadPositionCategoryException
	 */
	private void doJsonArray(String key, int startPos) throws JsonReaderException, JsonTextOutlineParserException, BadLocationException, BadPositionCategoryException {
		
		JsonArray jsonArray = new JsonArray(parent, key);
		if (root == null) {
			root = jsonArray;
			parent = root;
		} else {
			parent.addChild(jsonArray);
			parent = jsonArray;
		}
		
		jsonArray.setPosition(startPos, parser.getPosition() - startPos + 1, doc);
		
		char ch;
		do {
			ch = parser.getNextClean();
			int start = parser.getPosition();
			if (ch == openCurly) {
				doJsonObject("", start);
			} else if (ch == openSquare) {
				doJsonArray("", start);
			} else if (ch == n) {
				doJsonNull("", start);
			} else if (ch == quote) {
				doJsonValue("", start);
			} else if (ch == t) {
				doJsonTrueValue("", start);
			} else if (ch == f) {
				doJsonFalseValue("", start);
			} else if (Character.isDigit(ch) || ch == minus) {
				doJsonNumber("", start);
			} else if (ch == closeSquare) {
				parser.getNextClean();
				parent = ((JsonElement)parent).getParent();
				break;
			} else {
				JsonError jsonError = new JsonError(parent, "Expected org.sourceforge.jsonedit.core.core value");
				parent.addChild(jsonError);
				throw new JsonTextOutlineParserException();
			}
			
			ch = parser.getCurrent();
			if (ch == comma) {
				continue;
			}

			if (ch == closeSquare) {
				parser.getNextClean();
				parent = ((JsonElement)parent).getParent();
				break;
			}
			
			JsonError jsonError = new JsonError(parent, "Unexpected array character:" + ch);
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		} while (ch != eof);
	}
	
	/**
	 * Parse a Json "true" value.
	 * 
	 * @param key
	 * @param start
	 * @throws JsonReaderException
	 * @throws JsonTextOutlineParserException
	 */
	private void doJsonTrueValue(String key, int start) throws JsonReaderException, JsonTextOutlineParserException, BadLocationException, BadPositionCategoryException  {
		
		JsonBoolean jsonBoolean = new JsonBoolean(parent, key);
		parent.addChild(jsonBoolean);
		jsonBoolean.setStart(start, doc);
		
		char ch = parser.getNextChar();
		if (ch != r) {
			JsonError jsonError = new JsonError(parent, "Expect true value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		ch = parser.getNextChar();
		if (ch != u) {
			JsonError jsonError = new JsonError(parent, "Expect true value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		ch = parser.getNextChar();
		if (ch != e) {
			JsonError jsonError = new JsonError(parent, "Expect true value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		jsonBoolean.setLength(parser.getPosition() - start + 1);
		
		ch = parser.getNextClean();
		if (isNotClosed(ch)) {
			JsonError jsonError = new JsonError(parent, "Expected end value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		jsonBoolean.setValue("true");
	}
	
	/**
	 * Parse a Json "false" value.
	 * @param key
	 * @param start
	 * @throws JsonReaderException
	 * @throws JsonTextOutlineParserException
	 */
	private void doJsonFalseValue(String key, int start) throws JsonReaderException, JsonTextOutlineParserException, BadLocationException, BadPositionCategoryException  {
		
		JsonBoolean jsonBoolean = new JsonBoolean(parent, key);
		parent.addChild(jsonBoolean);
		jsonBoolean.setStart(start, doc);
		
		char ch = parser.getNextChar();
		if (ch != a) {
			JsonError jsonError = new JsonError(parent, "Expect true value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		ch = parser.getNextChar();
		if (ch != l) {
			JsonError jsonError = new JsonError(parent, "Expect true value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		ch = parser.getNextChar();
		if (ch != s) {
			JsonError jsonError = new JsonError(parent, "Expect true value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		ch = parser.getNextChar();
		if (ch != e) {
			JsonError jsonError = new JsonError(parent, "Expect true value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		jsonBoolean.setLength(parser.getPosition() - start + 1);
		ch = parser.getNextClean();
		if (isNotClosed(ch)) {
			JsonError jsonError = new JsonError(parent, "Expected end value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		jsonBoolean.setValue("false");
	}
	
	/**
	 * Parse a Json null value.
	 * 
	 * @param key
	 * @param start
	 * @throws JsonReaderException
	 * @throws JsonTextOutlineParserException
	 */
	private void doJsonNull(String key, int start) throws JsonReaderException, JsonTextOutlineParserException, BadLocationException, BadPositionCategoryException  {
		
		JsonNull jsonNull = new JsonNull(parent, key);
		parent.addChild(jsonNull);
		jsonNull.setStart(start, doc);
		
		char ch = parser.getNextChar();
		if (ch != u) {
			JsonError jsonError = new JsonError(parent, "Expect null value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		ch = parser.getNextChar();
		if (ch != l) {
			JsonError jsonError = new JsonError(parent, "Expect null value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		ch = parser.getNextChar();
		if (ch != l) {
			JsonError jsonError = new JsonError(parent, "Expect null value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		jsonNull.setLength(parser.getPosition() - start + 1);
		
		ch = parser.getNextClean();
		if (isNotClosed(ch)) {
			JsonError jsonError = new JsonError(parent, "Expected end value");
			parent.addChild(jsonError);
			throw new JsonTextOutlineParserException();
		}
		
		jsonNull.setValue("null");
		
	}
	
	/**
	 * Parse a Json string value.
	 * 
	 * @param key
	 * @param start
	 * @throws JsonReaderException
	 * @throws JsonTextOutlineParserException
	 */
	private void doJsonValue(String key, int start) throws JsonReaderException, JsonTextOutlineParserException, BadLocationException, BadPositionCategoryException  {
		
		JsonString jsonString = new JsonString(parent, key);
		parent.addChild(jsonString);
		jsonString.setStart(start, doc);
		
		StringBuilder valueBuilder = new StringBuilder();
		char ch;
		do {
			ch = parser.getNextChar();
			
			// TODO check format in values as well.
			if (ch == eof || (ch == quote && parser.getPrevious() != slash)) {
				jsonString.setLength(parser.getPosition() - start + 1);
				ch = parser.getNextClean();
				break;
			}
			
			valueBuilder.append(ch);
		} while (ch != eof);

		jsonString.setValue(valueBuilder.toString());
	}
	
	/**
	 * Parse a org.sourceforge.jsonedit.core.core number.
	 * 
	 * @param key
	 * @param start
	 * @throws JsonReaderException
	 * @throws JsonTextOutlineParserException
	 */
	private void doJsonNumber(String key, int start) throws JsonReaderException, JsonTextOutlineParserException, BadLocationException, BadPositionCategoryException {
		
		boolean decimalPointSet = false;
		
		JsonNumber jsonNumber = new JsonNumber(parent, key);
		parent.addChild(jsonNumber);
		jsonNumber.setStart(start, doc);
		
		StringBuilder numberBuilder = new StringBuilder();
		numberBuilder.append(parser.getCurrent());
		
		char ch;
		do {
			ch = parser.getNextChar();
			
			if (Character.isDigit(ch)) {
				numberBuilder.append(ch);
				continue;
			}
			
			if (!decimalPointSet && ch == point) {
				decimalPointSet = true;
				numberBuilder.append(ch);
				continue;
			}
			
			jsonNumber.setLength(parser.getPosition() - start);
			if (isClosed(ch)) {
				
				break;
			}
			
			if (isNotWhiteSpace(ch)) {
				JsonError jsonError = new JsonError(parent, "Value " + ch + " not expected here");
				parent.addChild(jsonError);
				throw new JsonTextOutlineParserException();
			}
			
			
			ch = parser.getNextClean();
			if (isNotClosed(ch)) {
				JsonError jsonError = new JsonError(parent, "Expected end value");
				parent.addChild(jsonError);
				throw new JsonTextOutlineParserException();
			}
			
			break;
		} while (ch != eof);
		
		jsonNumber.setValue(numberBuilder.toString());
	}
	
	/**
	 * Parse a org.sourceforge.jsonedit.core.core key.
	 * 
	 * @return
	 * @throws JsonReaderException
	 * @throws JsonTextOutlineParserException
	 */
	private String doJsonKey() throws JsonReaderException, JsonTextOutlineParserException {

		StringBuilder keyBuilder = new StringBuilder();
		
		char ch;
		do {
			ch = parser.getNextChar();
			
			if (ch == eof) {
				JsonError jsonError = new JsonError(parent, "Invalid JSON key, no closing \"");
				parent.addChild(jsonError);
				throw new JsonTextOutlineParserException();
			}
			
			if (ch != quote || parser.getPrevious() == slash) {
				keyBuilder.append(ch);
				continue;
			}
			
			break;
		} while (ch != eof);
		
		return keyBuilder.toString();
	}
}

/**
 * Exception thrown while parsing.
 * 
 * @author Matt Garner
 *
 */
class JsonTextOutlineParserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -75665558665935017L;

	public JsonTextOutlineParserException() {
		super();
	}
	
}
