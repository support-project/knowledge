package org.support.project.knowledge.control.admin;

import java.util.List;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.control.Control;
import org.support.project.knowledge.entity.ServiceConfigsEntity;
import org.support.project.knowledge.logic.ServiceConfigLogic;
import org.support.project.web.annotation.Auth;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;

@DI(instance = Instance.Prototype)
public class CustomServiceControl extends Control {
    
    /**
     * ユーザ登録方法設定画面を表示
     * 
     * @return
     */
    @Get(publishToken = "custom")
    @Auth(roles = "admin")
    public Boundary config() {
        return forward("config.jsp");
    }
    /**
     * ユーザ登録方法設定を保存
     * 
     * @return
     */
    @Post(subscribeToken = "custom", checkReqToken=true)
    @Auth(roles = "admin")
    public Boundary save() {
        ServiceConfigsEntity configsEntity = getParams(ServiceConfigsEntity.class);
        configsEntity.setServiceName(AppConfig.get().getSystemName());
        List<ValidateError> errors = configsEntity.validate();
        if (!errors.isEmpty()) {
            setResult(null, errors);
            return forward("config.jsp");
        }
        
        String enPage = getParam("enPage");
        String jaPage = getParam("jaPage");
        
        ServiceConfigLogic.get().saveConfig(getLoginedUser(), configsEntity, enPage, jaPage);
        
        // 保存した情報を読み出す
        ServiceConfigLogic.get().load();
        
        String successMsg = "message.success.save";
        setResult(successMsg, errors);
        
        return sendMsg(MessageStatus.Success, HttpStatus.SC_200_OK,
                "success", "message.success.insert");
    }
    
    
}
