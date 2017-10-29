package org.support.project.knowledge.control.api;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;

@DI(instance = Instance.Prototype)
public class SampleControl extends Control {
    /**
     * サンプル
     */
    @Get(path="api/sample", publishToken="")
    public Boundary index() {
        Msg msg = new Msg("Hi, " + getLoginedUser().getLoginUser().getUserName()  + ", this is api sample.");
        return send(msg);
    }

}
