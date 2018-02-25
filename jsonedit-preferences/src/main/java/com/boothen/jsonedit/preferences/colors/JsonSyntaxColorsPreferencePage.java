package com.boothen.jsonedit.preferences.colors;

import java.io.IOException;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.core.BundleUtils;
import com.boothen.jsonedit.core.JsonCorePlugin;
import com.boothen.jsonedit.core.JsonLog;
import com.boothen.jsonedit.core.preferences.TokenStyle;
import com.boothen.jsonedit.model.AntlrTokenScanner;
import com.boothen.jsonedit.preferences.Activator;
import com.boothen.jsonedit.preferences.JsonTokenStyler;
import com.boothen.jsonedit.preferences.OverlayPreferenceStore;

/**
 * Preference page for color and text style configuration
 */
public class JsonSyntaxColorsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private static final int ROW_HEIGHT = 22;
    private TextViewer textViewer;
    private OverlayPreferenceStore preferenceStore;

    public JsonSyntaxColorsPreferencePage() {
        // TODO: implement performDefaults: update existing widgets with changed selection
        noDefaultAndApplyButton();
    }

    @Override
    public void init(IWorkbench workbench) {
        IPreferenceStore orgStore = JsonCorePlugin.getDefault().getPreferenceStore();
        preferenceStore = new OverlayPreferenceStore(orgStore);
        setPreferenceStore(preferenceStore);
    }

    @Override
    protected Control createContents(Composite parent) {
        final Composite container = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2 * 4 + 1;
        container.setLayout(gridLayout);

        for (TokenStyle style : TokenStyle.values()) {
            createLabel(container, style);
            createColorSelector(container, style);
            createBoldButton(container, style);
            createItalicButton(container, style);
            if (style.ordinal() % 2 == 0) {
                Label sep = new Label(container, SWT.SEPARATOR | SWT.VERTICAL);
                sep.setLayoutData(new GridData(SWT.DEFAULT, ROW_HEIGHT));
            }
        }

        textViewer = createTextViewer(container, gridLayout.numColumns);

        createFontHint(container, gridLayout.numColumns);

        return container;
    }

    private void createFontHint(final Composite container, int numColumns) {
        Link fontHint = new Link(container, SWT.NONE);
        fontHint.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    Shell shell = container.getShell();
                    if ("org.eclipse.ui.preferencePages.GeneralTextEditor".equals(e.text)) //$NON-NLS-1$
                            PreferencesUtil.createPreferenceDialogOn(shell, e.text, null, null);
                    else if ("org.eclipse.ui.preferencePages.ColorsAndFonts".equals(e.text)) //$NON-NLS-1$
                            PreferencesUtil.createPreferenceDialogOn(shell, e.text, null,
                                    "selectFont:com.boothen.jsonedit.fonts.textfont"); //$NON-NLS-1$
                }
        });
        fontHint.setText("See <a href=\"org.eclipse.ui.preferencePages.GeneralTextEditor\">"
                + "Text Editors</a> to adjust text rendering and "
                + "<a href=\"org.eclipse.ui.preferencePages.ColorsAndFonts\">Colors and Fonts</a> "
                + "to change the font.");

        GridData fontHintData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        fontHintData.widthHint = 150; // only expand further if anyone else requires it
        fontHintData.horizontalSpan = numColumns;
        fontHint.setLayoutData(fontHintData);
    }


    private Label createLabel(Composite container, TokenStyle style) {
        Label label = new Label(container, SWT.NONE);
        label.setText(style.toString());
        return label;
    }

    private Button createBoldButton(Composite parent, final TokenStyle style) {
        boolean isBold = getPreferenceStore().getBoolean(style.isBold());
        Button button = new Button(parent, SWT.CHECK);
        GridData layoutData = new GridData();
        button.setLayoutData(layoutData);
        button.setSelection(isBold);
        button.setText("Bold");
        button.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                Button source = (Button) e.widget;
                getPreferenceStore().setValue(style.isBold(), source.getSelection());
                refreshViewer();
            }
        });
        return button;
    }

    private Button createItalicButton(Composite parent, final TokenStyle style) {
        boolean isItalic = getPreferenceStore().getBoolean(style.isItalic());
        Button button = new Button(parent, SWT.CHECK);
        GridData layoutData = new GridData();
        button.setLayoutData(layoutData);
        button.setSelection(isItalic);
        button.setText("Italic");
        button.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                Button source = (Button) e.widget;
                getPreferenceStore().setValue(style.isItalic(), source.getSelection());
                refreshViewer();
            }
        });
        return button;
    }


    private ColorSelector createColorSelector(Composite parent, final TokenStyle style) {
        GridData layoutData = new GridData(SWT.DEFAULT, ROW_HEIGHT);
        String colorText = getPreferenceStore().getString(style.color());
        ColorSelector selector = new ColorSelector(parent);
        selector.setColorValue(StringConverter.asRGB(colorText, new RGB(0, 0, 0)));
        selector.getButton().setLayoutData(layoutData);
        selector.addListener(new IPropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                String value = StringConverter.asString((RGB) event.getNewValue());
                getPreferenceStore().setValue(style.color(), value);
                refreshViewer();
            }
        });
        return selector;
    }

    private TextViewer createTextViewer(Composite appearanceComposite, int numColumns) {
        TextViewer viewer = new TextViewer(appearanceComposite, SWT.BORDER);
        GridData layoutData = new GridData();
        layoutData.horizontalSpan = numColumns;
        layoutData.grabExcessHorizontalSpace = true;
        layoutData.grabExcessVerticalSpace = true;
        layoutData.horizontalAlignment = SWT.FILL;
        layoutData.verticalAlignment = SWT.FILL;
        viewer.getTextWidget().setLayoutData(layoutData);
        viewer.getTextWidget().setFont(JFaceResources.getFont(Activator.FONT_ID));
        viewer.setEditable(false);

        try {
            String text = BundleUtils.readFile(Activator.getDefault().getBundle(), "/coloring_example.json");
            viewer.setDocument(new Document(text));
            IPresentationReconciler reconciler = createPresentationReconciler();
            reconciler.install(viewer);
        } catch (IOException e) {
            JsonLog.logError("Could not load example json file", e);
        }

        return viewer;
    }

    @Override
    public boolean performOk() {
        preferenceStore.writeThrough();
        return super.performOk();
    }

    void refreshViewer() {
        textViewer.refresh();
    }

    private IPresentationReconciler createPresentationReconciler() {
        PresentationReconciler reconciler = new PresentationReconciler();

        JSONLexer lexer = new JSONLexer(null);
        JsonTokenStyler mapping = new JsonTokenStyler(getPreferenceStore());
        AntlrTokenScanner scanner = new AntlrTokenScanner(lexer, mapping);
        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

        return reconciler;
    }

}
