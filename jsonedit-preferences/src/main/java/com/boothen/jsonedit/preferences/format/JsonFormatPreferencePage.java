package com.boothen.jsonedit.preferences.format;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.text.TextViewer;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.boothen.jsonedit.core.JsonCorePlugin;

/**
 * @author denis.mirochnik
 */
public class JsonFormatPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, SelectionListener
{
    private Button mWrapObject;
    private Button mWrapArray;
    private Button mNewLineObject;
    private Button mNewLineArray;
    private Button mAfterComma;
    private Button mAfterColon;
    private Button mBeforeColon;
    private Button mAfterObjectOpen;
    private Button mBeforeObjectClose;
    private Button mAfterArrayOpen;
    private Button mBeforeArrayClose;
    private Button mBlankLineAfterComplex;
    private Button mAutoFormat;
    private TextViewer mTextViewer;

    @Override
    protected Control createContents(Composite parent)
    {
        final Composite appearanceComposite = new Composite(parent, SWT.NONE);
        appearanceComposite.setLayout(new FormLayout());

        mAutoFormat = new Button(appearanceComposite, SWT.CHECK);
        mAutoFormat.setData(JsonPreferencesInitializer.PREF_AUTO_FORMAT_ON_SAVE);
        final FormData fd_btnAutoFormatOn = new FormData();
        fd_btnAutoFormatOn.top = new FormAttachment(0, 0);
        fd_btnAutoFormatOn.left = new FormAttachment(0, 0);
        mAutoFormat.setLayoutData(fd_btnAutoFormatOn);
        mAutoFormat.setText("Auto format on save");
        mAutoFormat.addSelectionListener(this);

        final Group grpWrap = new Group(appearanceComposite, SWT.NONE);
        grpWrap.setText("Wrap");
        grpWrap.setLayout(new FormLayout());
        final FormData fd_grpWrap = new FormData();
        fd_grpWrap.top = new FormAttachment(mAutoFormat, 10);
        fd_grpWrap.left = new FormAttachment(0, 0);
        grpWrap.setLayoutData(fd_grpWrap);

        mWrapObject = new Button(grpWrap, SWT.CHECK);
        mWrapObject.setData(JsonPreferencesInitializer.PREF_WRAP_OBJECT);
        final FormData fd_btnWrapObject = new FormData();
        fd_btnWrapObject.top = new FormAttachment(0, 5);
        fd_btnWrapObject.left = new FormAttachment(0, 5);
        fd_btnWrapObject.right = new FormAttachment(100, -5);
        mWrapObject.setLayoutData(fd_btnWrapObject);
        mWrapObject.setText("Object");
        mWrapObject.addSelectionListener(this);

        mWrapArray = new Button(grpWrap, SWT.CHECK);
        mWrapArray.setData(JsonPreferencesInitializer.PREF_WRAP_ARRAY);
        final FormData fd_btnWrapArray = new FormData();
        fd_btnWrapArray.top = new FormAttachment(mWrapObject, 5);
        fd_btnWrapArray.left = new FormAttachment(0, 5);
        fd_btnWrapArray.bottom = new FormAttachment(100, -5);
        mWrapArray.setLayoutData(fd_btnWrapArray);
        mWrapArray.setText("Array");
        mWrapArray.addSelectionListener(this);

        final Group grpBracketOnNew = new Group(appearanceComposite, SWT.NONE);
        grpBracketOnNew.setText("Bracket on new line");
        grpBracketOnNew.setLayout(new FormLayout());
        final FormData fd_grpBracketOnNew = new FormData();
        fd_grpBracketOnNew.top = new FormAttachment(grpWrap, 0, SWT.TOP);
        fd_grpBracketOnNew.left = new FormAttachment(grpWrap, 10);
        grpBracketOnNew.setLayoutData(fd_grpBracketOnNew);

        mNewLineObject = new Button(grpBracketOnNew, SWT.CHECK);
        mNewLineObject.setData(JsonPreferencesInitializer.PREF_OBJECT_BRACKETS_NEW_LINE);
        final FormData fd_btnNewLineObjectData = new FormData();
        fd_btnNewLineObjectData.top = new FormAttachment(0, 5);
        fd_btnNewLineObjectData.left = new FormAttachment(0, 5);
        fd_btnNewLineObjectData.right = new FormAttachment(100, -5);
        mNewLineObject.setLayoutData(fd_btnNewLineObjectData);
        mNewLineObject.setText("Object");
        mNewLineObject.addSelectionListener(this);

        mNewLineArray = new Button(grpBracketOnNew, SWT.CHECK);
        mNewLineArray.setData(JsonPreferencesInitializer.PREF_ARRAY_BRACKETS_NEW_LINE);
        final FormData fd_btnNewLineArray = new FormData();
        fd_btnNewLineArray.top = new FormAttachment(mNewLineObject, 5);
        fd_btnNewLineArray.bottom = new FormAttachment(100, -5);
        fd_btnNewLineArray.left = new FormAttachment(0, 5);
        mNewLineArray.setLayoutData(fd_btnNewLineArray);
        mNewLineArray.setText("Array");
        mNewLineArray.addSelectionListener(this);

        final Group grpSpaces = new Group(appearanceComposite, SWT.NONE);
        grpSpaces.setText("Spaces");
        grpSpaces.setLayout(new FormLayout());
        final FormData fd_grpSpaces = new FormData();
        fd_grpSpaces.top = new FormAttachment(grpWrap, 10);
        fd_grpSpaces.left = new FormAttachment(0, 0);
        grpSpaces.setLayoutData(fd_grpSpaces);

        mAfterComma = new Button(grpSpaces, SWT.CHECK);
        mAfterComma.setData(JsonPreferencesInitializer.PREF_SPACE_AFTER_COMMA);
        final FormData fd_afterComma = new FormData();
        fd_afterComma.left = new FormAttachment(0, 5);
        fd_afterComma.top = new FormAttachment(0, 5);
        mAfterComma.setLayoutData(fd_afterComma);
        mAfterComma.setText("After comma");
        mAfterComma.addSelectionListener(this);

        mAfterColon = new Button(grpSpaces, SWT.CHECK);
        mAfterColon.setData(JsonPreferencesInitializer.PREF_SPACE_AFTER_COLON);
        final FormData fd_afterColon = new FormData();
        fd_afterColon.top = new FormAttachment(mAfterComma, 5);
        fd_afterColon.left = new FormAttachment(0, 5);
        mAfterColon.setLayoutData(fd_afterColon);
        mAfterColon.setText("After colon");
        mAfterColon.addSelectionListener(this);

        mBeforeColon = new Button(grpSpaces, SWT.CHECK);
        mBeforeColon.setData(JsonPreferencesInitializer.PREF_SPACE_BEFORE_COLON);
        final FormData fd_beforeColon = new FormData();
        fd_beforeColon.top = new FormAttachment(mAfterColon, 5);
        fd_beforeColon.left = new FormAttachment(0, 5);
        mBeforeColon.setLayoutData(fd_beforeColon);
        mBeforeColon.setText("Before colon");
        mBeforeColon.addSelectionListener(this);

        mAfterObjectOpen = new Button(grpSpaces, SWT.CHECK);
        mAfterObjectOpen.setData(JsonPreferencesInitializer.PREF_SPACE_AFTER_OBJECT_OPEN);
        final FormData fd_btnObjectOpen = new FormData();
        fd_btnObjectOpen.left = new FormAttachment(mAfterComma, 10);
        fd_btnObjectOpen.right = new FormAttachment(100, -5);
        fd_btnObjectOpen.top = new FormAttachment(mAfterComma, 0, SWT.TOP);
        mAfterObjectOpen.setLayoutData(fd_btnObjectOpen);
        mAfterObjectOpen.setText("After object opening bracket");
        mAfterObjectOpen.addSelectionListener(this);

        mBeforeObjectClose = new Button(grpSpaces, SWT.CHECK);
        mBeforeObjectClose.setData(JsonPreferencesInitializer.PREF_SPACE_BEFORE_OBJECT_CLOSE);
        final FormData fd_btnObjectClose = new FormData();
        fd_btnObjectClose.left = new FormAttachment(mAfterObjectOpen, 0, SWT.LEFT);
        fd_btnObjectClose.right = new FormAttachment(100, -5);
        fd_btnObjectClose.top = new FormAttachment(mAfterObjectOpen, 5);
        mBeforeObjectClose.setLayoutData(fd_btnObjectClose);
        mBeforeObjectClose.setText("Before object closing bracket");
        mBeforeObjectClose.addSelectionListener(this);

        mAfterArrayOpen = new Button(grpSpaces, SWT.CHECK);
        mAfterArrayOpen.setData(JsonPreferencesInitializer.PREF_SPACE_AFTER_ARRAY_OPEN);
        final FormData fd_btnArrayOpen = new FormData();
        fd_btnArrayOpen.left = new FormAttachment(mAfterObjectOpen, 0, SWT.LEFT);
        fd_btnArrayOpen.right = new FormAttachment(100, -5);
        fd_btnArrayOpen.top = new FormAttachment(mBeforeObjectClose, 5);
        mAfterArrayOpen.setLayoutData(fd_btnArrayOpen);
        mAfterArrayOpen.setText("After array opening bracket");
        mAfterArrayOpen.addSelectionListener(this);

        mBeforeArrayClose = new Button(grpSpaces, SWT.CHECK);
        mBeforeArrayClose.setData(JsonPreferencesInitializer.PREF_SPACE_BEFORE_ARRAY_CLOSE);
        final FormData fd_btnArrayClose = new FormData();
        fd_btnArrayClose.left = new FormAttachment(mAfterObjectOpen, 0, SWT.LEFT);
        fd_btnArrayClose.right = new FormAttachment(100, -5);
        fd_btnArrayClose.bottom = new FormAttachment(100, -5);
        fd_btnArrayClose.top = new FormAttachment(mAfterArrayOpen, 5);
        mBeforeArrayClose.setLayoutData(fd_btnArrayClose);
        mBeforeArrayClose.setText("Before array closing bracket");
        mBeforeArrayClose.addSelectionListener(this);

        mBlankLineAfterComplex = new Button(appearanceComposite, SWT.CHECK);
        mBlankLineAfterComplex.setData(JsonPreferencesInitializer.PREF_BLANK_LINE_AFTER_COMPLEX);
        final FormData fd_blankLineAfterComplex = new FormData();
        fd_blankLineAfterComplex.top = new FormAttachment(grpSpaces, 10);
        fd_blankLineAfterComplex.left = new FormAttachment(0, 0);
        mBlankLineAfterComplex.setLayoutData(fd_blankLineAfterComplex);
        mBlankLineAfterComplex.setText("Blank line after complex entities");
        mBlankLineAfterComplex.addSelectionListener(this);

        mTextViewer = new TextViewer(appearanceComposite, SWT.BORDER);
        mTextViewer.setEditable(false);
        final StyledText styledText = mTextViewer.getTextWidget();
        final FormData fd_styledText = new FormData();
        fd_styledText.top = new FormAttachment(mBlankLineAfterComplex, 10);
        fd_styledText.left = new FormAttachment(0, 0);
        fd_styledText.right = new FormAttachment(100, -0);
        fd_styledText.bottom = new FormAttachment(100, -0);
        styledText.setLayoutData(fd_styledText);

//        update(mPreferenceStore);

//        mFormatter = new TextFormatter(mTextViewer, mPreferenceStore);
//        mFormatter.update();

        return appearanceComposite;
    }

