package org.support.project.knowledge.control.protect;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.logic.EventsLogic;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Delete;
import org.support.project.web.control.service.Put;
import org.support.project.web.exception.InvalidParamException;

@DI(instance = Instance.Prototype)
public class EventControl extends Control {
    
    /**
     * 指定のイベントに参加登録
     * @return
     * @throws InvalidParamException
     */
    @Put
    public Boundary participation() throws InvalidParamException {
        Long knowledgeId = getPathLong();
        Boolean result = EventsLogic.get().participation(knowledgeId, getLoginUserId());
        LabelValue labelValue = new LabelValue();
        labelValue.setValue(result.toString().toLowerCase());
        if (result) {
            labelValue.setLabel(getResource("knowledge.view.msg.participate"));
        } else {
            labelValue.setLabel(getResource("knowledge.view.msg.wait.cansel"));
        }
        return send(labelValue);
    }
    /**
     * 指定のイベントの参加をキャンセル
     * @return
     * @throws InvalidParamException
     */
    @Delete
    public Boundary nonparticipation() throws InvalidParamException {
        Long knowledgeId = getPathLong();
        EventsLogic.get().removeParticipation(knowledgeId, getLoginUserId());
        return send(getResource("knowledge.view.msg.participate.delete"));
    }

}
