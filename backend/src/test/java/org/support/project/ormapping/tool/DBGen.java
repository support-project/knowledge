package org.support.project.ormapping.tool;

import java.io.IOException;

import org.support.project.ormapping.tool.DaoMaker;
import org.support.project.ormapping.tool.DatabaseInitializer;
import org.support.project.ormapping.tool.EntityMaker;

public class DBGen {

	public static void main(String[] args) throws IOException, Exception {
		DatabaseInitializer.main(args);
		EntityMaker.main(args);
		DaoMaker.main(args);
	}

}
