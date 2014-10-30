package org.rustyclipse.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.rustyclipse.RustyclipsePlugin;

public class ProcessLogger extends Thread {

	InputStream is;
	boolean error;
	
	public ProcessLogger(InputStream is, boolean error) {
		this.is = is;
		this.error = error;
	}
	
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while(( line = br.readLine()) != null) {
				if(error) {
					RustyclipsePlugin.getConsole().errorLog(line);
				} else {
					RustyclipsePlugin.getConsole().log(line);	
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}