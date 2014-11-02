package org.rustyclipse.ui.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;
import org.rustyclipse.RustProjectNature;

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
		String projectName = ProjectConstants.PROJECT_MAIN_FILE_LOCATION;
		node.put(projectName, location);
		saveNode(node);
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
	
}
