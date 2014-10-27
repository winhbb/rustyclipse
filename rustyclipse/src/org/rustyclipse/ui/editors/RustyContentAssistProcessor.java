package org.rustyclipse.ui.editors;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.*;

public class RustyContentAssistProcessor implements IContentAssistProcessor {

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		IDocument document = viewer.getDocument();
		
		return null;
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {

		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '.', ':', ':' };
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return new char[] { '.', ':', ':' };
	}

	@Override
	public String getErrorMessage() {

		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {

		return null;
	}

}
