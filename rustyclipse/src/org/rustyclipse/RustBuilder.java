package org.rustyclipse;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class RustBuilder extends IncrementalProjectBuilder {

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		switch(kind) {
			case INCREMENTAL_BUILD | AUTO_BUILD:
				return null;
			case CLEAN_BUILD | FULL_BUILD:
				return null;
		}
		return null;
	}

}
