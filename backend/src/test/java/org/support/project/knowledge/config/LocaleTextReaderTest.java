package org.support.project.knowledge.config;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;
import org.support.project.common.util.FileUtil;
import org.support.project.knowledge.TestCommon;

public class LocaleTextReaderTest {
    private static final String path = "/org/support/project/knowledge/markdown/sample_markdown.md";
    private static final String path_ja = "/org/support/project/knowledge/markdown/sample_markdown_ja.md";
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AppConfig.initEnvKey(TestCommon.KNOWLEDGE_TEST_HOME);
    }
    
    @Test
    public void testRead() throws UnsupportedEncodingException, IOException {
        LocaleTextReader localeTextReader = LocaleTextReader.get();
        String text = localeTextReader.read(path, Locale.JAPAN);
        String check = FileUtil.read(getClass().getResourceAsStream(path_ja));
        assertEquals(text, check);
    }

    @Test
    public void testRead2() throws UnsupportedEncodingException, IOException {
        LocaleTextReader localeTextReader = LocaleTextReader.get();
        String text = localeTextReader.read(path, Locale.JAPANESE);
        String check = FileUtil.read(getClass().getResourceAsStream(path_ja));
        assertEquals(text, check);
    }
    
    @Test
    public void testRead3() throws UnsupportedEncodingException, IOException {
        LocaleTextReader localeTextReader = LocaleTextReader.get();
        String text = localeTextReader.read(path, Locale.ENGLISH);
        String check = FileUtil.read(getClass().getResourceAsStream(path));
        assertEquals(text, check);
    }
    
    @Test
    public void testRead4() throws UnsupportedEncodingException, IOException {
        LocaleTextReader localeTextReader = LocaleTextReader.get();
        String text = localeTextReader.read(path, Locale.FRANCE);
        String check = FileUtil.read(getClass().getResourceAsStream(path));
        assertEquals(text, check);
    }
    
    
}
