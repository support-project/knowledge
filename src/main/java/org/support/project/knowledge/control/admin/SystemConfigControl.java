package org.support.project.knowledge.control.admin;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.ormapping.config.ConnectionConfig;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.logic.DBConnenctionLogic;

@DI(instance = Instance.Prototype)
public class SystemConfigControl extends Control {

    @Get(publishToken = "admin")
    @Auth(roles = "admin")
    public Boundary index() {
        Boolean postgres = Boolean.FALSE;
        if (DBConnenctionLogic.get().getCustomConnectionConfig() != null) {
            ConnectionConfig config = DBConnenctionLogic.get().getCustomConnectionConfig();
            if (config.getDriverClass().indexOf("postgres") != -1) {
                postgres = Boolean.TRUE;
            }
        }
        setAttribute("postgres", postgres);
        return forward("index.jsp");
    }

}
