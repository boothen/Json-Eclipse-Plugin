package com.boothen.jsonedit.preferences.colors;

import java.io.IOException;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.boothen.jsonedit.core.BundleUtils;
import com.boothen.jsonedit.preferences.Activator;
import com.boothen.jsonedit.preferences.NodeType;
import com.boothen.jsonedit.text.JsonStringScanner;

/**
 * @author denis.mirochnik
 */
public class JsonSyntaxColorsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, ISelectionChangedListener, IPropertyChangeListener, SelectionListener
{
    private Button mEnabledButton;
    private ColorSelector mSelector;
    private Button mBoldButton;
    private Button mItalicButton;
    private Button mUnderButton;
    private Button mStrikeButton;

    @Override
    protected Control createContents(Composite parent)
    {
        final Composite appearanceComposite = new Composite(parent, SWT.NONE);
        appearanceComposite.setLayout(new FormLayout());

        final ListViewer listViewer = new ListViewer(appearanceComposite, SWT.BORDER | SWT.V_SCROLL);
        final List list = listViewer.getList();

        final FormData fd_list = new FormData();
        fd_list.top = new FormAttachment(0, 0);
        fd_list.left = new FormAttachment(0, 0);

        list.setLayoutData(fd_list);

        mEnabledButton = new Button(appearanceComposite, SWT.CHECK);
        final FormData fd_btnCheckButton = new FormData();
        fd_btnCheckButton.top = new FormAttachment(0, 0);
        fd_btnCheckButton.left = new FormAttachment(list, 10);
        mEnabledButton.setLayoutData(fd_btnCheckButton);
        mEnabledButton.setText("Enabled");
        mEnabledButton.addSelectionListener(this);

        mSelector = new ColorSelector(appearanceComposite);
        final Button colorButton = mSelector.getButton();
        final FormData fd_btnColorButton = new FormData();
        fd_btnColorButton.top = new FormAttachment(mEnabledButton, 10);
        fd_btnColorButton.left = new FormAttachment(list, 10);
        colorButton.setLayoutData(fd_btnColorButton);
        mSelector.addListener(this);

        final TextViewer textViewer = new TextViewer(appearanceComposite, SWT.BORDER);
        final StyledText styledText = textViewer.getTextWidget();
        final FormData fd_styledText = new FormData();
        fd_styledText.bottom = new FormAttachment(100, -0);
        fd_styledText.right = new FormAttachment(100, -0);
        fd_styledText.top = new FormAttachment(list, 10);
        fd_styledText.left = new FormAttachment(0, 0);
        styledText.setLayoutData(fd_styledText);

        textViewer.setEditable(false);

        try {
            String text = BundleUtils.readFile(Activator.getDefault().getBundle(), "/coloring_example.json");
            textViewer.setDocument(new Document(text));
            IPresentationReconciler reconciler = createPresentationReconciler();
            reconciler.install(textViewer);
        } catch (IOException e) {
            // TODO: handle properly
        }

        mBoldButton = new Button(appearanceComposite, SWT.CHECK);
        final FormData fd_btnBold = new FormData();
        fd_btnBold.top = new FormAttachment(colorButton, 10);
        fd_btnBold.left = new FormAttachment(list, 10);
        mBoldButton.setLayoutData(fd_btnBold);
        mBoldButton.setText("Bold");
        mBoldButton.addSelectionListener(this);

        mItalicButton = new Button(appearanceComposite, SWT.CHECK);
        final FormData fd_btnCheckButton0 = new FormData();
        fd_btnCheckButton0.top = new FormAttachment(mBoldButton, 10);
        fd_btnCheckButton0.left = new FormAttachment(list, 10);
        mItalicButton.setLayoutData(fd_btnCheckButton0);
        mItalicButton.setText("Italic");
        mItalicButton.addSelectionListener(this);

        mUnderButton = new Button(appearanceComposite, SWT.CHECK);
        final FormData fd_btnCheckButton_1 = new FormData();
        fd_btnCheckButton_1.top = new FormAttachment(colorButton, 10);
        fd_btnCheckButton_1.left = new FormAttachment(mItalicButton, 10);
        mUnderButton.setLayoutData(fd_btnCheckButton_1);
        mUnderButton.setText("Underline");
        mUnderButton.addSelectionListener(this);

        mStrikeButton = new Button(appearanceComposite, SWT.CHECK);
        final FormData fd_btnCheckButton_2 = new FormData();
        fd_btnCheckButton_2.top = new FormAttachment(mUnderButton, 10);
        fd_btnCheckButton_2.left = new FormAttachment(mItalicButton, 10);
        mStrikeButton.setLayoutData(fd_btnCheckButton_2);
        mStrikeButton.setText("Strikethrough");
        mStrikeButton.addSelectionListener(this);

        listViewer.setContentProvider(new ColorsContentProvider());
        listViewer.setLabelProvider(new ColorsLabelProvider());
//        listViewer.addSelectionChangedListener(this);
        listViewer.setInput(NodeType.values());
//        listViewer.setSelection(new StructuredSelection(NodeType.));

        return appearanceComposite;
    }

    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        updateSelection();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        if (ColorSelector.PROP_COLORCHANGE.equals(event.getProperty()))
        {
//            mPreferenceStore.setValue(mSelection.getKey(), StringConverter.asString((RGB) event.getNewValue()));
//            mHighlighter.update();
        }
    }

    private void updateSelection()
    {
//        final int style = mSelection.getOwnStyle(mPreferenceStore);
//
//        mSelector.setColorValue(mSelection.getOwnColor(mPreferenceStore));
//        mBoldButton.setSelection(JsonPreferences.isBold(style));
//        mItalicButton.setSelection(JsonPreferences.isItalic(style));
//        mUnderButton.setSelection(JsonPreferences.isUnderline(style));
//        mStrikeButton.setSelection(JsonPreferences.isStrike(style));
//
//        if (mSelection == TokenType.DEFAULT)
//        {
//            mEnabledButton.setSelection(true);
//            mEnabledButton.setEnabled(false);
//        }
//        else
//        {
//            mEnabledButton.setEnabled(true);
//            mEnabledButton.setSelection(mSelection.isEnabled(mPreferenceStore));
//        }
//
//        mHighlighter.update();
    }

    @Override
    public boolean performOk()
    {
//        final IPreferenceStore store = JsonPlugin.getDefault().getPreferenceStore();
//        final TokenType[] types = TokenType.values();
//
//        for (final TokenType type : types)
//        {
//            store.setValue(type.getEnabledKey(), type.isEnabled(mPreferenceStore));
//            store.setValue(type.getKey(), StringConverter.asString(type.getOwnColor(mPreferenceStore)));
//            store.setValue(type.getStyleKey(), type.getOwnStyle(mPreferenceStore));
//        }

        return true;
    }

    @Override
    protected void performDefaults()
    {
        super.performDefaults();

//        final IPreferenceStore store = JsonPlugin.getDefault().getPreferenceStore();
//        final TokenType[] types = TokenType.values();
//
//        for (final TokenType type : types)
//        {
//            mPreferenceStore.setValue(type.getEnabledKey(), store.getDefaultBoolean(type.getEnabledKey()));
//            mPreferenceStore.setValue(type.getKey(), store.getDefaultString(type.getKey()));
//            mPreferenceStore.setValue(type.getStyleKey(), store.getDefaultInt(type.getStyleKey()));
//        }
//
//        updateSelection();
//        mHighlighter.update();
    }

    @Override
    public void init(IWorkbench workbench)
    {
//        setPreferenceStore(JsonPlugin.getDefault().getPreferenceStore());
    }

    private int collectStyle()
    {
        final boolean bold = mBoldButton.getSelection();
        final boolean italic = mItalicButton.getSelection();
        final boolean under = mUnderButton.getSelection();
        final boolean strike = mStrikeButton.getSelection();

        return FontStyles.merge(bold, italic, under, strike);
    }

    @Override
    public void widgetSelected(SelectionEvent e)
    {
//        if (e.widget == mEnabledButton)
//        {
//            mPreferenceStore.setValue(mSelection.getEnabledKey(), mEnabledButton.getSelection());
//        }
//        else
//        {
//            mPreferenceStore.setValue(mSelection.getStyleKey(), collectStyle());
//        }
//
//        mHighlighter.update();
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e)
    {
//        if (e.widget == mEnabledButton)
//        {
//            mPreferenceStore.setValue(mSelection.getEnabledKey(), mEnabledButton.getSelection());
//        }
//        else
//        {
//            mPreferenceStore.setValue(mSelection.getStyleKey(), collectStyle());
//        }
//
//        mHighlighter.update();
    }

    private IPresentationReconciler createPresentationReconciler() {
        PresentationReconciler reconciler = new PresentationReconciler();

        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(new JsonStringScanner());
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

        return reconciler;
    }

}
