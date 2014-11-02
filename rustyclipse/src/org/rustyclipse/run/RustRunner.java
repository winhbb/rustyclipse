package org.rustyclipse.run;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.rustyclipse.RustBuilder;
import org.rustyclipse.RustyclipsePlugin;
import org.rustyclipse.ui.editors.RustEditor;
import org.rustyclipse.ui.util.ProjectUtils;

public class RustRunner extends AbstractHandler {
	
	RustBuilder builder = new RustBuilder();
	
	// The compilation types.
	private final int RUST = 1;
	private final int CARGO = 2;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		RustEditor editor = (RustEditor) (window.getActivePage().getActiveEditor().getAdapter(ITextEditor.class));
		IResource resource = (IResource) (editor.getEditorInput().getAdapter(IResource.class));
		IProject project = resource.getProject();
		
		if(getCompilationType(project) == RUST)
			if(compile(project) == 0)
				run(project);
		else
			if(cargoCompile(project) == 0)
				run(project);
		return null;
	}

	private int compile(IProject project) {
		try {
		
			String command = "rustc " + ProjectUtils.getMainFileLocation(project) + " --out-dir " +
					ProjectUtils.getBinFolder(project);
			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			int exitValue = exec.execute(cmdLine);
			
			RustyclipsePlugin.getConsole().log("Compilation exited with the following value: " + exitValue);
			
			return exitValue;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	private int cargoCompile(IProject project) {
		try {
			
			String command = "cargo compile";
			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			exec.setWorkingDirectory(new File(project.getRawLocation().toString()));
			int exitValue = exec.execute(cmdLine);
			
			RustyclipsePlugin.getConsole().log("Compilation exited with the following value: " + exitValue);
			
			return exitValue;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	private void run(IProject project) {
		
	}
	
	private void cargoRun(IProject project) {
		
	}

	private int getCompilationType(IProject project) {
		if(project.getFile("Cargo.toml").exists()) {
			return CARGO;
		} else {
			return RUST;
		}
	}
	
}
