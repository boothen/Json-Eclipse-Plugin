package com.boothen.jsonedit.core.model;

import org.eclipse.jface.text.rules.IWordDetector;

public class JsonConstantWordDetector implements IWordDetector {
	
	private final String string;
	
	public JsonConstantWordDetector(String string) {
		this.string = string;
	}

	@Override
	public boolean isWordStart(char c) {
		return string.startsWith(Character.toString(c));
	}

	@Override
	public boolean isWordPart(char c) {
		return string.contains(Character.toString(c));
	}

}
