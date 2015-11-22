package org.support.project.knowledge.logic;

import io.github.gitbucket.markedj.Marked;
import io.github.gitbucket.markedj.Options;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Date;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
import org.support.project.knowledge.vo.MarkDown;
import org.support.project.web.logic.SanitizingLogic;

@DI(instance = Instance.Singleton)
public class MarkdownLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(MarkdownLogic.class);
	
	public static final int ENGINE_PEGDOWN = 1;
	public static final int ENGINE_MARKEDJS = 2;
	public static final int ENGINE_MARKEDJ = 3; // スライド表示用の独自拡張（デフォルト）
	
	private ScriptEngine engine = null;
	private boolean initEngine = false;
	
	public static MarkdownLogic get() {
		return Container.getComp(MarkdownLogic.class);
	}
	
	/**
	 * マークダウンをパースしてHTMLを取得
	 * @param markdown
	 * @return
	 * @throws ParseException
	 */
	public MarkDown markdownToHtml(String markdown) throws ParseException {
		return markdownToHtml(markdown, ENGINE_MARKEDJ);
	}
	/**
	 * マークダウンをパースしてHTMLを取得(エンジンを指定)
	 * @param markdown
	 * @param engine
	 * @return
	 * @throws ParseException
	 */
	public MarkDown markdownToHtml(String markdown, int engine) throws ParseException {
		MarkDown result = new MarkDown();
		if (engine == ENGINE_MARKEDJS) {
			LOG.warn("marked.js parser was deprecated");
			markdownToHtmlOnMarkedJs(markdown, result);
		} else if (engine == ENGINE_PEGDOWN) {
			LOG.warn("PegDown parser was deprecated");
			markdownToHtmlOnPegDown(markdown, result);
		} else {
			markdownToHtmlOnMarkedJ(markdown, result);
		}
		return sanitize(markdown, result);
	}

	/**
	 * サニタイジング
	 * @param markdown
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	private MarkDown sanitize(String markdown, MarkDown result) throws ParseException {
		try {
			String html = result.getHtml();
			if (LOG.isDebugEnabled()) {
				LOG.debug("[Markdown]     : " + markdown);
				LOG.debug("[ParsedHtml]   : " + html);
			}
			// 危険なHTMLは削除
			html = SanitizingLogic.get().sanitize(html);
			if (LOG.isDebugEnabled()) {
				LOG.debug("[SanitizeHtml] : " + html);
			}
			result.setHtml(html);
			return result;
		} catch (Exception e) {
			// サニタイズではエラーにならないはず
			throw new ParseException(e);
		}
	}
	
	/**
	 * support-project/markedj (https://github.com/support-project/markedj) でマークダウンをパース
	 * スライド表示の独自拡張を行っている
	 * 
	 * @param markdown
	 * @param result
	 */
	private void markdownToHtmlOnMarkedJ(String markdown, MarkDown result) {
		Date start = new Date();

		Options options = new Options();
		options.setBreaks(true);
		options.setLinkTargetBlank(true);

		String html = Marked.marked(markdown, options);
		result.setHtml(html);
		result.setParsed(true);
		result.setMarkdown(markdown);

		if (LOG.isDebugEnabled()) {
			Date end = new Date();
			// 以前、別のMarkdownパーサーのパースが凄く時間がかかったので、パース時間を出力している
			LOG.debug("Parse time (MarkedJ): " + (end.getTime() - start.getTime()) + " [ms]");
		}
	}
	
	
	/**
	 * PegDownでMarkDownのパース
	 * @param markdown
	 * @param result
	 */
	private void markdownToHtmlOnPegDown(String markdown, MarkDown result) {
		Date start = new Date();
		result.setMarkdown(markdown);
		String html = markdown;
		try {
			// Markdownのパース
			PegDownProcessor processor = new PegDownProcessor(Extensions.ALL - Extensions.ANCHORLINKS);
			html = processor.markdownToHtml(markdown, new LinkRenderer() {
				@Override
				public Rendering render(AnchorLinkNode node) {
					return new Rendering(node.getText(), node.getText());
				}
			});
			result.setHtml(html);
			result.setParsed(true);
			if (LOG.isDebugEnabled()) {
				Date end = new Date();
				LOG.debug("Parse time (PegDown): " + (end.getTime() - start.getTime()) + " [ms]");
			}
		} catch (Exception e) {
			// Markdownのパースに失敗する事がある
			LOG.error("Markdown parse error.", e);
			// PegDownをデフォルトとして、失敗した場合、Marked.jsでパースしてみる
			markdownToHtmlOnMarkedJs(markdown, result);
		}
	}
	
	private ScriptEngine getScriptEngine() throws ScriptException, IOException {
		if (initEngine) {
			return engine;
		}
		ScriptEngineManager manager = new ScriptEngineManager();
		engine = manager.getEngineByName("js");
		if (engine == null) {
			System.out.println("JavaScriptはサポート外");
			initEngine = true;
			return null;
		}
		Reader fr = null;
		try {
			fr = new InputStreamReader(this.getClass().getResourceAsStream("/org/support/project/knowledge/logic/marked.js"), Charset.forName("UTF-8"));
			engine.eval(fr);
			initEngine = true;
			return engine;
		} finally {
			if (fr != null) {
				fr.close();
			}
		}
	}
	
	
	/**
	 * marked.jsでMarkDownのパース
	 * @param markdown
	 * @param result
	 */
	private void markdownToHtmlOnMarkedJs(String markdown, MarkDown result) {
		Date start = new Date();
		result.setMarkdown(markdown);
		String html = markdown;
		try {
			engine = getScriptEngine();
			if (engine == null) {
				return;
			}
			engine.put("content", markdown);
			html = (String) engine.eval("marked(content)");
			result.setHtml(html);
			result.setParsed(true);
			if (LOG.isDebugEnabled()) {
				Date end = new Date();
				LOG.debug("Parse time (marked.js): " + (end.getTime() - start.getTime()) + " [ms]");
			}
		} catch (Exception e) {
			LOG.error("Markdown parse error.", e);
		}
	}
	
	
}
