package org.rustyclipse.compilation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.eclipse.core.resources.IProject;
import org.rustyclipse.RustyclipsePlugin;

public class CargoCompiler {

	public static int cargoCompile(IProject project) {
		try {
			String command = "cargo build";
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
			
			PumpStreamHandler handler = new PumpStreamHandler(outputStream, errorStream);
			
			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			exec.setWorkingDirectory(new File(project.getLocation().toString()));
			exec.setStreamHandler(handler);
			
			int exitValue = exec.execute(cmdLine);
			
			RustyclipsePlugin.getConsole().log(outputStream.toString());
			RustyclipsePlugin.getConsole().errorLog(errorStream.toString());
			
			RustyclipsePlugin.getConsole().log("Compilation exited with the following value: " + exitValue + "\n");
			
			return exitValue;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
}
