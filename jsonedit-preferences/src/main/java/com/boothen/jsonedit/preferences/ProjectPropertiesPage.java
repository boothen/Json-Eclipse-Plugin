/**
 *
 */
package com.boothen.jsonedit.preferences;


import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

import com.boothen.jsonedit.validation.nature.JsonValidationNature;

/**
 * Project Properties page used to apply the JsonValidationNature to a
 * project.
 *
 * @author Matt Garner
 *
 */
public class ProjectPropertiesPage extends PropertyPage implements
		IWorkbenchPropertyPage {

	private Button checkbox;

	/**
	 *
	 */
	public ProjectPropertiesPage() {
	}

	@Override
	protected Control createContents(Composite parent) {

		Composite panel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		panel.setLayout(layout);

		checkbox = new Button(panel,SWT.CHECK);
		checkbox.setLayoutData(new GridData());
		checkbox.setText("Enable JSON validation in project");

		checkbox.setSelection(getValidationEnabled());
		return panel;
	}

	/**
	 * Determine if Json validation is already enabled for a project.
	 *
	 * @return
	 */
	private boolean getValidationEnabled() {

		IProject project =  (IProject) this.getElement().getAdapter(IProject.class);
		return JsonValidationNature.hasNature(project);
	}

	/**
	 * Perform the action when the user clicks ok.
	 *
	 */
	@Override
	public boolean performOk() {

		IProject project =  (IProject) this.getElement().getAdapter(IProject.class);
		if (JsonValidationNature.hasNature(project) != checkbox.getSelection()) {
			if (checkbox.getSelection()) {
				JsonValidationNature.addNature(project);
			} else {
				JsonValidationNature.removeNature(project);
			}
		}
		return true;
	}
}
