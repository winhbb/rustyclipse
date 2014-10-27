package org.rustyclipse.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.rustyclipse.ui.util.ColorManager;

public class RustEditor extends TextEditor {

	public RustEditor() {
		super();
		setSourceViewerConfiguration(new RustyConfiguration(new ColorManager()));
	}
	
	public void dispose() {
		super.dispose();
	}
	
	public IProject getCurrentProject() {
		if(getEditorInput() instanceof IFileEditorInput) {
			IFileEditorInput input = (IFileEditorInput) getEditorInput();
			
			IFile file = input.getFile();
			
			if(file != null) {
				return file.getProject();
			}
		}
		return null;
	}
	
}
