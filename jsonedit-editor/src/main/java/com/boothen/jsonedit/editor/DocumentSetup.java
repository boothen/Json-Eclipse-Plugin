package com.boothen.jsonedit.editor;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;

/**
 * Registers a position updater that tracks changes for the {@link JsonTextEditor#JSON_CATEGORY} positions.
 * This class is managed through an extension point.
 */
public class DocumentSetup implements IDocumentSetupParticipant {

    private final IPositionUpdater updater = new DefaultPositionUpdater(JsonTextEditor.JSON_CATEGORY);

    @Override
    public void setup(IDocument document) {
        document.addPositionCategory(JsonTextEditor.JSON_CATEGORY);
        document.addPositionUpdater(updater);
    }

}
