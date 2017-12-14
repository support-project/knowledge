package org.support.project.knowledge.parser;

import java.io.File;

import org.support.project.knowledge.vo.ParseResult;

public interface Parser {

    /**
     * 指定のファイルをパースし中身を取得する
     * 
     * @param path
     * @return
     * @throws Exception
     */
    ParseResult parse(File tmp) throws Exception;

}
