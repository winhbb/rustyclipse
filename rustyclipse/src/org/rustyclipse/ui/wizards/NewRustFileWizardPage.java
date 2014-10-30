package org.rustyclipse.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.rustyclipse.RustyclipsePlugin;

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
		fileName.setText("main.rs");
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
	
	private String getMod(String[] loc) {
		if(loc.length == 1) {
			return "main";
		} else {
			return loc[loc.length - 1];
		}
	}
	
	private InputStream createRustFile() {
		StringBuilder content = new StringBuilder()
			.append("mod " + getMod(sourceFolderName.getText().split("/")) + ";")
			.append("\n\n")
			.append("fn main() {")
			.append("\n")
			.append("	println!(\"Hello World!\");")
			.append("\n")
			.append("}");
		return new ByteArrayInputStream(content.toString().getBytes());
	}
	
	public boolean createFile() {
		try {
			IProject project = RustyclipsePlugin.getActiveProject();
			IFolder folder = project.getFolder(sourceFolderName.getText());
			
			if(!folder.exists())
				folder.create(false, true, null);
			
			IFile file = folder.getFile(fileName.getText());
			if(file.exists()) {
				updateStatus("File with the name " + fileName.getText() + " already exists.");
				return false;
			}
			else if (!file.exists())
				file.create(createRustFile(), false, null);
			
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void dialogChanged() {
		IProject project = RustyclipsePlugin.getActiveProject();
		IFile file = project.getFolder(sourceFolderName.getText()).getFile(getFileName());
		
		if(file.exists()) {
			updateStatus("File at the location already exists.");
		}
		
		if(!fileName.getText().endsWith(".rs")) {
			updateStatus("Must end with .rs");
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
				if(results[0].toString().startsWith("/" + RustyclipsePlugin.getActiveProject().getName() + "/"))
					text.setText(((Path) results[0]).toString().substring(9));
		}
	}
	
}
