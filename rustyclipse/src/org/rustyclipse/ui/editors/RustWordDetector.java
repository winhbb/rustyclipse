package org.rustyclipse.ui.editors;

import org.eclipse.jface.text.rules.IWordDetector;

public class RustWordDetector implements IWordDetector {

	@Override
	public boolean isWordStart(char c) {
		return Character.isJavaIdentifierPart(c);
	}

	@Override
	public boolean isWordPart(char c) {
		return Character.isJavaIdentifierPart(c);
	}

}
