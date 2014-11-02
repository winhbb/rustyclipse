package org.rustyclipse.run;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.rustyclipse.RustyclipsePlugin;
import org.rustyclipse.ui.editors.RustEditor;
import org.rustyclipse.ui.preferences.RustPreferences;
import org.rustyclipse.ui.util.ProjectUtils;

public class RustRunner extends AbstractHandler {
	
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
		
		if(getCompilationType(project) == RUST)
			if(compile(project) == 0)
				run(project);
		else
			if(cargoCompile(project) == 0)
				cargoRun(project);
		return null;
	}

	private int compile(IProject project) {
		try {
			String command = "";
			
			if(getOutDir() != null)
				command = "rustc " + ProjectUtils.getMainFileLocation(project) + " --out-dir " + getOutDir();
			else {
				RustyclipsePlugin.getConsole().errorLog("Out directory could not be found. Please check Rust preferences.");
				RustyclipsePlugin.getConsole().errorLog("Window -> Preferences -> Rust");
				return -1;
			}
			
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			ByteArrayOutputStream err=new ByteArrayOutputStream();
			
			PumpStreamHandler handler = new PumpStreamHandler(out, err);
			
			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			exec.setStreamHandler(handler);
			
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
			
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			ByteArrayOutputStream err=new ByteArrayOutputStream();
			
			PumpStreamHandler handler = new PumpStreamHandler(out, err);
			
			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			exec.setWorkingDirectory(new File(project.getRawLocation().toString()));
			exec.setStreamHandler(handler);
			
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
	
	private String getOutDir() {
		String args = prefStore.getString(RustPreferences.RUST_C_ARGS);
		char[] returnValue = new char[15];
		if(args != null) {
			if(args.contains("--out-dir")) {
				int outDir = args.indexOf("--out-dir") + 2;
				int endPoint = outDir + 1;
					for(int i = 0; i < 15; i++) {
						if(args.charAt(outDir) + i == ' ') {
							endPoint = outDir + i;
						}
					}
				args.getChars(outDir, endPoint, returnValue, 0);
				return new String(returnValue);
			}
		}
		return null;
	}
	
}
