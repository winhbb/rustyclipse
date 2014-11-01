package org.rustyclipse.ui.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.internal.Workbench;
import org.osgi.service.prefs.BackingStoreException;
import org.rustyclipse.RustProjectNature;

@SuppressWarnings("restriction")
public class ProjectUtils {

	public static final IEclipsePreferences getProjectPreferences(IProject project) {
		ProjectScope scope = new ProjectScope(project);
		IEclipsePreferences node = scope.getNode(RustProjectNature.NATURE_ID);
		return node;
	}
	
	public static final void setProjectName(IProject project, String name) {
		ProjectScope scope = new ProjectScope(project);
		IEclipsePreferences node = scope.getNode(RustProjectNature.NATURE_ID);
		String projectName = ProjectConstants.PROJECT_NAME;
		node.put(projectName, name);
		saveNode(node);
	}
	
	public static final String getProjectName(IProject project) {
		ProjectScope scope = new ProjectScope(project);
		IEclipsePreferences node = scope.getNode(RustProjectNature.NATURE_ID);
		return node.get(ProjectConstants.PROJECT_NAME, ProjectConstants.PROJECT_NAME_DEFAULT);
	}

	public static final void setMainFileLocation(IProject project, String location) {
		ProjectScope scope = new ProjectScope(project);
		IEclipsePreferences node = scope.getNode(RustProjectNature.NATURE_ID);
		
		String mainFileLoc = ProjectConstants.PROJECT_MAIN_FILE_LOCATION;
		String mainFileName = ProjectConstants.MAIN_FILE_NAME;
		node.put(mainFileLoc, location);
		node.put(mainFileName, mainFile(location));
		saveNode(node);
	}
	
	private static final String mainFile(String location) {
		String[] split = location.split("/");
		String mainFile = split[split.length - 1];
		return mainFile;
	}
	
	public static final String getMainFileName(IProject project) {
		ProjectScope scope = new ProjectScope(project);
		IEclipsePreferences node = scope.getNode(RustProjectNature.NATURE_ID);
		return node.get(ProjectConstants.MAIN_FILE_NAME,
				ProjectConstants.MAIN_FILE_NAME_DEFAULT);
	}
	
	public static final String getMainFileLocation(IProject project) {
		ProjectScope scope = new ProjectScope(project);
		IEclipsePreferences node = scope.getNode(RustProjectNature.NATURE_ID);
		return node.get(ProjectConstants.PROJECT_MAIN_FILE_LOCATION,
				ProjectConstants.PROJECT_MAIN_FILE_LOCATION_DEFAULT);
	}
	
	public static final void setBinFolder(IProject project, String folder) {
		ProjectScope scope = new ProjectScope(project);
		IEclipsePreferences node = scope.getNode(RustProjectNature.NATURE_ID);
		String binFolder = ProjectConstants.PROJECT_BIN_FOLDER_LOCATION;
		node.put(binFolder, folder);
		saveNode(node);
	}
	
	public static final String getBinFolder(IProject project) {
		ProjectScope scope = new ProjectScope(project);
		IEclipsePreferences node = scope.getNode(RustProjectNature.NATURE_ID);
		return node.get(ProjectConstants.PROJECT_BIN_FOLDER_LOCATION,
						ProjectConstants.PROJECT_BIN_FOLDER_LOCATION_DEFAULT);
	}
	
	private static void saveNode(IEclipsePreferences node) {
		if(node != null)
			try {
				node.flush();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
	}
	
	/** Gets the active project. */
	public static IProject getActiveProject() {
		ISelectionService selectionService = Workbench.getInstance().getActiveWorkbenchWindow().getSelectionService();
		ISelection selection = selectionService.getSelection();
		
		if(selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection).getFirstElement();
			if(element instanceof IResource) {
				IProject returnValue = ((IResource)element).getProject();
				return returnValue;
			}
		} else {
			throw new RuntimeException("No project found.");
		}
		return null;
	}
	
}
