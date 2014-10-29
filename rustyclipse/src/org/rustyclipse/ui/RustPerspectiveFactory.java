package org.rustyclipse.ui;

import org.eclipse.ui.*;
import org.eclipse.ui.console.IConsoleConstants;

public class RustPerspectiveFactory implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		
		IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f, editorArea);
			topLeft.addView(IPageLayout.ID_PROJECT_EXPLORER);
			topLeft.addPlaceholder(IPageLayout.ID_BOOKMARKS);
			
		IFolderLayout bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.50f, "topLeft");
			 bottomLeft.addView(IPageLayout.ID_OUTLINE);
			 
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.75f, editorArea);
			bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
			bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
			
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
		layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
		
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");
		layout.addNewWizardShortcut("org.eclipse.ui.editors.wizards.UntitledTextFileWizard");
		layout.addNewWizardShortcut("rustyclipse.newrsfile");
		layout.addNewWizardShortcut("rustyclipse.newrustproject");
	}

}
