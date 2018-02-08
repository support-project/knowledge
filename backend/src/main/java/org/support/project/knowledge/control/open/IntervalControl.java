package org.support.project.knowledge.control.open;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public class IntervalControl extends org.support.project.web.control.Control {

    @Get
    public Boundary access() throws InvalidParamException {
        return send(new Msg("OK"));
    }

}
