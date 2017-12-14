package org.support.project.knowledge.control.admin;

import java.util.List;

import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.entity.MailTemplatesEntity;
import org.support.project.knowledge.logic.MailLogic;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;

@DI(instance = Instance.Prototype)
public class MailTemplateControl extends Control {
    /**
     * メールテンプレート編集画面へ遷移
     * @return Boundary
     */
    @Get(publishToken = "mailtemplate")
    @Auth(roles = "admin")
    public Boundary index() {
        return forward("index.jsp");
    }
    
    /**
     * メールテンプレート情報を取得
     * @return Boundary
     */
    @Get()
    @Auth(roles = "admin")
    public Boundary list() {
        List<MailTemplatesEntity> templates = MailLogic.get().selectAll(getLocale());
        return send(templates);
    }
    
    
    /**
     * メールテンプレート情報を初期化
     * @return Boundary
     */
    @Post(subscribeToken = "mailtemplate", checkReferer=true, checkReqToken=true)
    @Auth(roles = "admin")
    public Boundary save() {
        String templateId = getParam("templateId");
        String en_title = getParam("en_title");
        String en_content = getParam("en_content");
        String ja_title = getParam("ja_title");
        String ja_content = getParam("ja_content");
        
        MailLogic.get().save(templateId, en_title, en_content, ja_title, ja_content);
        
        return send(getResource("message.success.save"));
    }
    
    
    /**
     * メールテンプレート情報を初期化
     * @return Boundary
     */
    @Post(subscribeToken = "mailtemplate", checkReferer=true, checkReqToken=true)
    @Auth(roles = "admin")
    public Boundary initialize() {
        String templateId = getParam("templateId");
        MailLogic.get().initialize(templateId);
        return send(getResource("message.success.process"));
    }
    
    
}
