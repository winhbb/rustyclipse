package org.rustyclipse.compilation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.eclipse.core.resources.IProject;
import org.rustyclipse.RustyclipsePlugin;
import org.rustyclipse.ui.util.BuildUtils;
import org.rustyclipse.ui.util.ProjectUtils;

public class RustCompiler {

	public static int compile(IProject project) {
		try {
			String command = "";
			
			if(BuildUtils.getOutDir() != null) {
				command = "rustc " + ProjectUtils.getMainFileLocation(project) + " --out-dir " 
						+ project.getFolder(BuildUtils.getOutDir()).getLocation().toString();
				System.out.println(command);
			}
			else {
				RustyclipsePlugin.getConsole().errorLog("Out directory could not be found. Please check Rust preferences.");
				RustyclipsePlugin.getConsole().errorLog("Window -> Preferences -> Rust");
				return -1;
			}
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
			
			PumpStreamHandler handler = new PumpStreamHandler(outputStream, errorStream);
			
			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			exec.setStreamHandler(handler);
			
			int exitValue = exec.execute(cmdLine);
			
			if(outputStream.toString().length() > 13)
				RustyclipsePlugin.getConsole().log(outputStream.toString());
			if(errorStream.toString().length() > 13)
				RustyclipsePlugin.getConsole().errorLog(errorStream.toString());
			
			RustyclipsePlugin.getConsole().log("Compilation exited with the following value: " + exitValue);
			
			return exitValue;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
}
