package org.rustyclipse;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class RustyclipsePlugin extends AbstractUIPlugin {
	
	/** The plugin */
	public RustyclipsePlugin plugin;
	
	/** Plugin ID of the Rustyclipse project */
	public static final String PluginID = "org.rustyclipse";
	
	/** This plugin's preference store*/
	public IPreferenceStore prefStore = plugin.getPreferenceStore();
	
	@Override
	public void start(BundleContext context) {
		try {
			super.start(context);
			plugin = this;
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
