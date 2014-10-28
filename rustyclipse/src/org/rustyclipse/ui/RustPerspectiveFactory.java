package org.rustyclipse.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class RustPerspectiveFactory implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		
		IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f, editorArea);
			topLeft.addView(IPageLayout.ID_PROJECT_EXPLORER);
			topLeft.addPlaceholder(IPageLayout.ID_BOOKMARKS);
			
		IFolderLayout bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.50f, "topLeft");
			 bottomLeft.addView(IPageLayout.ID_OUTLINE);
	}

}