package org.support.project.knowledge.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.junit.Test;
import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;

public class SanitizeMarkdownTextLogicTest {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /**
     * 改行コードは無視して値比較をするために、文字列（Line）の配列で取得
     * @param str
     * @return
     * @throws IOException
     */
    private String[] read(String str) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new StringReader(str));
            List<String> list = new ArrayList<String>();
            String s;
            while ((s = reader.readLine()) != null) {
                list.add(s);
            }
            return list.toArray(new String[0]);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }    
    
    @Test
    public void test() throws UnsupportedEncodingException, IOException, TransformerFactoryConfigurationError, TransformerException, ParseException {
        String markdown = FileUtil.read(getClass().getResourceAsStream("markdown/sanitize-raw-1.md"));
        String expecteds = FileUtil.read(getClass().getResourceAsStream("markdown/sanitize-result-1.md"));
        String result = SanitizeMarkdownTextLogic.get().sanitize(markdown);
        try {
            org.junit.Assert.assertArrayEquals(read(expecteds), read(result));
        } catch (AssertionError e) {
            LOG.info("[Markdown]   : \n" + markdown);
            LOG.info("[Expecteds]  : \n" + expecteds);
            LOG.warn("[Result]     : \n" + result);
            throw e;
        }
    }

}
