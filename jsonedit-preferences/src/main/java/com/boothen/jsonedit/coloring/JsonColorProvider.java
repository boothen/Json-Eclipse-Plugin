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
/**
 *
 */
package com.boothen.jsonedit.coloring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Provides the coloring for the text editor.
 *
 * @author Matt Garner
 *
 */
public class JsonColorProvider {

	public static final RGB STRING = new RGB(0, 128, 0);
	public static final RGB DEFAULT = new RGB(0, 0, 0);
	public static final RGB VALUE = new RGB(0, 0, 128);
	public static final RGB NULL = new RGB(128, 0, 128);
	public static final RGB ERROR = new RGB(255, 0, 0);

	protected Map<RGB, Color> fColorTable= new HashMap<RGB, Color>(10);

	/**
	 * Release all of the color resources held onto by the receiver.
	 */
	public void dispose() {
		Iterator<Color> e = fColorTable.values().iterator();
		while (e.hasNext())
			 e.next().dispose();
	}

	/**
	 * Return the color that is stored in the color table under the given RGB
	 * value.
	 *
	 * @param rgb the RGB value
	 * @return the color stored in the color table for the given RGB value
	 */
	public Color getColor(RGB rgb) {
		Color color= (Color) fColorTable.get(rgb);
		if (color == null) {
			color= new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}
}
