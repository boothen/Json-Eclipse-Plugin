package com.boothen.jsonedit.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

/**
 * A file wizard for JSON files.
 */
public class JsonNewFileWizard extends BasicNewResourceWizard
{
    private WizardNewFileCreationPage mainPage;

    @Override
    public void addPages()
    {
        super.addPages();
        mainPage = new WizardNewFileCreationPage("newFilePage1", getSelection());
        mainPage.setTitle("Create JSON File");
        mainPage.setFileName("file.json");
        addPage(mainPage);
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection currentSelection)
    {
        super.init(workbench, currentSelection);
        setWindowTitle("New JSON File");
        setNeedsProgressMonitor(false);
    }

    @Override
    public boolean performFinish()
    {
        IFile file = mainPage.createNewFile();
        if (file == null) {
            return false;
        }

        selectAndReveal(file);

        final IWorkbenchWindow dw = getWorkbench().getActiveWorkbenchWindow();

        try {
            if (dw != null) {
                IWorkbenchPage page = dw.getActivePage();

                if (page != null) {
                    IDE.openEditor(page, file, true);
                }
            }
        }
        catch (PartInitException e) {
            StatusManager.getManager().handle(e.getStatus());
        }

        return true;
    }
}
