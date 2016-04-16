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

import java.util.Map;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.text.AbstractInformationControl;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
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
import org.eclipse.swt.widgets.TreeItem;

import com.boothen.jsonedit.core.JsonCorePlugin;
import com.boothen.jsonedit.core.JsonLog;
import com.boothen.jsonedit.model.JsonContextTokenFinder;
import com.boothen.jsonedit.model.ParseTreeInfo;
import com.boothen.jsonedit.model.Segment;
import com.boothen.jsonedit.outline.Container;
import com.boothen.jsonedit.outline.JsonContentProvider;
import com.boothen.jsonedit.outline.JsonLabelProvider;
import com.boothen.jsonedit.text.PositionVisitor;

/**
 * An information control that shows a quick outline of an ANTLR {@link ParseTree} inside a shell.
 */
public class QuickOutlinePopup extends AbstractInformationControl implements IInformationControlExtension2 {

    private final JsonContentProvider contentProvider = new JsonContentProvider();
    private final JsonLabelProvider labelProvider = new JsonLabelProvider(
            JsonCorePlugin.getDefault().getPreferenceStore());

    private final ISourceViewer sourceViewer;

    private TreeViewer treeViewer;
    private Text filterText;

    private Pattern pattern = Pattern.compile(".*");

    /**
     * Creates an abstract information control with the given shell as parent.
     * The control will not be resizable and show a status line.
     *
     * @param parentShell the parent of this control's shell
     * @param sourceViewer the linked source viewer
     */
    public QuickOutlinePopup(Shell parentShell, ISourceViewer sourceViewer) {
        super(parentShell, "Regular expressions enabled");
        this.sourceViewer = sourceViewer;
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

        final NamePatternFilter namePatternFilter = new NamePatternFilter(labelProvider); // use unstyled text
        filterText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                String text = ((Text) e.widget).getText();
                pattern = Pattern.compile(text, Pattern.CASE_INSENSITIVE);
                namePatternFilter.setPattern(pattern);
                stringMatcherUpdated();
            }
        });

        Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
        separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        createTreeViewer(parent);

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
            selectFirstMatch();
            treeViewer.getControl().setRedraw(true);
    }

    /**
     * Selects the first element in the tree which matches the current filter pattern.
     */
    private void selectFirstMatch() {
        final Tree tree = treeViewer.getTree();
        TreeItem element = findElement(tree.getItems());

        if (element != null) {
            tree.setSelection(element);
            tree.showItem(element);
        } else {
            treeViewer.setSelection(StructuredSelection.EMPTY);
        }
    }

    private TreeItem findElement(TreeItem[] items) {

        // First search at same level
        for (int i = 0; i < items.length; i++) {
            final TreeItem item = items[i];
            ParseTree element = (ParseTree) item.getData();
            if (element != null) {
                String label = labelProvider.getText(element);
                if (pattern.matcher(label).find()) {
                    return item;
                }
            }
        }

        // Go one level down for each item
        for (int i = 0; i < items.length; i++) {
            final TreeItem item = items[i];
            TreeItem foundItem = findElement(item.getItems());
            if (foundItem != null) {
                return foundItem;
            }
        }

        return null;
    }

    @Override
    public void setInput(Object input) {
        ParseTree parseTree = (ParseTree) input;
        treeViewer.setInput(new Container<ParseTree>(parseTree));
        filterText.setText("");

        // In theory, it should be possible to derive the parent,
        // but this is not trivial and needs to be reliable
        contentProvider.refreshParents(parseTree);

        final ISelection selection = sourceViewer.getSelectionProvider().getSelection();

        // try to find the element that contains the text cursor in sourceViewer and pre-select it
        if (selection instanceof ITextSelection) {
            ITextSelection textSelection = (ITextSelection) selection;
            int start = textSelection.getOffset();
            int length = textSelection.getLength();

            PositionVisitor positionVisitor = new PositionVisitor();
            Map<ParseTree, Position> positions = parseTree.accept(positionVisitor);

            ParseTree element = parseTree.accept(new JsonContextTokenFinder(start, start + length, positions));
            // similar code exists in JsonContentOutlinePage
            while (element != null && !contentProvider.isKnown(element)) {
                element = element.getParent();
            }

            if (element != null) {
                treeViewer.reveal(element);
                treeViewer.setSelection(new TreeSelection(new TreePath(new Object[] { element })));
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
        tree.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                gotoSelectedElement();
            }
        });

        applyInfoColor(tree);

        treeViewer = new TreeViewer(tree);
        treeViewer.setContentProvider(contentProvider);
        treeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
        // wrap in DSCLP to forward the styled text to the tree viewer
        treeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(labelProvider));
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
                    hide();
            }
        });

        return text;
    }

    private void gotoSelectedElement() {
        // getStructuredSelection() is not yet available in indigo (3.7)
        IStructuredSelection viewerSelection = (IStructuredSelection) treeViewer.getSelection();
        Object selectedElement = viewerSelection.getFirstElement();

        ParseTree treeNode = (ParseTree) selectedElement;

        try {
            Segment segment = ParseTreeInfo.getSegment(treeNode);
            StyledText widget= sourceViewer.getTextWidget();
            if (segment != null) {
                int start = segment.getStart();
                int length = segment.getLength();
                widget.setRedraw(false);
                sourceViewer.revealRange(start, length);
                sourceViewer.setSelectedRange(start, length);
                widget.setRedraw(true);
                hide();
            }
        } catch (IllegalArgumentException e) {
            JsonLog.logError("Could not open selected element from quick outline", e);
        }
    }

    private void hide() {
        setVisible(false);
    }

    private void applyInfoColor(Control control) {
        Display display = getShell().getDisplay();
        Color foreground = display.getSystemColor(SWT.COLOR_INFO_FOREGROUND);
        Color background = display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
        control.setForeground(foreground);
        control.setBackground(background);
    }
}
