package org.support.project.knowledge.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.support.project.common.util.FileUtil;

public class MailLogicTest {
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
