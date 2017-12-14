package org.support.project.knowledge.parser;

import java.io.File;

import org.support.project.common.exception.ParseException;

public interface SlideShowParser {
    /**
     * ファイルからスライドショウの画像の一覧を生成する
     * @param inputFile
     * @param outputDir
     * @throws ParseException
     */
    void parse(File inputFile, File outputDir) throws ParseException;
    
}
