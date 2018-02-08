package org.support.project.knowledge.control.admin;

import org.support.project.knowledge.control.Control;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;

public class NoticeControl extends Control {
    /**
     * 通知一覧画面を表示
     * @return Boundary
     */
    @Get(publishToken = "admin")
    @Auth(roles = "admin")
    public Boundary list() {
        return forward("list.jsp");
    }
    
    
}
