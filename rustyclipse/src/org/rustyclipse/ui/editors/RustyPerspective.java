package org.rustyclipse.ui.editors;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class RustyPerspective implements IPerspectiveFactory {

	private IPageLayout factory;
	
	@Override
	public void createInitialLayout(IPageLayout layout) {
		this.factory = factory;
	}

}
