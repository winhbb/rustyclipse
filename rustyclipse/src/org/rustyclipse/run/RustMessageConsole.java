package org.rustyclipse.run;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class RustMessageConsole extends MessageConsole {

	private static MessageConsoleStream outputStream;
	private static MessageConsoleStream errorStream;
	private static MessageConsoleStream warningStream;
	
	private static Color BLACK = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	private static Color RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	private static Color YELLOW = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
	
	
	public RustMessageConsole() {
		super("Rust Console", null);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { this });
		
		outputStream = super.newMessageStream();
		errorStream = super.newMessageStream();
		warningStream = super.newMessageStream();
		
		outputStream.setColor(BLACK);
		errorStream.setColor(RED);
		warningStream.setColor(YELLOW);
		
		super.activate();
	}
	
	public void wipeLog() {
		clearConsole();
	}

	public void log(String message) {
		outputStream.println(message);
	}
	
	public void errorLog(String message) {
		errorStream.println(message);
	}
	
	public void warningLog(String message) {
		warningStream.println(message);
	}
	
	public void closeMessageStream() {
		dispose();
	}
	
}
