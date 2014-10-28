package org.rustyclipse.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.rustyclipse.RustyclipsePlugin;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = RustyclipsePlugin.getDefault().getPreferenceStore();
		String OS = System.getProperty("os.name");
		
		if(OS.contains("win")) {
			store.putValue(RUST_C, "C:/Program Files (x86)/Rust/bin/");
			store.putValue(CARGO_HOME, "C:/cargo/");
		}
		
		else if(OS.contains("mac") || OS.contains("unix")) {
			store.putValue(RUST_C, "/usr/local/bin/");
			store.putValue(CARGO_HOME, "/usr/local/bin/");
		}
	}

	private static final String RUST_C = "rustCompilerHome";
	private static final String CARGO_HOME = "cargoLocation";
	
}
