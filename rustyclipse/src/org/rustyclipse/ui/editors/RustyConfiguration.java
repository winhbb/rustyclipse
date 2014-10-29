package org.rustyclipse.ui.editors;

import org.eclipse.jface.text.DefaultTextDoubleClickStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.RGB;
import org.rustyclipse.ui.util.ColorManager;

public class RustyConfiguration extends SourceViewerConfiguration {

	private ColorManager colorManager;
	
	public RustyConfiguration(ColorManager cm)  {
		this.colorManager = cm;
	}
	
	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String documentType) {
		return new DefaultTextDoubleClickStrategy();
	}
	
	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer viewer) {
		PresentationReconciler pr = new PresentationReconciler();
		DefaultDamagerRepairer ddr = new DefaultDamagerRepairer(new RustyScanner());
		pr.setRepairer(ddr, IDocument.DEFAULT_CONTENT_TYPE);
		pr.setDamager(ddr, IDocument.DEFAULT_CONTENT_TYPE);
		return pr;
	}
	
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant contentAssistant = new ContentAssistant();
		IContentAssistProcessor contentAssistProcessor = new RustyContentAssistProcessor();
		contentAssistant.enableAutoActivation(true);
		contentAssistant.setAutoActivationDelay(500);
		
		contentAssistant.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
		contentAssistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
		contentAssistant.setContextInformationPopupBackground(colorManager.getColor(new RGB(150, 150, 0)));
		
		contentAssistant.setContentAssistProcessor(contentAssistProcessor, IDocument.DEFAULT_CONTENT_TYPE);
		contentAssistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		
		return contentAssistant;
	}
	
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		return new RustyTextHover();
	}
	
}
