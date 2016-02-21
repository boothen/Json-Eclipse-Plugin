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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Provides the Tree Structure for the outline view.
 * It uses the original syntax tree as provided by ANTLR.
 */
public class JsonContentProvider implements ITreeContentProvider {

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // must be implemented
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof ParserRuleContext) {
            ParserRuleContext context = (ParserRuleContext) parentElement;
            List<Object> children = new ArrayList<>();
            for (int i = 0; i < context.getChildCount(); i++) {
                ParseTree child = context.getChild(i);
                if (matches(child)) {
                    children.add(child);
                }
            }
            return children.toArray();
        }

        return new Object[0];
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof ParserRuleContext) {
            ParserRuleContext context = (ParserRuleContext) element;
            for (int i = 0; i < context.getChildCount(); i++) {
                ParseTree child = context.getChild(i);
                if (matches(child)) {
                    // there is at least one matching child
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matches(ParseTree child) {
        return (child instanceof ParserRuleContext);
    }

    @Override
    public Object getParent(Object element) {
        if (element instanceof ParserRuleContext) {
            ParserRuleContext context = (ParserRuleContext) element;
            return context.getParent();
        }

        return null;
    }

    @Override
    public void dispose() {
        // must be implemented
    }
}
