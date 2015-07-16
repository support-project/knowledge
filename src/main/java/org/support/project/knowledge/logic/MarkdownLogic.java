package org.support.project.knowledge.logic;

import org.pegdown.Extensions;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.AnchorLinkNode;
import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.logic.SanitizingLogic;

@DI(instance=Instance.Singleton)
public class MarkdownLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(MarkdownLogic.class);
	public static MarkdownLogic get() {
		return Container.getComp(MarkdownLogic.class);
	}
	
	public String markdownToHtml(String markdown) throws ParseException {
		try {
			// Markdownのパース
			PegDownProcessor processor = new PegDownProcessor(Extensions.ALL - Extensions.ANCHORLINKS);
			String html = processor.markdownToHtml(markdown, new LinkRenderer() {
				@Override
				public Rendering render(AnchorLinkNode node) {
					return new Rendering(node.getText(), node.getText());
				}
			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("[Markdown]     : " + markdown);
				LOG.debug("[ParsedHtml]   : " + html);
			}
			// 危険なHTMLは削除
			html = SanitizingLogic.get().sanitize(html);
			if (LOG.isDebugEnabled()) {
				LOG.debug("[SanitizeHtml] : " + html);
			}
			return html;
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}
	
}
