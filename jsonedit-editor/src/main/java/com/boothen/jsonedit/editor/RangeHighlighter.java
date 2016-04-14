package com.boothen.jsonedit.editor;

import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.editors.text.TextEditor;

import com.boothen.jsonedit.model.JsonContextTokenFinder;
import com.boothen.jsonedit.model.ParseTreeInfo;
import com.boothen.jsonedit.model.Segment;

/**
 * Updates the text editors highlight range (bluish line left of the vertical ruler)
 * according to the current text selection.
 */
public class RangeHighlighter implements ISelectionListener {

    private TextEditor textEditor;
    private ParseTree jsonContext;
    private Map<ParseTree, Position> positions;

    /**
     * @param textEditor the text editor to use
     */
    public RangeHighlighter(TextEditor textEditor) {
        this.textEditor = textEditor;
    }

    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        if (jsonContext == null) {
            return;
        }

        if (selection instanceof ITextSelection) {
            ITextSelection textSelection = (ITextSelection) selection;
            int start = textSelection.getOffset();
            int length = textSelection.getLength();
            ParseTree element = jsonContext.accept(new JsonContextTokenFinder(start, start + length, positions));
            // TODO: consider using the same rules as the FoldingVisitor
            if (element instanceof TerminalNode) {
                element = element.getParent();
            }
            if (element != null) {
                Segment seg = ParseTreeInfo.getSegment(element);
                textEditor.setHighlightRange(seg.getStart(), seg.getLength(), false);
            } else {
                textEditor.resetHighlightRange();
            }
        }
    }

    /**
     * @param tree the syntax tree
     * @param positions maps tree elements to document positions
     */
    public void setInput(ParseTree tree, Map<ParseTree, Position> positions) {
        this.jsonContext = tree;
        this.positions = positions;
    }

}
