package org.rustyclipse.run;

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
import org.rustyclipse.ui.editors.RustEditor;
import org.rustyclipse.ui.util.ProjectUtils;

public class RustRunner extends AbstractHandler {
	
	RustBuilder builder = new RustBuilder();
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		RustEditor editor = (RustEditor) (window.getActivePage().getActiveEditor().getAdapter(ITextEditor.class));
		IResource resource = (IResource) (editor.getEditorInput().getAdapter(IResource.class));
		IProject project = resource.getProject();
		
		compile(project);
		return null;
	}

	private int compile(IProject project) {
		try {
		
			String command = "rustc " + ProjectUtils.getMainFileLocation(project) + " --out-dir " +
					ProjectUtils.getBinFolder(project);
			System.out.println(command);
			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			int exitValue = exec.execute(cmdLine);
		
			return exitValue;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
}
