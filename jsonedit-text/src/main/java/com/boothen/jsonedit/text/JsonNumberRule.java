package com.boothen.jsonedit.text;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class JsonNumberRule implements IPredicateRule {
	
	public static final char MINUS = '-';
	public static final char E = 'E';
	public static final char e = 'e';
	public static final char PLUS = '+';
	public static final char POINT = '.';
	
	private final IToken success;
	
	public JsonNumberRule(IToken success) {
		this.success = success;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		return evaluate(scanner, false);
	}

	@Override
	public IToken getSuccessToken() {
		return success;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		int read = scanner.read();
		
		if (read == -1) {
			return Token.EOF;
		}
		
		if (!resume) {
			if (!isStartOfNumber(read)) {
				scanner.unread();
				return Token.UNDEFINED;
			}
		}
		
		int current = doDigit(scanner);
		if (current == POINT) {
			current = doFrac(scanner);
		}
		
		if (current == E || current == e) {
			doExp(scanner);
		}
		
		scanner.unread();
		return success;
	}
	
	private int doExp(ICharacterScanner scanner) {
		return doDigit(scanner);
	}

	private int doFrac(ICharacterScanner scanner) {
		return doDigit(scanner);
	}

	private int doDigit(ICharacterScanner scanner) {

		int ch = scanner.read();
		while (Character.isDigit(ch)) {
			ch = scanner.read();
		}

		return ch;
	}

	private boolean isStartOfNumber(int ch) {
		if (ch == MINUS || Character.isDigit(ch)) {
			return true;
		}
		return false;
	}
}
