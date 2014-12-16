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
package com.boothen.jsonedit.validator;

import static com.boothen.jsonedit.core.util.JsonCharUtility.E;
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
import static com.boothen.jsonedit.core.util.JsonCharUtility.plus;
import static com.boothen.jsonedit.core.util.JsonCharUtility.point;
import static com.boothen.jsonedit.core.util.JsonCharUtility.quote;
import static com.boothen.jsonedit.core.util.JsonCharUtility.r;
import static com.boothen.jsonedit.core.util.JsonCharUtility.s;
import static com.boothen.jsonedit.core.util.JsonCharUtility.slash;
import static com.boothen.jsonedit.core.util.JsonCharUtility.t;
import static com.boothen.jsonedit.core.util.JsonCharUtility.u;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

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

	private static final Logger LOG = Logger.getLogger(JsonTextValidator.class.toString());

	private final JsonFileReader parser;
	private final String markerId;

	public static final String KEY = "key";
	public static final String VIOLATION = "violation";

	/**
	 * Constructor taking an IFile.
	 *
	 * @param file
	 * @throws CoreException
	 */
	public JsonTextValidator(IFile file, String markerId) throws CoreException {
		this.parser = new JsonFileReader(file);
		this.markerId = markerId;
	}

	/**
	 * Constructor taking a File object.
	 * @param file
	 * @throws FileNotFoundException
	 */
	public JsonTextValidator(File file) throws FileNotFoundException {
		this.parser = new JsonFileReader(file);
		this.markerId = null;
	}

	/**
	 * Parse the file and report the first error found.
	 */
	public void parse() {
		LOG.info("Validating JSON file");
		try {

			char current = parser.getNextClean();

			if (current == openCurly) {
				doJsonObject();
			} else if (current == openSquare) {
				doJsonArray();
			} else {
				reportProblemEndValidation("JSON should begin with { or [", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
			}

			if (parser.getCurrent() != eof) {
				reportProblemEndValidation("Unexpected character", new Location(parser.getIFile(),"", parser.getPosition()), 0, true);
			}

		} catch (JsonValidationException e) {

		} catch (JsonReaderException e) {
			reportProblem("Unexpected end of file", new Location(parser.getIFile(),"", parser.getPosition()), 0, true);
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
			if (ch == closeCurly && parser.getPrevious() != comma) {
				parser.getNextClean();
				break;
			}

			if (ch != quote) {
				reportProblemEndValidation("JSON key should begin with \"", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
			}
			doJsonKey();

			ch = parser.getNextClean();
			if (ch != colon) {
				reportProblemEndValidation("Expected colon key/value delimitor", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
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
				reportProblemEndValidation("Expected JSON value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
			}

			if (parser.getCurrent() == comma) {
				continue;
			}

			if (parser.getCurrent() == closeCurly && parser.getPrevious() != comma) {
				parser.getNextClean();
				break;
			}

			reportProblemEndValidation("Unexpected object character:" + ch, new Location(parser.getIFile(),"", parser.getPosition()),0, true);
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

			if (ch == closeSquare && parser.getPrevious() != comma) {
				parser.getNextClean();
				break;
			}

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
			} else {
				reportProblemEndValidation("Expected JSON value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
			}

			ch = parser.getCurrent();
			if (ch == comma) {
				continue;
			}

			if (ch == closeSquare && parser.getPrevious() != comma) {
				parser.getNextClean();
				break;
			}

			reportProblemEndValidation("Unexpected array character:" + ch, new Location(parser.getIFile(),"", parser.getPosition()),0, true);
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
			reportProblemEndValidation("Expect true value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
		}

		ch = parser.getNextChar();
		if (ch != u) {
			reportProblemEndValidation("Expect true value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
		}

		ch = parser.getNextChar();
		if (ch != e) {
			reportProblemEndValidation("Expect true value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
		}

		ch = parser.getNextClean();
		if (isNotClosed(ch)) {
			reportProblemEndValidation("Expected end value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
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
			reportProblemEndValidation("Expect true value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
		}

		ch = parser.getNextChar();
		if (ch != l) {
			reportProblemEndValidation("Expect true value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
		}

		ch = parser.getNextChar();
		if (ch != s) {
			reportProblemEndValidation("Expect true value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
		}

		ch = parser.getNextChar();
		if (ch != e) {
			reportProblemEndValidation("Expect true value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
		}

		ch = parser.getNextClean();
		if (isNotClosed(ch)) {
			reportProblemEndValidation("Expected end value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
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
			reportProblemEndValidation("Expect null value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
		}

		ch = parser.getNextChar();
		if (ch != l) {
			reportProblemEndValidation("Expect null value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
		}

		ch = parser.getNextChar();
		if (ch != l) {
			reportProblemEndValidation("Expect null value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
		}

		ch = parser.getNextClean();
		if (isNotClosed(ch)) {
			reportProblemEndValidation("Expected end value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
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
				reportProblemEndValidation("Expected quotation", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
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
		boolean exponentialSet = false;
		boolean waitingOnExponential = false;
		char ch;
		do {
			ch = parser.getNextChar();
			if (Character.isDigit(ch)) {
				waitingOnExponential = false;
				continue;
			}

			if (isClosed(ch) && !waitingOnExponential) {
				break;
			}

			if (!decimalPointSet && ch == point) {
				decimalPointSet = true;
				continue;
			}

			if (!exponentialSet && (ch == e || ch == E)) {
				exponentialSet = true;
				waitingOnExponential = true;
				continue;
			}

			if ((ch == plus || ch == minus) && (parser.getPrevious() == e || parser.getPrevious() == E)) {
				continue;
			}

			if (isNotWhiteSpace(ch)) {
				reportProblemEndValidation("Value " + ch + " not expected here", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
			}

			ch = parser.getNextClean();
			if (isNotClosed(ch)) {
				reportProblemEndValidation("Expected end value", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
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
				reportProblemEndValidation("Invalid JSON key, no closing \"", new Location(parser.getIFile(),"", parser.getPosition()),0, true);
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
			IMarker marker = loc.file.createMarker(markerId);
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

	public void reportProblemEndValidation(String msg, Location loc, int violation, boolean isError) throws JsonValidationException {
		reportProblem(msg, loc, violation, isError);
		throw new JsonValidationException();
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