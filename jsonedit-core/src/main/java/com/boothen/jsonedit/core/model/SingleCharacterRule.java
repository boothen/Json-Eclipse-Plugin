package com.boothen.jsonedit.core.model;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class SingleCharacterRule implements IPredicateRule {

	private final char tChar;
	private final IToken success;
	
	public SingleCharacterRule(char tChar, IToken success) {
		this.tChar = tChar;
		this.success = success;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		int i = scanner.read();
		if (i == -1) {
			return Token.EOF;
		}
		
		if (i == tChar) {
			return success;
		}
		
		scanner.unread();
		return Token.UNDEFINED;
	}

	@Override
	public IToken getSuccessToken() {
		return success;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}

}
