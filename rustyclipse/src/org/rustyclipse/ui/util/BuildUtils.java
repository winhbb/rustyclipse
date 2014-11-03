package org.rustyclipse.ui.util;

import org.eclipse.jface.preference.IPreferenceStore;
import org.rustyclipse.RustyclipsePlugin;
import org.rustyclipse.ui.preferences.RustPreferences;

public class BuildUtils {

	static IPreferenceStore prefStore = RustyclipsePlugin.getDefault().getPreferenceStore();
	
	public static String getOutDir() {
		String args = prefStore.getString(RustPreferences.RUST_C_ARGS);
		char[] returnValue;
		if(args != null) {
			if(args.contains("--out-dir")) {
				int outDir = args.indexOf("--out-dir") + 10;
				int endPoint = outDir + 2;
				int counter = endPoint;
				char[] spaceReturnValue;
				for(char point : args.substring(endPoint).toCharArray()) {
					counter += 1;
					if(point == ' ') {
						spaceReturnValue = new char[counter - outDir];
						args.getChars(outDir, counter, spaceReturnValue, 0);
						return new String(spaceReturnValue);
					}
				}
				
				returnValue = new char[counter - outDir];
				args.getChars(outDir, counter, returnValue, 0);
				
				return new String(returnValue);
			}
		}
		return null;
	}
	
}
