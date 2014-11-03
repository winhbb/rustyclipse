package org.rustyclipse.run;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.eclipse.core.resources.IProject;
import org.rustyclipse.RustyclipsePlugin;
import org.rustyclipse.ui.util.BuildUtils;
import org.rustyclipse.ui.util.ProjectUtils;

public class RustRunner {
	
	public static int run(IProject project) {
		try {
			RustyclipsePlugin.getConsole().log("Running project.\n");
			
			String OS = System.getProperty("os.name").toLowerCase();
			String mainFile = project.getFolder(BuildUtils.getOutDir()).getFile(ProjectUtils.getMainFileName(project)).getName();
			
			String command = "";
			
			if(OS.contains("win"))
				command = "cmd /c " + mainFile.substring(0, mainFile.length() - 3) + ".exe";
			else if(OS.contains("unix") || OS.contains("mac")) 
				command = ".\\" + mainFile.substring(0, mainFile.length() - 3); //Dunno if this works :>
			else {
				RustyclipsePlugin.getConsole().errorLog("Your operation system is not supported.");
				return - 1;
			}
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
			
			PumpStreamHandler handler = new PumpStreamHandler(outputStream, errorStream);

			CommandLine cmdLine = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			exec.setStreamHandler(handler);
			exec.setWorkingDirectory(new File(project.getFolder(BuildUtils.getOutDir()).getLocation().toString()));
			
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
