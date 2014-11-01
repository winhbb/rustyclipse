package org.rustyclipse;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.rustyclipse.run.RustMessageConsole;

public class RustyclipsePlugin extends AbstractUIPlugin {
	
	/** The plugin */
	private static RustyclipsePlugin plugin;
	
	/** Plugin ID of the Rustyclipse project */
	public static final String PluginID = "org.rustyclipse";
	
	/** Plugins console */
	private static RustMessageConsole console = new RustMessageConsole();
	
	/** Public access to the plugin instance */
	public static RustyclipsePlugin getDefault() {
		return plugin;
	}
	
	/** Public access to the console */
	public static RustMessageConsole getConsole() {
		return console;
	}
	
	private static final String VERSION = "0.1.0";
	
	public static String getVersionText() {
		return VERSION;
	}
	
	@Override
	public void start(BundleContext context) {
		try {
			super.start(context);
			plugin = this;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop(BundleContext context) {
		try {
			plugin = null;
			super.stop(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
