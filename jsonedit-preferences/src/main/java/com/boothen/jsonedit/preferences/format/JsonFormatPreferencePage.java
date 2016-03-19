package com.boothen.jsonedit.preferences.format;

import java.io.IOException;

import org.antlr.v4.runtime.Vocabulary;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextViewer;
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

    private final JsonContentFormatter formatter = new JsonContentFormatter();

    private final SelectionAdapter refreshViewer = new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent e) {
            refreshViewer();
        }
    };

    private TextViewer textViewer;

    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(JsonCorePlugin.getDefault().getPreferenceStore());
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

        textViewer = createTextViewer(container, gridLayout.numColumns);
        refreshViewer();

        return container;
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

    private TextViewer createTextViewer(Composite appearanceComposite, int numColumns) {
        TextViewer viewer = new TextViewer(appearanceComposite, SWT.BORDER);
        GridData layoutData = new GridData();
        layoutData.horizontalSpan = numColumns;
        layoutData.grabExcessHorizontalSpace = true;
        layoutData.grabExcessVerticalSpace = true;
        layoutData.horizontalAlignment = SWT.FILL;
        layoutData.verticalAlignment = SWT.FILL;
        layoutData.widthHint = 150;  // only expand further if anyone else requires it
        layoutData.heightHint = 150; // only expand further if anyone else requires it
        viewer.getTextWidget().setLayoutData(layoutData);
        viewer.getTextWidget().setFont(JFaceResources.getFont(Activator.FONT_ID));
        viewer.setEditable(false);

        try {
            String text = BundleUtils.readFile(Activator.getDefault().getBundle(), "/formatter_example.json");
            viewer.setDocument(new Document(text));
        } catch (IOException e) {
            JsonLog.logError("Could not load example json file", e);
        }

        return viewer;
    }

    private void refreshViewer() {
        Region region = new Region(0, textViewer.getDocument().getLength());
        formatter.format(textViewer.getDocument(), region);
    }
}
