package org.rustyclipse;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class RustProjectNature implements IProjectNature {

	IProject project;
	
	@Override
	public void configure() throws CoreException {
		IProjectDescription projDesc = project.getDescription();
		ICommand[] commands = projDesc.getBuildSpec();
		for(int i = 0; i < commands.length; i++) {
			if(commands[i].getBuilderName().contains(RustBuilder.BUILDER_ID)) {
				break;
			}
		}
		ICommand newCommand = projDesc.newCommand();
		newCommand.setBuilderName(RustBuilder.BUILDER_ID);
		commands[commands.length] = newCommand;
		projDesc.setBuildSpec(commands);
	}

	@Override
	public void deconfigure() throws CoreException {
		IProjectDescription projDesc = project.getDescription();
		ICommand[] commands = projDesc.getBuildSpec();
		ICommand[] newCommands = new ICommand[commands.length];
		for (int i = 0; i < commands.length; i++) {
			if(commands[i].getBuilderName().equals(RustBuilder.BUILDER_ID)) {
				newCommands[i] = commands[i];
			}
		}
		projDesc.setBuildSpec(newCommands);
		project.setDescription(projDesc, null);
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}

}
