/**
 *
 */
package com.boothen.jsonedit.validation;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

import com.boothen.jsonedit.log.JsonLog;
import com.boothen.jsonedit.validator.JsonTextValidator;

/**
 * Incremental Json Validator is used to validate Json files in a project.
 *
 * @author Matt Garner
 *
 */
public class IncrementalJsonValidator extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "com.boothen.jsonedit.validation.builder";

	public static final String MARKER_ID = "com.boothen.jsonedit.validation.marker";

	/**
	 *
	 */
	public IncrementalJsonValidator() {

	}

	/**
	 * Add the builder to a project
	 *
	 * @param project
	 */
	public static void addBuilderToProject(IProject project) {

		if (!project.isOpen()) {
			return;
		}

		IProjectDescription description;
		try {
			description = project.getDescription();
		} catch (CoreException e) {
			JsonLog.logError("Error get project description: ", e);
			return;
		}

		ICommand[] cmds = description.getBuildSpec();
		for (ICommand cmd : cmds) {
			if(cmd.getBuilderName().equals(BUILDER_ID)) {
				return;
			}
		}

		ICommand newCmd = description.newCommand();
		newCmd.setBuilderName(BUILDER_ID);
		List<ICommand> newCmds = new LinkedList<ICommand>();

		newCmds.addAll(Arrays.asList(cmds));
		newCmds.add(newCmd);
		description.setBuildSpec(newCmds.toArray(new ICommand[newCmds.size()]));

		try {
			project.setDescription(description, null);
		} catch (CoreException e) {
			JsonLog.logError("Error set project description: ", e);
		}
	}

	public static void removeBuilderFromProject(IProject project) {

		if (!project.isOpen()) {
			return;
		}

		IProjectDescription description;
		try {
			description = project.getDescription();
		} catch (CoreException e) {
			JsonLog.logError("Error get project description: ", e);
			return;
		}

		ICommand[] cmds = description.getBuildSpec();
		List<ICommand> cmdsList = new LinkedList<ICommand>();
		cmdsList.addAll(Arrays.asList(cmds));
		ICommand removeCmd = null;
		for (ICommand cmd : cmdsList) {
			if(cmd.getBuilderName().equals(BUILDER_ID)) {
				removeCmd = cmd;
			}
		}

		if (removeCmd == null) {
			return;
		}

		cmdsList.addAll(Arrays.asList(cmds));
		cmdsList.remove(removeCmd);
		description.setBuildSpec(cmdsList.toArray(new ICommand[cmdsList.size()]));

		try {
			project.setDescription(description, null);
		} catch (CoreException e) {
			JsonLog.logError("Error set project description: ", e);
		}

	}

	@Override
	protected IProject[] build(final int kind, Map<String,String> args, IProgressMonitor monitor)
	throws CoreException {

		ResourcesPlugin.getWorkspace().run( new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				validateJson(kind, monitor);
			}
		}, monitor);

		return null;
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		super.clean(monitor);
	}

	private void validateJson(int kind, IProgressMonitor monitor) throws CoreException {

		final Collection<IFile> validateFiles = new LinkedList<IFile>();

		if (kind == FULL_BUILD) {

			deleteValidationMarkers(getProject());
			getProject().accept(new IResourceProxyVisitor() {

				public boolean visit(IResourceProxy proxy) throws CoreException {
					if (!proxy.isDerived()) {

						String filename = proxy.requestFullPath().getFileExtension();
						if (filename != null && filename.equalsIgnoreCase("json")) {
							IFile file = getProject().getFile(proxy.requestResource().getProjectRelativePath());
							validateFiles.add(file);
						}
					}
					return true;
				}
			}, 0);
		} else {

			getDelta(getProject()).accept( new IResourceDeltaVisitor() {

				public boolean visit(IResourceDelta delta) throws CoreException {
					if (!delta.getResource().isDerived()) {
						String filename = delta.getProjectRelativePath().getFileExtension();
						if (filename != null && filename.equalsIgnoreCase("json")) {
							IFile file = getProject().getFile(delta.getProjectRelativePath());
							validateFiles.add(file);
						}
					}
					return true;
				}
			});
		}

		if (validateFiles.size() == 0 ) {
			return;
		}

		monitor.beginTask("Validating JSON files", validateFiles.size());

		if (checkCancel(monitor)) {
			return;
		}

		for (IFile file : validateFiles) {
			deleteValidationMarker(file);
			JsonTextValidator jsonTextValidator = new JsonTextValidator(file, MARKER_ID);
			jsonTextValidator.parse();
			monitor.worked(1);
			if (checkCancel(monitor)) {
				return;
			}
		}

		monitor.done();
	}

	private boolean checkCancel(IProgressMonitor monitor) {

		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}

		if (isInterrupted()) {
			return true;
		}

		return false;
	}

	public boolean deleteValidationMarker(IResource resource) {

		try {
			resource.deleteMarkers(MARKER_ID, false, IResource.DEPTH_INFINITE);
			return true;
		} catch (CoreException e) {
			JsonLog.logError(e);
			return false;
		}
	}

	public static boolean deleteValidationMarkers(IProject project) {

		try {
			project.deleteMarkers(MARKER_ID, false, IResource.DEPTH_INFINITE);
			return true;
		} catch (CoreException e) {
			JsonLog.logError(e);
			return false;
		}

	}
}
