package org.rustyclipse.ui.preferences;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.rustyclipse.RustyclipsePlugin;

public class CargoPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage  {

	public static final String CARGO_HOME = "cargoLocation";

	public CargoPreferences() {
		super(FieldEditorPreferencePage.GRID);
		setPreferenceStore(RustyclipsePlugin.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		Group group = new Group(getFieldEditorParent(), SWT.NONE);
		group.setText("Cargo preferences");
		
		GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.TOP).applyTo(group);
		GridLayoutFactory.fillDefaults().margins(10, 4).applyTo(group);

		Composite fieldParent = new Composite(group, SWT.NONE);
		GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.TOP).hint(150, -1).applyTo(fieldParent);
		
		DirectoryFieldEditor cargoHome = new DirectoryFieldEditor(CARGO_HOME, "Cargo Home:", fieldParent);
		
		addField(cargoHome);
	}
	
	@Override
	public void init(IWorkbench workbench) {
	
	}
}