    public void update(IPreferenceStore store)
    {
        mWrapObject.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_WRAP_OBJECT));
        mWrapArray.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_WRAP_ARRAY));
        mNewLineObject.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_OBJECT_BRACKETS_NEW_LINE));
        mNewLineArray.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_ARRAY_BRACKETS_NEW_LINE));
        mAfterComma.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_SPACE_AFTER_COMMA));
        mAfterColon.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_SPACE_AFTER_COLON));
        mBeforeColon.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_SPACE_BEFORE_COLON));
        mAfterObjectOpen.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_SPACE_AFTER_OBJECT_OPEN));
        mBeforeObjectClose.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_SPACE_BEFORE_OBJECT_CLOSE));
        mAfterArrayOpen.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_SPACE_AFTER_ARRAY_OPEN));
        mBeforeArrayClose.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_SPACE_BEFORE_ARRAY_CLOSE));
        mBlankLineAfterComplex.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_BLANK_LINE_AFTER_COMPLEX));
        mAutoFormat.setSelection(store.getBoolean(JsonPreferencesInitializer.PREF_AUTO_FORMAT_ON_SAVE));
    }

    @Override
    public void init(IWorkbench workbench)
    {
        setPreferenceStore(JsonCorePlugin.getDefault().getPreferenceStore());
    }

    @Override
    public void widgetSelected(SelectionEvent e)
    {
        final String key = (String) e.widget.getData();

//        mPreferenceStore.setValue(key, ((Button) e.widget).getSelection());
//
//        mFormatter.update();
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e)
    {
    }
}
