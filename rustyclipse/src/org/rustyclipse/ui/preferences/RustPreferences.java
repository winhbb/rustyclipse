package org.rustyclipse.ui.preferences;

import org.eclipse.jface.layout.*;
import org.eclipse.jface.preference.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

import org.eclipse.ui.*;
import org.rustyclipse.RustyclipsePlugin;

public class RustPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public RustPreferences() {
		super(FieldEditorPreferencePage.GRID);
		setPreferenceStore(RustyclipsePlugin.getDefault().getPreferenceStore());
	}
	
	@Override
	protected void createFieldEditors() {
		Group group = new Group(getFieldEditorParent(), SWT.NONE);
		group.setText("Rust preferences");
		
		GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.TOP).applyTo(group);
		GridLayoutFactory.fillDefaults().margins(10, 4).applyTo(group);

		Composite fieldParent = new Composite(group, SWT.NONE);
		GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.TOP).hint(150, -1).applyTo(fieldParent);
		
		addField(new DirectoryFieldEditor(RUST_C, "Rust Compiler Home:", fieldParent));
		addField(new DirectoryFieldEditor(RUST_C_ARGS, "Rust Runtime Arguments:", fieldParent));
	}

	@Override
	public void init(IWorkbench workbench) {
		
	}
	
	public static final String RUST_C = "rustCompilerHome";
	public static final String RUST_C_ARGS = "rustCompilerArguments";
}
