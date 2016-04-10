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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Provides the Tree Structure for the outline view.
 * It uses the original syntax tree as provided by ANTLR.
 */
public class JsonContentProvider implements ITreeContentProvider {

    private final JsonContextTreeFilter treeFilter = new JsonContextTreeFilter();
    private final ParentProvider parentProvider = new ParentProvider(this);
    private Map<Object, Object> parents = Collections.emptyMap();

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // must be implemented
    }

    @Override
    public Object[] getElements(Object inputElement) {
        // the root input element is wrapped a Container
        Container<?> container = (Container<?>) inputElement;
        return getChildren(container.getContent());
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof ParserRuleContext) {
            ParserRuleContext context = (ParserRuleContext) parentElement;
            List<ParseTree> children = context.accept(treeFilter);
            return children.toArray();
        }

        return new Object[0];
    }

    @Override
    public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }

    @Override
    public Object getParent(Object element) {
        Object parent = parents.get(element);
        return parent;
    }

    @Override
    public void dispose() {
        // must be implemented
    }

    /**
     * This method must be called when the content of the root container has changed.
     * It should be possible to derive the parent element directly, though.
     * @param tree the syntax tree that is walked to identify parent elements
     */
    public void refreshParents(ParseTree tree) {
        parents = parentProvider.record(tree);
    }

    /**
     * @param element the element to test for existance in the syntax tree
     * @return true if known
     */
    public boolean isKnown(ParseTree element) {
        return parents.containsKey(element);
    }
}
