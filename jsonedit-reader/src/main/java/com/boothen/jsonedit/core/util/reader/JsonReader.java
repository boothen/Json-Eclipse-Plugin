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
package com.boothen.jsonedit.core.util.reader;

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