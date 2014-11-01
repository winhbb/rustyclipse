package org.rustyclipse.run;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

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
		
		if(getCompilationType(project) == RUST) {
			if(compile(project) == 0) {
				run(project);	
			}
		} else if(getCompilationType(project) == CARGO) {
			if(cargoCompile(project) == 0) {
				cargoRun(project);	
			}
		}
		return null;
	}

	private int compile(IProject project) {
		try {
			String command = "";
			
			if(getOutDir() != null) {
				command = "rustc " + ProjectUtils.getMainFileLocation(project) + " --out-dir " 
						+ project.getFolder(getOutDir()).getLocation().toString();
				System.out.println(command);
			}
			else {
				RustyclipsePlugin.getConsole().errorLog("Out directory could not be found. Please check Rust preferences.");
				RustyclipsePlugin.getConsole().errorLog("Window -> Preferences -> Rust");
				return -1;
			}
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayOutputStream err = new ByteArrayOutputStream();
			
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
			String command = "cargo build";
			
			PumpStreamHandler handler = new PumpStreamHandler();
			
			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			exec.setWorkingDirectory(new File(project.getLocation().toString()));
			exec.setStreamHandler(handler);
			
			int exitValue = exec.execute(cmdLine);
			
			RustyclipsePlugin.getConsole().log("Compilation exited with the following value: " + exitValue);
			
			return exitValue;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	private int run(IProject project) {
		try {
			String OS = System.getProperty("os.name").toLowerCase();
			String mainFile = project.getFolder(getOutDir()).getFile(ProjectUtils.getMainFileName(project)).getLocation().toString();
			
			String command = "";
			
			if(OS.contains("win"))
				command = mainFile.substring(0, mainFile.length() - 3) + ".exe";
			else if(OS.contains("unix") || OS.contains("mac")) 
				command = ".\\" + mainFile.substring(0, mainFile.length() - 3); //Dunno if this works :>
			else {
				RustyclipsePlugin.getConsole().errorLog("Your operation system is not supported.");
				return - 1;
			}
			
			PumpStreamHandler handler = new PumpStreamHandler();

			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			exec.setStreamHandler(handler);
			
			int exitValue = exec.execute(cmdLine);
			
			return exitValue;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
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
		char[] returnValue;
		if(args != null) {
			if(args.contains("--out-dir")) {
				int outDir = args.indexOf("--out-dir") + 10;
				int endPoint = outDir + 2;
				int counter = endPoint;
				char[] spaceReturnValue;
				for(char point : args.substring(endPoint).toCharArray()) {
					counter += 1;
					if(point == ' ') {
						spaceReturnValue = new char[counter - outDir];
						args.getChars(outDir, counter, spaceReturnValue, 0);
						return new String(spaceReturnValue);
					}
				}
				
				returnValue = new char[counter - outDir];
				args.getChars(outDir, counter, returnValue, 0);
				
				return new String(returnValue);
			}
		}
		return null;
	}
	
}
