package org.rustyclipse.run;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.rustyclipse.RustyclipsePlugin;

public class RustRunner extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			String[] cmd = new String[3];
			String OS = System.getProperty("os.name").toLowerCase();
			
			if(OS.contains("win")) {
				cmd[0] = "";
			}
			
		} catch(Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	private String findExecutable() {
		String outDir = "";
		
		IPreferenceStore prefStore = RustyclipsePlugin.getDefault().getPreferenceStore();
		String[] runtimeArgs = prefStore.getString("RUST_C_ARGS").split(" ");
		
		for(int i = 0; i < runtimeArgs.length; i++) {
			if(runtimeArgs[i] == "--output-dir") {
				outDir = runtimeArgs[i + 1];
				break;
			}
		}
		
		if(!outDir.isEmpty()) {
			try {
				IResource[] executable = RustyclipsePlugin.getActiveProject().getFolder(outDir).members();
				IResource[] executables = new IResource[executable.length];
				int count = 0;
				for(int i = 0; i < executable.length; i++) {
					count++;
					if(executable[i].getFileExtension() == ".exe") {
						executables[i] = executable[i];
						break;
					}
				}
				if(executables.length > 0) {
					RustyclipsePlugin.getConsole().errorLog("There can only be one executable in output dir.");
				} else {
					return executables[count].getName();
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
		return null;
		
	}
	
	public static void run() {
		try {
			compile();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean compile() throws IOException, InterruptedException {
		IFile mainFile = RustyclipsePlugin.getActiveProject().getFolder("src").getFile("main.rs");
		if(mainFile != null) {
			String[] cmd = new String[3];
			String OS = System.getProperty("os.name").toLowerCase();
			
			if(OS.contains("win")) {
				cmd[0] = "rustc";
				cmd[1] = mainFile.getLocation().toString();
				cmd[2] = "";
			}
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd);
			
			ProcessLogger eLogger = new ProcessLogger(proc.getErrorStream(), true);
			ProcessLogger oLogger = new ProcessLogger(proc.getInputStream(), false);
			
			eLogger.start();
			oLogger.start();
			
			if(proc.waitFor() == 0) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
	
	private static boolean compileCargo() {
		IFile cargoFile = RustyclipsePlugin.getActiveProject().getFile("Cargo.toml");
		if(!cargoFile.exists()) {
			RustyclipsePlugin.getConsole().errorLog("You fucked up");
			return false;
		}
		
		return false;
	}
	
}
