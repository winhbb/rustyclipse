package org.rustyclipse.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
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
import org.rustyclipse.RustProjectNature;
import org.rustyclipse.ui.util.ProjectUtils;
public class NewRustProjectWizardPage extends WizardPage {

	private Composite container;
	
	private static Text version;
	private static Text projectName;
	
	private static Button useCargo;
	
	private static IProject project;
	private IFile mainFile;
	private IFolder binFolder;
	
	public NewRustProjectWizardPage() {
		super("New Rust project");
		setTitle("New Rust project");
		setDescription("Create a new rust project");
	}

	@Override
	public void createControl(Composite parent) {
		GridLayout layout = new GridLayout(4, true);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		
		GridData textData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		textData.horizontalSpan = 3;
		
		container = new Composite(parent, SWT.NONE);
		container.setLayout(layout);
		
		layout.numColumns = 2;
		layout.verticalSpacing = 5;
		
		Label label = new Label(container, SWT.NONE);
		label.setText("Project name:");
		
		projectName = new Text(container, SWT.BORDER);
		projectName.setText("Project");
		projectName.setLayoutData(textData);
		projectName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		Label versionLabel = new Label(container, SWT.NONE);
		versionLabel.setText("Version:");
		
		version = new Text(container, SWT.BORDER);
		version.setLayoutData(textData);
		version.setText("1.0.0");
		version.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		useCargo = new Button(container, SWT.CHECK);
		useCargo.setLayoutData(gridData);
		useCargo.setText("Use Cargo");
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
		
		dialogChanged();
		setControl(container);
	}
	
	public boolean createProject() throws CoreException {
		if(project != null) {
			IProjectDescription desc = project.getWorkspace().newProjectDescription(project.getName());
			try {
				String binFolderLoc = "";
				
				project.create(desc, null);
				
				if(!project.isOpen())
					project.open(null);
				
				if(!project.getFolder("src").exists())
					project.getFolder("src").create(false, true, null);
				
				if(!useCargo.getSelection()) {
					binFolder = project.getFolder("bin");
					if(!binFolder.exists())
						project.getFolder("bin").create(false, true, null);
					binFolderLoc = binFolder.getLocation().toString();
				}
				
				mainFile = project.getFolder("src").getFile("main.rs");
				if(!mainFile.exists())
					mainFile.create(createMainFile(), false, null);
				String mainFileLoc = mainFile.getLocation().toString();
				
				if(useCargo.getSelection())
					if(!project.getFile("Cargo.toml").exists())
						project.getFile("Cargo.toml").create(createCargoFile(), false, null);
				
				RustProjectNature.addNature(project);
				
				ProjectUtils.setMainFileLocation(project, mainFileLoc);
				ProjectUtils.setProjectName(project, projectName.getText());
				ProjectUtils.setBinFolder(project, binFolderLoc);
				
				return true;
			} catch(CoreException e) {
				updateStatus(e.getMessage());
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	private InputStream createMainFile() {
		StringBuilder content = new StringBuilder()
			.append("fn main() {")
			.append("\n")
			.append("	println!(\"Hello World!\");")
			.append("\n")
			.append("}");
		return new ByteArrayInputStream(content.toString().getBytes());
	}
	
	private InputStream createCargoFile() {
		StringBuilder content = new StringBuilder()
			.append("[project]")
			.append("\n\n")
			.append("name = \"" + projectName.getText() + "\"")
			.append("\n")
			.append("version = \"" + version.getText() + "\"")
			.append("\n")
			.append("authors = [\"Author Name <AuthorsEmail@gmail.com>\"]")
			.append("\n\n")
			.append("[[bin]]")
			.append("\n\n")
			.append("name = \"" + "main" + "\"")
			.append("\n")
			.append("path = \"" + "src/main.rs" + "\"");
		return new ByteArrayInputStream(content.toString().getBytes());
	}

	private void dialogChanged() {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName.getText());
		if(projectName.getText() == null || projectName.getText().length() == 0) {
			updateStatus("Project name can't be empty.");
		} else if(project.getWorkspace().getRoot().getProject(projectName.getText()).exists()) {
			updateStatus("Project already exists.");
		}
		
		updateStatus(null);
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
}
