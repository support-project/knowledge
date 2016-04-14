package org.support.project.knowledge.control.open;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;

@DI(instance = Instance.Prototype)
public class NoticeControl extends Control {

    /**
     * 通知一覧画面を表示
     * 
     * @return Boundary
     */
    @Get
    public Boundary list() {
        return forward("list.jsp");
    }

}
