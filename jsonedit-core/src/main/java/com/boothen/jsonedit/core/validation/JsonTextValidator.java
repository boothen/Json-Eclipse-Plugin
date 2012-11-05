/**
 *
 */
package com.boothen.jsonedit.core.validation;

import static com.boothen.jsonedit.core.util.JsonCharUtility.a;
import static com.boothen.jsonedit.core.util.JsonCharUtility.closeCurly;
import static com.boothen.jsonedit.core.util.JsonCharUtility.closeSquare;
import static com.boothen.jsonedit.core.util.JsonCharUtility.colon;
import static com.boothen.jsonedit.core.util.JsonCharUtility.comma;
import static com.boothen.jsonedit.core.util.JsonCharUtility.e;
import static com.boothen.jsonedit.core.util.JsonCharUtility.eof;
import static com.boothen.jsonedit.core.util.JsonCharUtility.f;
import static com.boothen.jsonedit.core.util.JsonCharUtility.isClosed;
import static com.boothen.jsonedit.core.util.JsonCharUtility.isNotClosed;
import static com.boothen.jsonedit.core.util.JsonCharUtility.isNotWhiteSpace;
import static com.boothen.jsonedit.core.util.JsonCharUtility.l;
import static com.boothen.jsonedit.core.util.JsonCharUtility.minus;
import static com.boothen.jsonedit.core.util.JsonCharUtility.n;
import static com.boothen.jsonedit.core.util.JsonCharUtility.openCurly;
import static com.boothen.jsonedit.core.util.JsonCharUtility.openSquare;
import static com.boothen.jsonedit.core.util.JsonCharUtility.point;
import static com.boothen.jsonedit.core.util.JsonCharUtility.quote;
import static com.boothen.jsonedit.core.util.JsonCharUtility.r;
import static com.boothen.jsonedit.core.util.JsonCharUtility.s;
import static com.boothen.jsonedit.core.util.JsonCharUtility.slash;
import static com.boothen.jsonedit.core.util.JsonCharUtility.t;
import static com.boothen.jsonedit.core.util.JsonCharUtility.u;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

import com.boothen.jsonedit.core.util.reader.JsonFileReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;

/**
 * JsonTextValidator parses a Json file and validates it. The parser exits
 * when the first error is found.
 *
 * @author Matt Garner
 *
 */
public class JsonTextValidator {

	private JsonFileReader parser;

	public static final String KEY = "key";
	public static final String VIOLATION = "violation";

	/**
	 * Constructor taking an IFile.
	 *
	 * @param file
	 * @throws CoreException
	 */
	public JsonTextValidator(IFile file) throws CoreException {
		super();
		this.parser = new JsonFileReader(file);
	}

	/**
	 * Constructor taking a File object.
	 * @param file
	 * @throws FileNotFoundException
	 */
	public JsonTextValidator(File file) throws FileNotFoundException {
		this.parser = new JsonFileReader(file);
	}

