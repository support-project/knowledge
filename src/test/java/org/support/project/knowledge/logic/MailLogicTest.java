package org.support.project.knowledge.logic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;

public class MailLogicTest {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MailLogicTest.class);

    @Test
    public void testGetMailContent() throws Exception {
        String html = FileUtil.read(this.getClass().getResourceAsStream("data/html.txt"));
        //LOG.info(html);
        String content = MailLogic.get().getMailContent(html);
        //LOG.info(content);
        String expected = "こんな情報を入手しました\n興味がある人は声をかけてください。\n \n";
        assertEquals(expected, content);
    }

}
