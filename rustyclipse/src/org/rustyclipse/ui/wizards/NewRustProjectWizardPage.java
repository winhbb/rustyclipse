package org.rustyclipse.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
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
import org.rustyclipse.RustyclipsePlugin;

public class NewRustProjectWizardPage extends WizardPage {

	private Composite container;
	
	private static Text version;
	private static Text projectName;
	
	private static Button useCargo;
	private static Button createReadme;
	
	private IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName.getText());
	private IFile mainFile;
	
	public static String getProjectName() {
		return projectName.getText();
	}
	
	public static String getVersionNumber() {
		return version.getText();
	}
	
	public NewRustProjectWizardPage() {
		super("New Rust project");
		setTitle("New Rust project");
		setDescription("Create a new rust project");
	}

	@Override
	public void createControl(Composite parent) {
		GridLayout layout = new GridLayout();
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		
		container = new Composite(parent, SWT.NONE);
		container.setLayout(layout);
		
		layout.numColumns = 3;
		layout.verticalSpacing = 5;
		
		Label label = new Label(container, SWT.NONE);
		label.setText("Project name:");
		
		projectName = new Text(container, SWT.BORDER);
		projectName.setText("Project " + getWSProjectCount());
		projectName.setLayoutData(gridData);
		projectName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		Label versionLabel = new Label(container, SWT.NONE);
		versionLabel.setText("Version:");
		
		version = new Text(container, SWT.BORDER);
		version.setLayoutData(gridData);
		version.setText("1.0.0");
		version.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		useCargo = new Button(container, SWT.CHECK);
		useCargo.setLayoutData(gridData);
		useCargo.setText("Use Cargo:");
		useCargo.setToolTipText("Highly adviced to use Cargo, as of it makes development in Rust easier.");
		useCargo.setSelection(true);
		useCargo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dialogChanged();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		
		createReadme = new Button(container, SWT.CHECK);
		createReadme.setLayoutData(gridData);
		createReadme.setText("Create Readme");
		createReadme.setToolTipText("Create's a readme file for cargo to use.");
		createReadme.setSelection(false);
		
		setControl(container);
	}
	
	public void createProject() {
		if(project != null) {
			IProjectDescription desc = project.getWorkspace().newProjectDescription(project.getName());
			try {
				project.create(desc, null);
				
				if(project.isOpen()) {
					project.open(null);
				}
				
				if(!project.getFolder("src").exists())
					project.getFolder("src").create(false, true, null);
				
				
				if(useCargo.getSelection())
					if(!project.getFile("Cargo.toml").exists())
						project.getFile("Cargo.toml").create(createCargoFile(), false, null);
				
			} catch(CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	private InputStream createMainFile() {
		StringBuilder content = new StringBuilder()
			.append("mod main;")
			.append(System.getProperty("line.separator"))
			.append("fn main() {")
			.append("	println(\"Hello World!\");")
			.append("}");
		return new ByteArrayInputStream(content.toString().getBytes());
	}
	
	private InputStream createCargoFile() {
		StringBuilder content = new StringBuilder()
			.append("[project]")
			.append(System.getProperty("line.separator"))
			.append("name = \"" + projectName.getText() + "\"")
			.append("version = \"" + version.getText() + "\"");
		if(createReadme.getSelection()) {
			content.append("readme = \"" + createReadme + "\"");
		}
		content.append(System.getProperty("line.separator"))
			.append("[[bin]]")
			.append(System.getProperty("line.separator"))
			.append("name = \"")
			.append("path = \"");
		return new ByteArrayInputStream(content.toString().getBytes());
	}
	
	private InputStream readmeStream() {
		StringBuilder builder = new StringBuilder()
			.append(projectName.getText());
		return new ByteArrayInputStream(builder.toString().getBytes());
	}
	
	private String createReadme() throws CoreException {
		String readmeString = "README.md";
		IFile readmeFile = project.getFile(readmeString);
		
		if(!readmeFile.exists())
			readmeFile.create(readmeStream(), false, null);
		return readmeString;
	}

	private void dialogChanged() {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName.getText());
		if(projectName.getText() == null || projectName.getText().length() == 0) {
			updateStatus("Project name can't be empty.");
		}
		if(useCargo.getSelection()) {
			createReadme.setEnabled(true);
		}
		updateStatus(null);
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	private int getWSProjectCount() {
		IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(ResourcesPlugin.getWorkspace().getRoot().getFullPath());
		try {
			return folder.members().length;
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
