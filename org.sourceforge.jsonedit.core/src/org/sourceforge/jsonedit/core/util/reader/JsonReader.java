package org.sourceforge.jsonedit.core.util.reader;

import java.io.IOException;

/**
 * JsonReader interface.
 * 
 * @author Matt Garner
 *
 */
public interface JsonReader {
	
	/**
	 * Returns the previous character read from the current position.
	 * @return
	 */
	public abstract char getPrevious();
	
	/**
	 * Returns the current character in the position.
	 * @return
	 */
	public abstract char getCurrent();

	/**
	 * Returns next non-whitespace character or -1 if end of reader.
	 * @return
	 * @throws IOException
	 */
	public abstract char getNextClean() throws JsonReaderException;
	
	/**
	 * Returns the next character in the reader.
	 * @return
	 * @throws JsonReaderException
	 */
	public abstract char getNextChar() throws JsonReaderException;
	
	/**
	 * Returns the current position of the reader.
	 * @return
	 */
	public abstract int getPosition();

}