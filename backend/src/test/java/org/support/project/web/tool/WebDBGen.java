package org.support.project.web.tool;

import java.io.IOException;

import org.support.project.ormapping.tool.DaoMaker;
import org.support.project.ormapping.tool.DatabaseInitializer;
import org.support.project.ormapping.tool.EntityMaker;

public class WebDBGen {
    public static void main(String[] args) throws IOException, Exception {
        
        if (args.length == 0) {
            args = new String[1];
            args[0] = "/ormappingtool.xml";
        }
        
        DatabaseInitializer.main(args);
        EntityMaker.main(args);
        DaoMaker.main(args);
    }

}
