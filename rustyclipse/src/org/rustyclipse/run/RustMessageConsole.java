package org.rustyclipse.run;

import java.io.IOException;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class RustMessageConsole {

	private static MessageConsole instance;
	private static MessageConsoleStream messageStream;
	
	private MessageConsole getInstance() {
		return instance;
	}
	
	public RustMessageConsole() {
		if(getInstance() == null)
			instance = new MessageConsole("Rust Console", null);
		
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { instance });
		messageStream = instance.newMessageStream();
		instance.activate();
	}
	
	public void wipeLog() {
		messageStream.getConsole().clearConsole();
	}
	
	public void log(String message) {
		messageStream.println("<LOG> " + message);
	}
	
	public void errorLog(String message) {
		messageStream.println("<ERROR> " + message);
	}
	
	public void warningLog(String message) {
		messageStream.println("<WARNING> " + message);
	}
	
	public void closeMessageStream() {
		try {
			messageStream.flush();
			messageStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
