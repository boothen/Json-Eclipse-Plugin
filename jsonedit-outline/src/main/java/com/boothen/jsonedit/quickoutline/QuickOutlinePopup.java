/*******************************************************************************
 * Copyright 2016 Boothen Technology Ltd.
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

package com.boothen.jsonedit.quickoutline;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.text.AbstractInformationControl;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import com.boothen.jsonedit.outline.Container;
import com.boothen.jsonedit.outline.JsonContentProvider;
import com.boothen.jsonedit.outline.JsonLabelProvider;

public class QuickOutlinePopup extends AbstractInformationControl implements IInformationControlExtension2 {

    private final JsonContentProvider contentProvider = new JsonContentProvider();
    private final JsonLabelProvider labelProvider = new JsonLabelProvider();

    private TreeViewer treeViewer;
    private final ISourceViewer mEditor;
    private Text filterText;

    public QuickOutlinePopup(Shell parentShell, ISourceViewer editor) {
        super(parentShell, "Regular expressions enabled");
        mEditor = editor;
        create();
    }

    @Override
    protected void createContent(Composite parent) {
        parent.setLayout(GridLayoutFactory
                .fillDefaults()
                .margins(PopupDialog.POPUP_MARGINWIDTH, PopupDialog.POPUP_MARGINHEIGHT)
                .spacing(PopupDialog.POPUP_HORIZONTALSPACING, PopupDialog.POPUP_VERTICALSPACING)
                .create());

        filterText = createFilterText(parent);

        final NamePatternFilter namePatternFilter = new NamePatternFilter();
        filterText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                String text = ((Text) e.widget).getText();
                namePatternFilter.setPattern(text);
                stringMatcherUpdated();
            }
        });

        Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
        separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        createTreeViewer(parent);

//        final DelegatingStyledCellLabelProvider delegatingStyledCellLabelProvider = new DelegatingStyledCellLabelProvider(new JsonLabelProvider());
        treeViewer.setLabelProvider(labelProvider);
        treeViewer.addFilter(namePatternFilter);
    }

    /**
     * The string matcher has been modified. The default implementation
     * refreshes the view and selects the first matched element
     */
    protected void stringMatcherUpdated() {
            // refresh viewer to re-filter
            treeViewer.getControl().setRedraw(false);
            treeViewer.refresh();
            treeViewer.expandAll();
            treeViewer.getControl().setRedraw(true);
    }

    @Override
    public void setInput(Object input) {
        ParseTree parseTree = (ParseTree) input;
        treeViewer.setInput(new Container<ParseTree>(parseTree));
        filterText.setText("");

        // In theory, it should be possible to derive the parent,
        // but this is not trivial and needs to be reliable
        contentProvider.refreshParents(parseTree);

        final ISelection selection = mEditor.getSelectionProvider().getSelection();

        if (selection instanceof ITextSelection) {
            final ITextSelection textSelection = (ITextSelection) selection;
            final int start = textSelection.getOffset();

            //            final JsonElement found = mFinder.findSelectedScope(start, (JsonElement) input);
            Object found = null;

            if (found != null) {

                final TreeSelection treeSelection = new TreeSelection(new TreePath(new Object[] { found }));

                treeViewer.reveal(found);
                treeViewer.setSelection(treeSelection);
            }
        }

        treeViewer.expandAll();
    }

    @Override
    public void setFocus() {
//        getShell().forceFocus();
        filterText.setFocus();
    }

    @Override
    public boolean hasContents() {
        return true;
    }

    private void createTreeViewer(Composite parent) {
        Tree tree = new Tree(parent, SWT.SINGLE);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.heightHint = tree.getItemHeight() * 12;
        tree.setLayoutData(gd);

        applyInfoColor(tree);

        treeViewer = new TreeViewer(tree);
        treeViewer.setLabelProvider(labelProvider);
        treeViewer.setContentProvider(contentProvider);
        treeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);

        treeViewer.getTree().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.character == SWT.ESC) // ESC
                    dispose();
            }
        });

        tree.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                gotoSelectedElement();
            }
        });
    }

    private Text createFilterText(Composite parent) {
        Text text = new Text(parent, SWT.NONE);
        Dialog.applyDialogFont(text);

        GridData data= new GridData(GridData.FILL_HORIZONTAL);
        data.verticalIndent = 2;
        data.horizontalAlignment= GridData.FILL;
        data.verticalAlignment= GridData.CENTER;
        text.setLayoutData(data);

        applyInfoColor(text);

        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) // Enter keys
                    gotoSelectedElement();
                if (e.keyCode == SWT.ARROW_DOWN)
                    treeViewer.getTree().setFocus();
                if (e.keyCode == SWT.ARROW_UP)
                    treeViewer.getTree().setFocus();
                if (e.character == SWT.ESC) // ESC
                    dispose();
            }
        });

        return text;
    }

    private void gotoSelectedElement() {
        // TODO: select
    }

    private void applyInfoColor(Control control) {
        Display display = getShell().getDisplay();
        Color foreground = display.getSystemColor(SWT.COLOR_INFO_FOREGROUND);
        Color background = display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
        control.setForeground(foreground);
        control.setBackground(background);
    }
}
