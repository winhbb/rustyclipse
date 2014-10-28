package org.rustyclipse.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

public class NewRustProjectWizardPage extends WizardPage {

	public NewRustProjectWizardPage() {
		super("New Rust project");
		setTitle("New Rust project");
		setDescription("Create a new rust project");
	}

	@Override
	public void createControl(Composite parent) {

	}

}
