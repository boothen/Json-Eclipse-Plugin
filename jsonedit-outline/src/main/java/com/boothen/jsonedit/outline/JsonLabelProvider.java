/**
 *
 */
package com.boothen.jsonedit.outline;


import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import com.boothen.jsonedit.outline.node.JsonTreeNode;

/**
 * JsonLabelProvider provides the label format for each element in the tree.
 *
 * @author Matt Garner
 *
 */
public class JsonLabelProvider extends ColumnLabelProvider implements IStyledLabelProvider  {

	static {
		ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
		colorRegistry.put("GREEN", new RGB(0,128,0));
		colorRegistry.put("WHITE", new RGB(255,255,255));
		colorRegistry.put("BLACK", new RGB(0,0,0));
		colorRegistry.put("BLUE", new RGB(0,0,128));
		colorRegistry.put("RED", new RGB(128,0,0));
		colorRegistry.put("PURPLE", new RGB(128,0,128));
	}

	/**
	 * Returns the text contained in the tree element.
	 *
	 */
	@Override
	public String getText(Object element) {
		return getStyledText(element).toString();

	}

	/**
	 * The <code>LabelProvider</code> implementation of this
	 * <code>ILabelProvider</code> method returns <code>null</code>.
	 * Subclasses may override.
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof JsonTreeNode) {
			JsonTreeNode node = (JsonTreeNode) element;
			return node.getImage();
		}
		return null;
	}

	/**
	 * Returns the styled text contained in the tree element.
	 */
	@Override
	public StyledString getStyledText(Object element) {
		StyledString styledString = new StyledString();
		if (element instanceof JsonTreeNode) {
			JsonTreeNode node = (JsonTreeNode) element;
			return node.getStyledString();
		}
		return styledString;
	}

}
