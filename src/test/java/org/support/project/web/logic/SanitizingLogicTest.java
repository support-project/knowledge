package org.support.project.web.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.test.Order;
import org.support.project.common.test.OrderedRunner;
import org.support.project.common.util.FileUtil;

@RunWith(OrderedRunner.class)
public class SanitizingLogicTest {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(SanitizingLogicTest.class);

    /**
     * 改行コードは無視して値比較をするために、文字列（Line）の配列で取得
     * 
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
    @Order(order = 1)
    public void testSanitize()
            throws ParseException, UnsupportedEncodingException, IOException, TransformerFactoryConfigurationError, TransformerException {
        String base = FileUtil.read(getClass().getResourceAsStream("sanitize/sanitize1.txt"));
        String check = FileUtil.read(getClass().getResourceAsStream("sanitize/result-sanitize1.txt"));
        String result = SanitizingLogic.get().sanitize(base);
        try {
            org.junit.Assert.assertArrayEquals(read(check), read(result));
        } catch (AssertionError e) {
            LOG.info("Sanitize");
            LOG.info("[Base] : " + base);
            LOG.info("[Check]     : " + check);
            LOG.info("[Result]   : " + result);
            throw e;
        }
    }
    
    @Test
    @Order(order = 2)
    public void testUNC() throws Exception {
        LOG.info("testUNC");
        String base = "<p><a href=\"\\\\hoge\\data\" title=\"UNCPathLink\">UNCPathLink</a></p>";
        String result = SanitizingLogic.get().sanitize(base);
        try {
            String check = "<p><a href=\"\\\\hoge\\data\" title=\"UNCPathLink\" rel=\"nofollow\">UNCPathLink</a></p>";
            org.junit.Assert.assertEquals(check, result);
        } catch (AssertionError e) {
            LOG.info("Sanitize");
            LOG.info("[Base]   : " + base);
            LOG.info("[Result] : " + result);
            throw e;
        }
    }
    
    @Test
    @Order(order = 3)
    public void testFile() throws Exception {
        LOG.info("testFile");
        String base = "<p><a href=\"file://hoge/data\" title=\"UNCPathLink\">UNCPathLink</a></p>";
        String result = SanitizingLogic.get().sanitize(base);
        try {
            String check = "<p><a href=\"file://hoge/data\" title=\"UNCPathLink\" rel=\"nofollow\">UNCPathLink</a></p>";
            org.junit.Assert.assertEquals(check, result);
        } catch (AssertionError e) {
            LOG.info("Sanitize");
            LOG.info("[Base]   : " + base);
            LOG.info("[Result] : " + result);
            throw e;
        }
    }
    
    @Test
    @Order(order = 4)
    public void testSamba() throws Exception {
        LOG.info("testSamba");
        String base = "<p><a href=\"smb://hoge/data\" title=\"UNCPathLink\">UNCPathLink</a></p>";
        String result = SanitizingLogic.get().sanitize(base);
        try {
            String check = "<p><a href=\"smb://hoge/data\" title=\"UNCPathLink\" rel=\"nofollow\">UNCPathLink</a></p>";
            org.junit.Assert.assertEquals(check, result);
        } catch (AssertionError e) {
            LOG.info("Sanitize");
            LOG.info("[Base]   : " + base);
            LOG.info("[Result] : " + result);
            throw e;
        }
    }
    
    @Test
    @Order(order = 5)
    public void testMathJax() throws Exception {
        LOG.info("testSamba");
        String base = "<pre><code>\\(ax^2 + bx + c = 0\\)</code></pre>";
        String result = SanitizingLogic.get().sanitize(base);
        try {
            String check = "<pre><code>\\(ax^2 &#43; bx &#43; c &#61; 0\\)</code></pre>";
            org.junit.Assert.assertEquals(check, result);
        } catch (AssertionError e) {
            LOG.info("Sanitize");
            LOG.info("[Base]   : " + base);
            LOG.info("[Result] : " + result);
            throw e;
        }
    }
    
    
    
}
