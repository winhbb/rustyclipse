package org.rustyclipse.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.rustyclipse.RustyclipsePlugin;

public class ProcessLogger extends Thread {

	InputStream is;
	String type;
	
	public ProcessLogger(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}
	
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while(( line = br.readLine()) != null) {
				RustyclipsePlugin.getConsole().log(type + ">" + line);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
