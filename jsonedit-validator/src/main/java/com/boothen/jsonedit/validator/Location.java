package com.boothen.jsonedit.validator;

import org.eclipse.core.resources.IFile;

public class Location {

	public final IFile file;
	public final String key;
	public final int charStart;
	public final int charEnd;

	public Location(IFile file, String key, int charStart, int charEnd) {
		this.file = file;
		this.key = key;
		this.charStart = charStart;
		this.charEnd = charEnd;
	}

	public Location(IFile file, String key, int charStart) {
		this.file = file;
		this.key = key;
		this.charStart = charStart;
		this.charEnd = charStart + 1;
	}
}


