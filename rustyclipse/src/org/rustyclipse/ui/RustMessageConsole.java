package org.rustyclipse.ui;

import java.io.IOException;

import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class RustMessageConsole {

	private static MessageConsole instance;
	private static MessageConsoleStream messageStream;
	
	//public access
	public static MessageConsole getInstance() {
		return instance;
	}
	
	//public access
	public static MessageConsoleStream getMessageStream() {
		return messageStream;
	}
	
	public static void initialize() {
		if(getInstance() == null)
			instance = new MessageConsole("Rust Console", null);
		
		messageStream = instance.newMessageStream();
		instance.activate();
	}
	
	public static void log(String message) {
		try {
			messageStream.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeMessageStream() {
		try {
			messageStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
