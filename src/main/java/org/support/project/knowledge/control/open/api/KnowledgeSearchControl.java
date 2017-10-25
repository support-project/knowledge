package org.support.project.knowledge.control.open.api;

import org.support.project.web.bean.Msg;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.control.ApiControl;
import org.support.project.web.control.service.Get;

/**
 * Knowledgeの一覧を取得するためのAPI
 */
public class KnowledgeSearchControl extends ApiControl {

    @Get(path="open.api/knowledges", publishToken="")
    public JsonBoundary index() {
        return send(new Msg("aaa"));
    }


}
