package org.support.project.knowledge.tool;

import java.io.IOException;

import org.support.project.ormapping.tool.DaoMaker;
import org.support.project.ormapping.tool.DatabaseInitializer;
import org.support.project.ormapping.tool.EntityMaker;

public class KnowledgeDBGen {

    public static void main(String[] args) throws IOException, Exception {
        if (args.length == 0) {
            args = new String[1];
            args[0] = "/ormappingtool.xml";
        }

        DatabaseInitializer initializer = new DatabaseInitializer(args[0]);
        initializer.dropAllTable();

        DatabaseInitializer.main(args);
        EntityMaker.main(args);
        DaoMaker.main(args);
    }

}
