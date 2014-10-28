package org.rustyclipse.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.*;
import org.rustyclipse.RustyclipsePlugin;

public class RustIDEPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage  {

	public RustIDEPreferences() {
		setDescription("Rustyclipse v." + RustyclipsePlugin.getVersionText());
	}
	
	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected void createFieldEditors() {

	}

}
