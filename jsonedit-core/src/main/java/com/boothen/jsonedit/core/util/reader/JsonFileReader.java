package com.boothen.jsonedit.core.util.reader;

import static com.boothen.jsonedit.core.util.JsonCharUtility.isWhiteSpace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

/**
 * JsonFileReader reads an IFile or File object.
 *
 * @author Matt Garner
 *
 */
public class JsonFileReader implements JsonReader {

	private Reader reader;

	private char previous;

	private char current;

	private int position;

	private IFile iFile;

	private File file;


	public JsonFileReader(IFile iFile) throws CoreException {
		this.iFile = iFile;
		InputStream stream = iFile.getContents();
		reader = new BufferedReader(new InputStreamReader(stream));
	}

	public JsonFileReader(File file) throws FileNotFoundException {
		this.file = file;
		InputStream stream = new FileInputStream(file);
		reader = new BufferedReader(new InputStreamReader(stream));
	}

	public char getPrevious() {
		return previous;
	}

	public char getCurrent() {
		return current;
	}

	public char getNextClean() throws JsonReaderException {

		char ch = ' ';
		while (isWhiteSpace(ch)) {
			ch = next();
		}
		previous = current;
		current = ch;
		return current;
	}

	public char getNextChar() throws JsonReaderException {
		char ch = next();
		previous = current;
		current = ch;
		return current;
	}

	private char next() throws JsonReaderException {
		try {
			position++;
			char ch = (char) reader.read();
			return ch;
		} catch (IOException e) {
			throw new JsonReaderException();
		}
	}

	public int getPosition() {
		return position - 1;
	}

	/**
	 * Returns the IFile.
	 * @return
	 */
	public IFile getIFile() {
		return iFile;
	}

	/**
	 * Returns the File object.
	 * @return
	 */
	public File getFile() {
		return file;
	}

}
