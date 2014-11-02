package org.rustyclipse.run;

import java.io.IOException;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class RustMessageConsole {

	private static MessageConsole instance;
	private static MessageConsoleStream messageStream;
	
	//private static Color black = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	//private static Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	//private static Color yellow = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
	
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
		//messageStream.setColor(black);
		messageStream.println(message);
	}
	
	public void errorLog(String message) {
		//messageStream.setColor(red);
		messageStream.println(message);
	}
	
	public void warningLog(String message) {
		//messageStream.setColor(yellow);
		messageStream.println(message);
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
