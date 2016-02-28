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
        TreeNode<?> node = (TreeNode<?>) parentElement;
        return node.getChildren().toArray();
    }

    @Override
    public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }

    @Override
    public Object getParent(Object element) {
        TreeNode<?> node = (TreeNode<?>) element;
        return node.getParent();
    }

    @Override
    public void dispose() {
        // must be implemented
    }
}
