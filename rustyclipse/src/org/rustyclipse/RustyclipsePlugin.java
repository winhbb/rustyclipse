package org.rustyclipse;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.rustyclipse.run.RustMessageConsole;

@SuppressWarnings("restriction")
public class RustyclipsePlugin extends AbstractUIPlugin {
	
	/** The plugin */
	private static RustyclipsePlugin plugin;
	
	/** Plugin ID of the Rustyclipse project */
	public static final String PluginID = "org.rustyclipse";
	
	/** Plugins console */
	private static RustMessageConsole console = new RustMessageConsole();
	
	/** Public access to the plugin instance */
	public static RustyclipsePlugin getDefault() {
		return plugin;
	}
	
	/** Public access to the console */
	public static RustMessageConsole getConsole() {
		return console;
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
	
	/** Get the active file */
	public static IFile getActiveFile() {
		IWorkbenchPage wb = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorInput editor = wb.getActiveEditor().getEditorInput();
		IFile file = ((FileEditorInput) editor).getFile();
		return file;
	}
	
	private static final String VERSION = "0.1.0";
	
	public static String getVersionText() {
		return VERSION;
	}
	
	@Override
	public void start(BundleContext context) {
		try {
			super.start(context);
			plugin = this;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop(BundleContext context) {
		try {
			plugin = null;
			super.stop(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