	/**
	 * Parse the file and report the first error found.
	 */
	public void parse() {

		try {

			char current = parser.getNextClean();

			if (current == openCurly) {
				doJsonObject();
			} else if (current == openSquare) {
				doJsonArray();
			} else {
				reportProblem("JSON should begin with { or [", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			}

		} catch (Exception e) {
		//	JsonLog.logError("Read exception: ", e);
		}
	}

	/**
	 * Parse Json Object.
	 *
	 * @throws JsonReaderException
	 * @throws JsonValidationException
	 */
	private void doJsonObject() throws JsonReaderException, JsonValidationException {

		char ch;
		do {
			ch = parser.getNextClean();

			// Check for empty object.
			if (ch == closeCurly) {
				parser.getNextClean();
				break;
			}

			if (ch != quote) {
				reportProblem("JSON key should begin with \"", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
				throw new JsonValidationException();
			}
			doJsonKey();

			ch = parser.getNextClean();
			if (ch != colon) {
				reportProblem("Expected colon key/value delimitor", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
				throw new JsonValidationException();
			}

			ch = parser.getNextClean();

			if (ch == openCurly) {
				doJsonObject();
			} else if (ch == openSquare) {
				doJsonArray();
			} else if (ch == n) {
				doJsonNull();
			} else if (ch == quote) {
				doJsonValue();
			}	else if (ch == t) {
				doJsonTrueValue();
			} else if (ch == f) {
				doJsonFalseValue();
			} else if (Character.isDigit(ch) || ch == minus) {
				doJsonNumber();
			} else {
				reportProblem("Expected JSON value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
				throw new JsonValidationException();
			}

			if (parser.getCurrent() == comma) {
				continue;
			}

			if (parser.getCurrent() == closeCurly) {
				parser.getNextClean();
				break;
			}

			reportProblem("Unexpected object character:" + ch, new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		} while (ch != eof);
	}

	/**
	 * Parse Json Array.
	 *
	 * @throws JsonReaderException
	 * @throws JsonValidationException
	 */
	private void doJsonArray() throws JsonReaderException, JsonValidationException {

		char ch;

		do {
			ch = parser.getNextClean();

			if (ch == openCurly) {
				doJsonObject();
			} else if (ch == openSquare) {
				doJsonArray();
			} else if (ch == n) {
				doJsonNull();
			} else if (ch == quote) {
				doJsonValue();
			} else if (ch == t) {
				doJsonTrueValue();
			} else if (ch == f) {
				doJsonFalseValue();
			} else if (Character.isDigit(ch) || ch == minus) {
				doJsonNumber();
			} else if (ch == closeSquare) {
				parser.getNextClean();
				break;
			} else {
				reportProblem("Expected JSON value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
				throw new JsonValidationException();
			}

			ch = parser.getCurrent();
			if (ch == comma) {
				continue;
			}

			if (ch == closeSquare) {
				parser.getNextClean();
				break;
			}

			reportProblem("Unexpected array character:" + ch, new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		} while (ch != eof);
	}

	/**
	 * Parse true value.
	 * @throws JsonReaderException
	 * @throws JsonValidationException
	 */
	private void doJsonTrueValue() throws JsonReaderException, JsonValidationException {

		char ch = parser.getNextChar();
		if (ch != r) {
			reportProblem("Expect true value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}

		ch = parser.getNextChar();
		if (ch != u) {
			reportProblem("Expect true value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}

		ch = parser.getNextChar();
		if (ch != e) {
			reportProblem("Expect true value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}

		ch = parser.getNextClean();
		if (isNotClosed(ch)) {
			reportProblem("Expected end value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}
	}

	/**
	 * Parse false value.
	 *
	 * @throws JsonReaderException
	 * @throws JsonValidationException
	 */
	private void doJsonFalseValue() throws JsonReaderException, JsonValidationException {

		char ch = parser.getNextChar();
		if (ch != a) {
			reportProblem("Expect true value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}

		ch = parser.getNextChar();
		if (ch != l) {
			reportProblem("Expect true value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}

		ch = parser.getNextChar();
		if (ch != s) {
			reportProblem("Expect true value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}

		ch = parser.getNextChar();
		if (ch != e) {
			reportProblem("Expect true value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}

		ch = parser.getNextClean();
		if (isNotClosed(ch)) {
			reportProblem("Expected end value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}

	}

	/**
	 * Parse null value.
	 *
	 * @throws JsonReaderException
	 * @throws JsonValidationException
	 */
	private void doJsonNull() throws JsonReaderException, JsonValidationException {

		char ch = parser.getNextChar();
		if (ch != u) {
			reportProblem("Expect null value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}

		ch = parser.getNextChar();
		if (ch != l) {
			reportProblem("Expect null value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}

		ch = parser.getNextChar();
		if (ch != l) {
			reportProblem("Expect null value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}

		ch = parser.getNextClean();
		if (isNotClosed(ch)) {
			reportProblem("Expected end value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
			throw new JsonValidationException();
		}
	}

	/**
	 * Parse Json value.
	 *
	 * @throws JsonReaderException
	 * @throws JsonValidationException
	 */
	private void doJsonValue() throws JsonReaderException, JsonValidationException {

		char ch;
		do {
			ch = parser.getNextChar();

			if (ch == eof) {
				reportProblem("Expected quotation", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
				break;
			}

			if (ch != quote) {
				continue;
			}

			if (parser.getPrevious() == slash) {
				continue;
			}

			ch = parser.getNextClean();
			break;
		} while (ch != eof);
	}

	/**
	 * Parse Json number.
	 *
	 * @throws JsonReaderException
	 * @throws JsonValidationException
	 */
	private void doJsonNumber() throws JsonReaderException, JsonValidationException {

		boolean decimalPointSet = false;
		char ch;
		do {
			ch = parser.getNextChar();
			if (Character.isDigit(ch)) {
				continue;
			}

			if (isClosed(ch)) {
				break;
			}

			if (!decimalPointSet && ch == point) {
				decimalPointSet = true;
				continue;
			}

			if (isNotWhiteSpace(ch)) {
				reportProblem("Value " + ch + " not expected here", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
				throw new JsonValidationException();
			}

			ch = parser.getNextClean();
			if (isNotClosed(ch)) {
				reportProblem("Expected end value", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
				throw new JsonValidationException();
			}

			break;
		} while (ch != eof);

	}

	/**
	 * Parse Json Key.
	 *
	 * @throws JsonReaderException
	 * @throws JsonValidationException
	 */
	private void doJsonKey() throws JsonReaderException, JsonValidationException {

		char ch;
		do {
			ch = parser.getNextChar();

			if (ch == eof) {
				reportProblem("Invalid JSON key, no closing \"", new Location(parser.getIFile(),"", parser.getPosition(), parser.getPosition()),0, true);
				throw new JsonValidationException();
			}

			if (ch != quote) {
				continue;
			}

			if (parser.getPrevious() == slash) {
				continue;
			}

			break;
		} while (ch != eof);
	}

	/**
	 * Report the problem.
	 *
	 * @param msg
	 * @param loc
	 * @param violation
	 * @param isError
	 */
	public void reportProblem(String msg, Location loc, int violation, boolean isError) {

		try {

			IMarker marker = loc.file.createMarker(IncrementalJsonValidator.MARKER_ID);
			marker.setAttribute(IMarker.MESSAGE, msg);
			marker.setAttribute(IMarker.CHAR_START, loc.charStart);
			marker.setAttribute(IMarker.CHAR_END, loc.charEnd);
			marker.setAttribute(IMarker.SEVERITY, isError ? IMarker.SEVERITY_ERROR : IMarker.SEVERITY_WARNING);
			marker.setAttribute(KEY, loc.key);
			marker.setAttribute(VIOLATION, violation);
		} catch (CoreException e) {
			//JsonLog.logError(e);
		}
	}

	/**
	 * Location in the file of the issue.
	 *
	 * @author Matt Garner
	 *
	 */
	class Location {


		public Location(IFile file, String key, int charStart, int charEnd) {
			super();
			this.file = file;
			this.key = key;
			this.charStart = charStart;
			this.charEnd = charEnd;
		}

		IFile file;
		String key;
		int charStart;
		int charEnd;
	}


}

/**
 * Validation Exception
 *
 * @author Matt Garner
 *
 */
class JsonValidationException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -75665558665935017L;

	public JsonValidationException() {
		super();
	}

}