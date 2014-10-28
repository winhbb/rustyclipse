package org.rustyclipse;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class RustyclipsePlugin extends AbstractUIPlugin {
	
	/** The plugin */
	private static RustyclipsePlugin plugin;
	
	/** Plugin ID of the Rustyclipse project */
	public static final String PluginID = "org.rustyclipse";
	
	/** Public access to the plugin instance */
	public static RustyclipsePlugin getDefault() {
		return plugin;
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
