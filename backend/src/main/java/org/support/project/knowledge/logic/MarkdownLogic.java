package org.support.project.knowledge.logic;

import io.github.gitbucket.markedj.Marked;
import io.github.gitbucket.markedj.Options;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
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
import org.support.project.common.util.DateUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.vo.MarkDown;
import org.support.project.web.logic.SanitizingLogic;

@DI(instance = Instance.Singleton)
public class MarkdownLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

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
     * 
     * @param markdown
     * @return
     * @throws ParseException
     */
    public MarkDown markdownToHtml(String markdown) throws ParseException {
        return markdownToHtml(markdown, ENGINE_MARKEDJ);
    }

    /**
     * マークダウンをパースしてHTMLを取得(エンジンを指定)
     * 
     * @param markdown
     * @param engine
     * @return
     * @throws ParseException
     */
    public MarkDown markdownToHtml(String markdown, int engine) throws ParseException {
        MarkDown result = new MarkDown();
        if (engine == ENGINE_MARKEDJS) {
            LOG.error("marked.js parser was deprecated");
        } else if (engine == ENGINE_PEGDOWN) {
            LOG.error("PegDown parser was deprecated");
        }
        markdownToHtmlOnMarkedJ(markdown, result);
        return sanitize(markdown, result);
    }

    /**
     * サニタイジング
     * 
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
     * support-project/markedj (https://github.com/support-project/markedj) でマークダウンをパース スライド表示の独自拡張を行っている
     * 
     * @param markdown
     * @param result
     */
    private void markdownToHtmlOnMarkedJ(String markdown, MarkDown result) {
        Date start = DateUtils.now();

        Options options = new Options();
        options.setBreaks(true);
        options.setLinkTargetBlank(true);
        options.setHeaderPrefix("markdown-agenda-");
        options.setHeaderIdSequential(true);

        String html = Marked.marked(markdown, options);
        result.setHtml(html);
        result.setParsed(true);
        result.setMarkdown(markdown);

        if (LOG.isDebugEnabled()) {
            Date end = DateUtils.now();
            // 以前、別のMarkdownパーサーのパースが凄く時間がかかったので、パース時間を出力している
            LOG.debug("Parse time (MarkedJ): " + (end.getTime() - start.getTime()) + " [ms]");
        }
    }

}
