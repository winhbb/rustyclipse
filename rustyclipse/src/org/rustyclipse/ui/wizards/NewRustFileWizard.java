package org.rustyclipse.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewRustFileWizard extends Wizard implements INewWizard {

	NewRustFileWizardPage pageFile;
	
	public NewRustFileWizard() {
		setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		pageFile = new NewRustFileWizardPage();
		addPage(pageFile);
	}
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
	}

	@Override
	public boolean performFinish() {
		
		return false;
	}

}
