package org.rustyclipse.run;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.eclipse.core.resources.IProject;
import org.rustyclipse.RustyclipsePlugin;

public class CargoRunner {

	public static int cargoRun(IProject project) {
		try {
			String command = "cargo run";
		
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
			
			PumpStreamHandler handler = new PumpStreamHandler(outputStream, errorStream);
			
			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			exec.setStreamHandler(handler);
			exec.setWorkingDirectory(new File(project.getLocation().toString()));

			int exitValue = exec.execute(cmdLine);
			
			RustyclipsePlugin.getConsole().log(outputStream.toString());
			RustyclipsePlugin.getConsole().errorLog(errorStream.toString());
			
			return exitValue;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	
}
