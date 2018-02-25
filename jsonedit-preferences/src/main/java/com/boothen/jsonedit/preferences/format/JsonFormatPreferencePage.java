package com.boothen.jsonedit.preferences.format;

import java.io.IOException;

import org.antlr.v4.runtime.Vocabulary;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.WhitespaceCharacterPainter;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.core.BundleUtils;
import com.boothen.jsonedit.core.JsonCorePlugin;
import com.boothen.jsonedit.core.JsonLog;
import com.boothen.jsonedit.core.preferences.JsonPreferences;
import com.boothen.jsonedit.preferences.Activator;
import com.boothen.jsonedit.preferences.OverlayPreferenceStore;
import com.boothen.jsonedit.preferences.format.JsonFormatter.Affix;

/**
 * A preference page for formatting details. Uses the preference store from {@link JsonCorePlugin} as a basis.
 */
public class JsonFormatPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private static final String SHOW_WHITESPACE_PREF = "showWhitespaceInPrefPage";


    private final SelectionAdapter refreshViewer = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent e) {
            refreshViewer();
        }
    };

    private TextViewer textViewer;
    private ListViewer listViewer;
    private JsonContentFormatter formatter;
    private WhitespaceCharacterPainter painter;
    private OverlayPreferenceStore preferenceStore;

    /**
     * Default constructor
     */
    public JsonFormatPreferencePage() {
        // TODO: implement performDefaults: update existing widgets with changed selection
        noDefaultAndApplyButton();
    }

    @Override
    public void init(IWorkbench workbench) {
        IPreferenceStore orgStore = JsonCorePlugin.getDefault().getPreferenceStore();
        preferenceStore = new OverlayPreferenceStore(orgStore);
        setPreferenceStore(preferenceStore);
        formatter = new JsonContentFormatter(preferenceStore);
    }

    @Override
    protected Control createContents(Composite parent) {

        Composite container = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 3;
        container.setLayout(gridLayout);

        createAffixConfigGroup(container, JSONLexer.VOCABULARY);

        createNewlineCheckbox(container, gridLayout.numColumns);
        createTextViewer(container, gridLayout.numColumns);
        refreshViewer();

        createWhitespaceCheckbox(container, gridLayout.numColumns);
        createGeneralTextEditorHint(container, gridLayout.numColumns);

        return container;
    }

    /**
     * @param container
     * @param vocabulary
     */
    private void createAffixConfigGroup(Composite container, final Vocabulary vocabulary) {

        Object[] tokenTypes = {
                JSONLexer.BEGIN_OBJECT,
                JSONLexer.END_OBJECT,
                JSONLexer.BEGIN_ARRAY,
                JSONLexer.END_ARRAY,
                JSONLexer.COLON,
                JSONLexer.COMMA,
        };

        listViewer = new ListViewer(container, SWT.BORDER | SWT.V_SCROLL);
        listViewer.setContentProvider(ArrayContentProvider.getInstance());
        listViewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                int type = (Integer)element;
                return vocabulary.getLiteralName(type) + "  " + vocabulary.getSymbolicName(type);
            }
        });
        listViewer.setInput(tokenTypes);

        final Group prefixGroup = createConfigGroup(container, vocabulary, "prefix", "Before");
        final Group suffixGroup = createConfigGroup(container, vocabulary, "suffix", "After");

        listViewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                // getStructuredSelection() is not yet available in indigo (3.7)
                IStructuredSelection selection = (IStructuredSelection) listViewer.getSelection();
                Integer token = (Integer) selection.getFirstElement();
                String symbol = vocabulary.getSymbolicName(token);
                String prefixValue = preferenceStore.getString(symbol + ".prefix");
                String suffixValue = preferenceStore.getString(symbol + ".suffix");
                if (prefixValue.isEmpty()) {
                    prefixValue = Affix.NONE.name();
                }
                if (suffixValue.isEmpty()) {
                    suffixValue = Affix.NONE.name();
                }
                for (int i = 0; i < 3; i++) {
                    Button prefixButton = (Button) prefixGroup.getChildren()[i];
                    String prefixName = prefixButton.getData().toString();
                    prefixButton.setSelection(prefixName.equals(prefixValue));

                    Button suffixButton = (Button) suffixGroup.getChildren()[i];
                    String suffixName = suffixButton.getData().toString();
                    suffixButton.setSelection(suffixName.equals(suffixValue));
                }
            }
        });

        listViewer.setSelection(new StructuredSelection(tokenTypes[0]));   // yeah, why not make it complicated?
        listViewer.getList().setLayoutData(new GridData());
    }

    private Group createConfigGroup(Composite container, Vocabulary vocab, String key, String text) {

        Group group = new Group(container, SWT.NONE);
        group.setText(text);
        group.setLayout(new RowLayout(SWT.VERTICAL));

        // must be in the same order as the Affix enum value
        createRadioButton(group, Affix.NONE, vocab, key, "Nothing");
        createRadioButton(group, Affix.SPACE, vocab, key, "Space");
        createRadioButton(group, Affix.NEWLINE, vocab, key, "Newline");

        GridData layoutData = new GridData();
        layoutData.verticalAlignment = SWT.FILL;
        layoutData.horizontalAlignment = SWT.FILL;
        layoutData.grabExcessHorizontalSpace = true;
        group.setLayoutData(layoutData);
        return group;
    }

    private Button createRadioButton(Group group, Affix affix, Vocabulary vocab, String key, String text) {
        IPreferenceStore store = getPreferenceStore();
        Button off = new Button(group, SWT.RADIO);
        off.addSelectionListener(new FormatChangeListener(store, vocab, key));
        off.addSelectionListener(refreshViewer);
        off.setData(affix);
        off.setText(text);
        return off;
    }

    @Override
    public boolean performOk() {
        preferenceStore.writeThrough();
        return super.performOk();
    }

    private void createWhitespaceCheckbox(Composite container, int numColumns) {
        Button checkbox = new Button(container, SWT.CHECK);
        checkbox.setText("Show whitespace on this page");
        checkbox.setSelection(getPreferenceStore().getBoolean(SHOW_WHITESPACE_PREF));
        checkbox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Button button = (Button) e.widget;
                boolean enabled = button.getSelection();
                getPreferenceStore().setValue(SHOW_WHITESPACE_PREF, enabled);
                if (enabled) {
                    textViewer.addPainter(painter);
                } else {
                    textViewer.removePainter(painter);
                }
            }
        });

        GridData fontHintData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        fontHintData.horizontalSpan = numColumns;
        checkbox.setLayoutData(fontHintData);
    }

    private void createNewlineCheckbox(Composite container, int numColumns) {
        Button checkbox = new Button(container, SWT.CHECK);
        checkbox.setText("Add newline at end of file");
        checkbox.setSelection(getPreferenceStore().getBoolean(JsonPreferences.EDITOR_TRAILING_NEWLINE));
        checkbox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Button button = (Button) e.widget;
                boolean enabled = button.getSelection();
                getPreferenceStore().setValue(JsonPreferences.EDITOR_TRAILING_NEWLINE, enabled);
            }
        });
        checkbox.addSelectionListener(refreshViewer);

        GridData fontHintData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        fontHintData.horizontalSpan = numColumns;
        checkbox.setLayoutData(fontHintData);
    }

    private void createTextViewer(Composite appearanceComposite, int numColumns) {
        textViewer = new TextViewer(appearanceComposite, SWT.BORDER);
        GridData layoutData = new GridData();
        layoutData.horizontalSpan = numColumns;
        layoutData.grabExcessHorizontalSpace = true;
        layoutData.grabExcessVerticalSpace = true;
        layoutData.horizontalAlignment = SWT.FILL;
        layoutData.verticalAlignment = SWT.FILL;
        layoutData.widthHint = 150;  // only expand further if anyone else requires it
        layoutData.heightHint = 150; // only expand further if anyone else requires it
        textViewer.getTextWidget().setLayoutData(layoutData);
        textViewer.getTextWidget().setFont(JFaceResources.getFont(Activator.FONT_ID));
        textViewer.setEditable(false);

        try {
            String text = BundleUtils.readFile(Activator.getDefault().getBundle(), "/formatter_example.json");
            textViewer.setDocument(new Document(text));
        } catch (IOException e) {
            JsonLog.logError("Could not load example json file", e);
        }

        painter = createWhitespacePainter(textViewer);
        if (getPreferenceStore().getBoolean(SHOW_WHITESPACE_PREF)) {
            textViewer.addPainter(painter);
        }
    }

    private void createGeneralTextEditorHint(final Composite container, int numColumns) {
        Link editorHint = new Link(container, SWT.NONE);
        editorHint.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    Shell shell = container.getShell();
                    if ("org.eclipse.ui.preferencePages.GeneralTextEditor".equals(e.text)) { //$NON-NLS-1$
                        PreferencesUtil.createPreferenceDialogOn(shell, e.text, null, null);
                    }
                }
        });
        editorHint.setText("Go to <a href=\"org.eclipse.ui.preferencePages.GeneralTextEditor\">"
                + "Text Editors</a> to configure general text settings such as tabs/spaces");

        GridData editorHintData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        editorHintData.widthHint = 150; // only expand further if anyone else requires it
        editorHintData.horizontalSpan = numColumns;
        editorHint.setLayoutData(editorHintData);
    }

    private static WhitespaceCharacterPainter createWhitespacePainter(ITextViewer viewer) {
        int alpha = 80;
        boolean showLeading = true;
        boolean showEnclosed = true;
        boolean showTrailing = true;
        boolean showLineDelimiter = true;
        boolean showLeadingSpaces = showLeading;
        boolean showEnclosedSpaces = showEnclosed;
        boolean showTrailingSpaces = showTrailing;
        boolean showLeadingIdeographicSpaces = showLeading;
        boolean showEnclosedIdeographicSpaces = showEnclosed;
        boolean showTrailingIdeographicSpace = showTrailing;
        boolean showLeadingTabs = showLeading;
        boolean showEnclosedTabs = showEnclosed;
        boolean showTrailingTabs = showTrailing;
        boolean showCarriageReturn = showLineDelimiter;
        boolean showLineFeed = showLineDelimiter;
        return new WhitespaceCharacterPainter(viewer,
                showLeadingSpaces, showEnclosedSpaces, showTrailingSpaces,
                showLeadingIdeographicSpaces, showEnclosedIdeographicSpaces,
                showTrailingIdeographicSpace, showLeadingTabs, showEnclosedTabs,
                showTrailingTabs, showCarriageReturn, showLineFeed, alpha);
    }

    private void refreshViewer() {
        Region region = new Region(0, textViewer.getDocument().getLength());
        formatter.format(textViewer.getDocument(), region);
    }

    private class FormatChangeListener extends SelectionAdapter {

        private IPreferenceStore prefStore;
        private Vocabulary vocab;
        private String key;

        public FormatChangeListener(IPreferenceStore prefStore, Vocabulary vocab, String key) {
            this.prefStore = prefStore;
            this.vocab = vocab;
            this.key = key;
        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            Button button = (Button)e.widget;

            // getStructuredSelection() is not yet available in indigo (3.7)
            IStructuredSelection selection = (IStructuredSelection) listViewer.getSelection();
            Integer token = (Integer) selection.getFirstElement();

            if (button.getSelection()) {
                String affixName = button.getData().toString();
                String symbol = vocab.getSymbolicName(token);
                String storeKey = symbol + "." + key;
                prefStore.setValue(storeKey, affixName);
            }
        }

    }

}
