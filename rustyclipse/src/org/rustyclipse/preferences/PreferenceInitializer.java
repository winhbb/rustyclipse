package org.rustyclipse.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.rustyclipse.RustyclipsePlugin;
import org.rustyclipse.ui.preferences.CargoPreferences;
import org.rustyclipse.ui.preferences.RustPreferences;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = RustyclipsePlugin.getDefault().getPreferenceStore();
		String OS = System.getProperty("os.name");
		
		if(OS.contains("win")) {
			store.putValue(RustPreferences.RUST_C, "C:/Program Files (x86)/Rust/bin/");
			store.putValue(CargoPreferences.CARGO_HOME, "C:/cargo/");
		}
		
		else if(OS.contains("mac") || OS.contains("unix")) {
			store.putValue(RustPreferences.RUST_C, "/usr/local/bin/");
			store.putValue(CargoPreferences.CARGO_HOME, "/usr/local/bin/");
		}
		
		store.putValue(RustPreferences.RUST_C_ARGS, "--out-dir bin");
	}

}
