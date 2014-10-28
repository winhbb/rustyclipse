package org.rustyclipse.ui;

import org.eclipse.ui.console.MessageConsole;

public class RustMessageConsole {

	private static MessageConsole instance;
	
	public static MessageConsole getInstance() {
		return instance;
	}
	
	public static void initialize() {
		if(getInstance() == null)
			instance = new MessageConsole("Console", null);
		
		getInstance().activate();
	}
	
	public static void log() {
		
	}
	
}
