package org.rustyclipse.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.rustyclipse.RustyclipsePlugin;

public class RustIDEPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage  {

	public RustIDEPreferences() {
		setDescription("Rustyclipse v." + RustyclipsePlugin.getVersionText());
	}
	
	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createFieldEditors() {
		// TODO Auto-generated method stub
		
	}

}
