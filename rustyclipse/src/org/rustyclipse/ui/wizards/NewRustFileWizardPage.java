package org.rustyclipse.ui.wizards;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

public class NewRustFileWizardPage extends WizardPage {

	private Composite container;
	
	private static Text sourceFolderName;
	private static Text fileName;
	
	public static String getFileName() {
		return fileName.getText();
	}
	
	public static String getSourceFolder() {
		return sourceFolderName.getText();
	}
	
	public NewRustFileWizardPage() {
		super("New Rust file");
		setTitle("New Rust file");
		setDescription("Create a new rust file");
	}

	@Override
	public void createControl(Composite parent) {
		GridLayout layout = new GridLayout();
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		
		container = new Composite(parent, SWT.NONE);
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 5;
		
		Label label = new Label(container, SWT.NULL);
		label.setText("Source Folder:");
		
		sourceFolderName = new Text(container, SWT.BORDER);
		sourceFolderName.setText("src");
		sourceFolderName.setLayoutData(gridData);
		sourceFolderName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleFileBrowse(sourceFolderName);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		
		Label labelFileName = new Label(container, SWT.NONE);
		labelFileName.setText("File Name:");
		
		fileName = new Text(container, SWT.BORDER);
		fileName.setText("new_file.rs");
		fileName.setLayoutData(gridData);
		fileName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		setControl(container);
		dialogChanged();
	}
	
	private void dialogChanged() {
		IResource fileHome = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(sourceFolderName.getText()));
		
		if(fileHome == null || (fileHome.getType() & (IResource.PROJECT | IResource.FILE)) == 0) {
			updateStatus("Location is unavaiable");
			return;
		} else if(!fileHome.isAccessible()) {
			updateStatus("Unable to access folder location.");
			return;
		}
		
		if(fileName.getText() == null || fileName.getText().length() == 0) {
			updateStatus("File name required.");
		}
		
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	private void handleFileBrowse(Text text) {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog
				(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false, "Select File");
		if(dialog.open() == Window.OK) {
			Object[] results = dialog.getResult();
			if(results.length == 1)
				text.setText(((Path) results[0]).toString());
		}
	}
	
}
