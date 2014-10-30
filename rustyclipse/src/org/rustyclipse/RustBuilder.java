package org.rustyclipse;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.rustyclipse.run.RustRunner;

public class RustBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "org.rustyclipse.RustBuilder";
	
	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		switch(kind) {
			case INCREMENTAL_BUILD | AUTO_BUILD:
				return null;
			case CLEAN_BUILD | FULL_BUILD:
				RustRunner.run();
				return null;
		}
		return null;
	}

}
