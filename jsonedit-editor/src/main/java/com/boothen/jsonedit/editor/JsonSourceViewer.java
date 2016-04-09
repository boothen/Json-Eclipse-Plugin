package com.boothen.jsonedit.editor;

import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * A source code viewer for JSON that supports additional operations such as displaying a quick outline info popup.
 */
public class JsonSourceViewer extends ProjectionViewer {

    /**
     * Text operation code for requesting the outline for the current input.
     */
    public static final int SHOW_OUTLINE = 51;

    private IInformationPresenter fOutlinePresenter;

    /**
     * Creates a new JSON projection source viewer.
     *
     * @param parent the SWT parent control
     * @param ruler the vertical ruler
     * @param overviewRuler the overview ruler
     * @param showAnnotationsOverview <code>true</code> if the overview ruler should be shown
     * @param styles the SWT style bits
     */
    public JsonSourceViewer(Composite parent, IVerticalRuler ruler, IOverviewRuler overviewRuler,
            boolean showAnnotationsOverview, int styles) {
        super(parent, ruler, overviewRuler, showAnnotationsOverview, styles);
    }

    @Override
    public void configure(SourceViewerConfiguration configuration) {
        super.configure(configuration);
        if (configuration instanceof JsonSourceViewerConfiguration) {
            JsonSourceViewerConfiguration jsonConfig = (JsonSourceViewerConfiguration) configuration;
            fOutlinePresenter = jsonConfig.getOutlinePresenter(this);
            if (fOutlinePresenter != null) {
                fOutlinePresenter.install(this);
            }

        }
    }

    @Override
    public void unconfigure() {
        if (fOutlinePresenter != null) {
            fOutlinePresenter.uninstall();
            fOutlinePresenter = null;
        }
        super.unconfigure();
    }

    @Override
    public boolean canDoOperation(int operation) {
        if (operation == SHOW_OUTLINE) {
            return fOutlinePresenter != null;
        }

        return super.canDoOperation(operation);
    }

    @Override
    public void doOperation(int operation) {
        if (getTextWidget() == null)
            return;

        switch (operation) {
        case SHOW_OUTLINE:
            if (fOutlinePresenter != null)
                fOutlinePresenter.showInformation();
            return;
        }

        super.doOperation(operation);
    }
}
