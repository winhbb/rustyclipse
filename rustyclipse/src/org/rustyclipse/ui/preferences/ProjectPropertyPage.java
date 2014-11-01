package org.rustyclipse.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;
import org.rustyclipse.ui.util.ProjectUtils;

public class ProjectPropertyPage extends PropertyPage implements IWorkbenchPropertyPage {

	private Composite container;
	
	private IProject project;
	private Text projectMainFileLocation;
	
	@Override
	protected Control createContents(Composite parent) {
		GridLayout layout = new GridLayout();
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		container = new Composite(parent, SWT.NULL);
		container.setLayout(layout);
		
		layout.numColumns = 1;
		layout.verticalSpacing = 5;
		
		initialize();
		
		Label projectName = new Label(container, SWT.NONE);
		projectName.setText("Project Name: " + ProjectUtils.getProjectName(project));
		projectName.setLayoutData(gd);
		
		Group group = new Group(container, SWT.NONE);
		GridLayout groupLayout = new GridLayout();
		GridData groupData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		groupLayout.numColumns = 3;
		group.setLayout(groupLayout);
		group.setLayoutData(groupData);
		
		Label projectMainFile = new Label(group, SWT.SHADOW_ETCHED_IN);
		projectMainFile.setText("Main File:");
		projectMainFile.setToolTipText("Main file is used to compile and run the program.");
		projectMainFile.setLayoutData(gd);
		
		projectMainFileLocation = new Text(group, SWT.SINGLE | SWT.BORDER);
		projectMainFileLocation.setText(ProjectUtils.getMainFileLocation(project));
		projectMainFileLocation.setLayoutData(gd);
		
		Button browseMainFile = new Button(group, SWT.PUSH);
		browseMainFile.setText("Browse..");
		browseMainFile.setLayoutData(gd);
		browseMainFile.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleFileBrowse();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		return container;
	}
	
	private void handleFileBrowse() {
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.rs" });
		dialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName()).getLocation().toString());
		String result = dialog.open();
		projectMainFileLocation.setText(result);
	}

	private void initialize() {
		setTitle("Rust Properties");
		setDescription("Rust properties handle the properties for Rust projects.\n"
				+ "This property section only handles the main file location and project name.");
		project = (IProject) getElement().getAdapter(IResource.class);
	}
	
}
