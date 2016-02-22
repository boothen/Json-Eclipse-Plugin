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
package com.boothen.jsonedit.outline;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import com.boothen.jsonedit.antlr.JSONParser.ArrayContext;
import com.boothen.jsonedit.outline.node.JsonTreeNode;
import com.boothen.jsonedit.type.JsonDocumentType;

/**
 * JsonLabelProvider provides the label format for each element in the tree.
 *
 * @author Matt Garner
 *
 */
public class JsonLabelProvider extends ColumnLabelProvider implements IStyledLabelProvider  {

    private final JsonContextLabelVisitor contextLabelVisitor = new JsonContextLabelVisitor();
    private final JsonContextImageVisitor contextImageVisitor = new JsonContextImageVisitor();

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
     */
    @Override
    public String getText(Object element) {
        return getStyledText(element).toString();
    }

    @Override
    public Image getImage(Object element) {
        if (element instanceof ParseTree) {
            ParseTree context = (ParseTree) element;
            return context.accept(contextImageVisitor);
        }

        return null;
    }

    /**
     * Returns the styled text contained in the tree element.
     */
    @Override
    public StyledString getStyledText(Object element) {
        StyledString styledString = new StyledString();
        if (element instanceof ParseTree) {
            ParseTree node = (ParseTree) element;
            return contextLabelVisitor.visit(node);
        }
        return styledString;
    }

    @Override
    public void dispose() {
        contextImageVisitor.dispose();
        super.dispose();
    }


}
