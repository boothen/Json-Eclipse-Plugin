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


import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

/**
 * JsonLabelProvider provides the label format for each element in the tree.
 */
public class JsonLabelProvider extends ColumnLabelProvider implements IStyledLabelProvider  {

    private final JsonContextLabelVisitor contextLabelVisitor;
    private final JsonContextImageVisitor contextImageVisitor = new JsonContextImageVisitor();

    /**
     * @param preferenceStore the preference store that provides the text style
     */
    public JsonLabelProvider(IPreferenceStore preferenceStore) {
        contextLabelVisitor = new JsonContextLabelVisitor(preferenceStore);
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
