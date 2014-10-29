package org.rustyclipse.ui.editors;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;

public class RustyScanner extends RuleBasedScanner {

	Token keyword;
	Token comment;
	Token string;
	
	private String[] keywords = {
								"as",
								"break",
								"crate",
								"else", "enum", "extern",
								"false", "fn", "for",
								"if", "impl", "in",
								"let", "loop",
								"match", "mod", "move", "mut",
								"priv", "proc", "pub",
								"ref", "return",
								"self", "static", "struct", "super",
								"true", "trait", "type",
								"unsafe", "use",
								"while"
								};
	
	public RustyScanner() {
		WordRule rule = new WordRule(new RustWordDetector());
		
		keyword = new Token(new TextAttribute(RustyColorConstants.KEYWORD, null, SWT.BOLD));
		string = new Token(new TextAttribute(RustyColorConstants.STRING));
		comment = new Token(new TextAttribute(RustyColorConstants.COMMENT));
		
		for(String s: keywords) {
			rule.addWord(s, keyword);
		}
		
		setRules(new IRule[] {
			rule,
			new SingleLineRule("/", "/", comment),
			new SingleLineRule("\"", "\"", string, '\\'),
			new SingleLineRule("'", "'", string, '\\'),
			new SingleLineRule("#[", "]", comment),
			new SingleLineRule("#![", "]", string),
			new WhitespaceRule(new IWhitespaceDetector() {
				@Override
				public boolean isWhitespace(char c) {
					return Character.isWhitespace(c);
				}
			})	
		});
	}
	
}
