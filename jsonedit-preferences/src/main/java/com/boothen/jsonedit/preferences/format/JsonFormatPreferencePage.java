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
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.core.BundleUtils;
import com.boothen.jsonedit.core.JsonCorePlugin;
import com.boothen.jsonedit.core.JsonLog;
import com.boothen.jsonedit.preferences.Activator;
import com.boothen.jsonedit.preferences.format.JsonFormatter.Affix;

/**
 *
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
    private JsonContentFormatter formatter;
    private WhitespaceCharacterPainter painter;

    @Override
    public void init(IWorkbench workbench) {
        IPreferenceStore preferenceStore = JsonCorePlugin.getDefault().getPreferenceStore();
        setPreferenceStore(preferenceStore);
        formatter = new JsonContentFormatter(preferenceStore);
    }

    @Override
    protected Control createContents(Composite parent) {

        Composite container = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 4;
        container.setLayout(gridLayout);

        int[] tokenTypes = {
                JSONLexer.BEGIN_OBJECT,
                JSONLexer.END_OBJECT,
                JSONLexer.BEGIN_ARRAY,
                JSONLexer.END_ARRAY,
                JSONLexer.COLON,
                JSONLexer.COMMA,
        };

        for (int tokenType : tokenTypes) {
            createAffixConfigGroup(container, JSONLexer.VOCABULARY, tokenType, true);
            createAffixConfigGroup(container, JSONLexer.VOCABULARY, tokenType, false);
        }

        createTextViewer(container, gridLayout.numColumns);
        refreshViewer();

        createWhitespaceCheckbox(container, gridLayout.numColumns);

        return container;
    }

    /**
     * @param container
     */
    private void createWhitespaceCheckbox(Composite container, int numColumns) {
        Button showWhitespaceCheckbox = new Button(container, SWT.CHECK);
        showWhitespaceCheckbox.setText("Show whitespace on this page");
        showWhitespaceCheckbox.addSelectionListener(new SelectionAdapter() {
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
        fontHintData.widthHint = 150; // only expand further if anyone else requires it
        fontHintData.horizontalSpan = numColumns;
        showWhitespaceCheckbox.setLayoutData(fontHintData);
    }

    private Group createAffixConfigGroup(Composite container, Vocabulary vocab, int token, boolean preOrSuf) {
        String symbol = vocab.getSymbolicName(token);
        String literal = vocab.getLiteralName(token);
        String subkey = preOrSuf ? "prefix" : "suffix";
        String affixText = preOrSuf ? "Before" : "After";
        return createConfigGroup(container, symbol + "." + subkey, affixText + " " + literal);
    }

    private Group createConfigGroup(Composite container, String key, String text) {

        Group group = new Group(container, SWT.NONE);
        group.setText(text);
        group.setLayout(new RowLayout(SWT.VERTICAL));

        createRadioButton(group, key, Affix.NONE, "Nothing");
        createRadioButton(group, key, Affix.NEWLINE, "Newline");
        createRadioButton(group, key, Affix.SPACE, "Space");

        group.setLayoutData(new GridData());
        return group;
    }

    private Button createRadioButton(Group group, String key, Affix affix, String text) {
        IPreferenceStore store = getPreferenceStore();
        Button off = new Button(group, SWT.RADIO);
        off.setSelection(affix.name().equals(store.getString(key)));
        off.addSelectionListener(new FormatChangeListener(store, key, affix));
        off.addSelectionListener(refreshViewer);
        off.setText(text);
        return off;
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

    private static WhitespaceCharacterPainter createWhitespacePainter(ITextViewer viewer) {
        int alpha = 80;
        boolean showLeading = true;
        boolean showEnclosed = true;
        boolean showTrailing = true;
        boolean showLineDelimiter = false;
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
}
