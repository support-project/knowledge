package org.support.project.knowledge.control.admin;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;

@DI(instance = Instance.Prototype)
public class SystemConfigControl extends Control {

    @Get
    @Auth(roles = "admin")
    public Boundary index() {
        return forward("index.jsp");
    }

}
