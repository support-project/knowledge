package org.support.project.ormapping.tool;

/**
 * DBを初期化し、Entity/Daoを生成
 * @author koda
 *
 */
public class ORmappingTool {

	public static void main(String[] args) throws Exception {
		DatabaseInitializer.main(args);
		EntityMaker.main(args);
		DaoMaker.main(args);
	}

}
