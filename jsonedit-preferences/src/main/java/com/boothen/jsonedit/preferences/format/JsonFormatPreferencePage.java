package com.boothen.jsonedit.preferences.format;

import java.io.IOException;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.boothen.jsonedit.core.BundleUtils;
import com.boothen.jsonedit.core.JsonCorePlugin;
import com.boothen.jsonedit.core.JsonLog;
import com.boothen.jsonedit.preferences.Activator;

/**
 *
 */
public class JsonFormatPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

    private final JsonContentFormatter formatter = new JsonContentFormatter();

    @Override
    protected Control createContents(Composite parent) {
        String[] text = { "Before {", "After {", "Before }", "After }" };

        Composite container = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = text.length;
        container.setLayout(gridLayout);

        for (int i = 0; i < text.length; i++) {
            Group group = new Group(container, SWT.NONE);
            group.setText(text[i]);
            group.setLayout(new RowLayout(SWT.VERTICAL));
            Button off = new Button(group, SWT.RADIO);
            off.setText("Nothing");
            Button newline = new Button(group, SWT.RADIO);
            newline.setText("Newline");
            Button space = new Button(group, SWT.RADIO);
            space.setText("Space");
            group.setLayoutData(new GridData());
        }

        createTextViewer(container, gridLayout.numColumns);

        return container;
    }

    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(JsonCorePlugin.getDefault().getPreferenceStore());
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
            String text = BundleUtils.readFile(Activator.getDefault().getBundle(), "/formatter_example.json");
            viewer.setDocument(new Document(text));

            Region region = new Region(0, viewer.getDocument().getLength());
            formatter.format(viewer.getDocument(), region);

        } catch (IOException e) {
            JsonLog.logError("Could not load example json file", e);
        }

        return viewer;
    }
}
