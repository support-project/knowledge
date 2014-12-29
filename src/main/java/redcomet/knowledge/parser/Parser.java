package redcomet.knowledge.parser;

import java.io.File;

import redcomet.knowledge.vo.ParseResult;

public interface Parser {

	/**
	 * 指定のファイルをパースし中身を取得する
	 * @param path
	 * @return
	 * @throws Exception
	 */
	ParseResult parse(File tmp) throws Exception;
	
	
}
