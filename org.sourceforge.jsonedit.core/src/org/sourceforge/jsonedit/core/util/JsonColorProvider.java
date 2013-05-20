/**
 * 
 */
package org.sourceforge.jsonedit.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.sourceforge.jsonedit.core.JsonEditorPlugin;

/**
 * Provides the coloring for the text editor.
 * 
 * @author Matt Garner
 *
 */
public class JsonColorProvider {
	
	public static final String STRING = "colorString";
	public static final String DEFAULT = "colorDefault";
	public static final String VALUE = "colorValue";
	public static final String NULL = "colorNull";
	
	protected Map<String, Color> fColorTable= new HashMap<String, Color>(10);
	
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
	public Color getColor(String rgb) {
		
		Color color= (Color) fColorTable.get(rgb);
		
		if (color == null) {
			color= new Color(Display.getCurrent(), PreferenceConverter.getColor(JsonEditorPlugin.getJsonPreferenceStore(), rgb));
			fColorTable.put(rgb, color);
		}
		
		return color;
	}
}
