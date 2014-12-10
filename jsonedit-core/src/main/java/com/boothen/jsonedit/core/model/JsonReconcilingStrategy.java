package com.boothen.jsonedit.core.model;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.swt.widgets.Display;

import com.boothen.jsonedit.core.editors.JsonTextEditor;
import com.boothen.jsonedit.core.model.jsonnode.JsonNode;
import com.boothen.jsonedit.core.model.jsonnode.JsonNodeBuilder;
import com.boothen.jsonedit.core.model.node.Node;
import com.boothen.jsonedit.core.model.node.NodeBuilder;
import com.boothen.jsonedit.folding.JsonFoldingPositionsBuilder;

public class JsonReconcilingStrategy implements IReconcilingStrategy,
IReconcilingStrategyExtension {

	private JsonTextEditor textEditor;

	private IDocument fDocument;

	@Override
	public void reconcile(IRegion partition) {
		initialReconcile();
	}

	@Override
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		initialReconcile();
	}

	@Override
	public void setDocument(IDocument document) {
		this.fDocument = document;

	}

	@Override
	public void initialReconcile() {

		parse();
	}

	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {

	}

	private void parse() {

		final List<Node> nodes = new NodeBuilder(fDocument).buildNodes();

		final List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();
		final List<Position> fPositions = new JsonFoldingPositionsBuilder(jsonNodes).buildFoldingPositions();

		if (textEditor != null) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
//					textEditor.updateFoldingStructure(fPositions);
					textEditor.updateContentOutlinePage(jsonNodes, nodes);
				}

			});
		}
	}

	public void setTextEditor(JsonTextEditor textEditor) {
		this.textEditor = textEditor;
	}
}
