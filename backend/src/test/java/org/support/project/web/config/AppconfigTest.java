package org.support.project.web.config;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.support.project.web.bean.Batchinfo;
import org.support.project.web.bean.LabelValue;

public class AppconfigTest {

    public void test() {
        assertEquals("web", AppConfig.get().getSystemName());
        
        List<LabelValue> languages = AppConfig.get().getLanguages();
        assertEquals(2, languages.size());
        
        List<Batchinfo> batchs = AppConfig.get().getBatchs();
        assertEquals(2, batchs.size());
        
        assertEquals("Java", AppConfig.get().getBatchs().get(0).getType());
        assertEquals("org.support.project.web.batch.TestBatch", AppConfig.get().getBatchs().get(0).getCommand());
        assertEquals(5, AppConfig.get().getBatchs().get(0).getTerm().intValue());
        
        assertEquals("APP_HOME", AppConfig.getEnvKey());
        
    }

}
