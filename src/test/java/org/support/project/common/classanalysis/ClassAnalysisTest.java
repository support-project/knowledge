package org.support.project.common.classanalysis;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.support.project.common.classanalysis.ClassAnalysis;

public class ClassAnalysisTest {

    @Test
    public void test() {
        ClassAnalysis analysis = new ClassAnalysis(ClassAnalysisTestTarget.class);

        List<String> propertyNames = analysis.getPropertyNames();
        assertEquals("[プロパティ名の個数]", 7, propertyNames.size());

    }

}
