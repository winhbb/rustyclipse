package org.rustyclipse;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.rustyclipse.compilation.CargoCompiler;
import org.rustyclipse.compilation.RustCompiler;
import org.rustyclipse.run.CargoRunner;
import org.rustyclipse.run.RustRunner;
import org.rustyclipse.ui.editors.RustEditor;

public class RustHandler extends AbstractHandler {

	IPreferenceStore prefStore = RustyclipsePlugin.getDefault().getPreferenceStore();
	
	// The compilation types.
	private final int RUST = 1;
	private final int CARGO = 2;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		RustEditor editor = (RustEditor) (window.getActivePage().getActiveEditor().getAdapter(ITextEditor.class));
		IResource resource = (IResource) (editor.getEditorInput().getAdapter(IResource.class));
		IProject project = resource.getProject();
		
		RustyclipsePlugin.getConsole().wipeLog();
		
		if(getCompilationType(project) == RUST) {
			if(RustCompiler.compile(project) == 0) {
				RustRunner.run(project);	
			}
		} else if(getCompilationType(project) == CARGO) {
			if(CargoCompiler.cargoCompile(project) == 0) {
				CargoRunner.cargoRun(project);	
			}
		}
		return null;
	}
	
	private int getCompilationType(IProject project) {
		if(project.getFile("Cargo.toml").exists()) {
			return CARGO;
		} else {
			return RUST;
		}
	}
	
}
