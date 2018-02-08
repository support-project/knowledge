package org.support.project.web.filter;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.exception.ExceptionHandler;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.InvokeTarget;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.InvalidParamException;

public class ControlFilter extends ControlManagerFilter {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());

    @Override
    protected void invoke(InvokeTarget invokeTarget, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Object result = invokeTarget.invoke();
            if (result instanceof Boundary) {
                Boundary boundary = (Boundary) result;
                if (boundary instanceof JsonBoundary) {
                    JsonBoundary json = (JsonBoundary) boundary;
                    Object send = json.getObj();

                    if (sendPasswordCheck(send)) {
                        log.error("password send! " + invokeTarget.getTargetClass().getName() + "#" + invokeTarget.getTargetMethod().getName());
                        MessageResult messageResult = new MessageResult();
                        messageResult.setStatus(MessageStatus.Error.getValue());
                        messageResult.setCode(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);

                        json = new JsonBoundary(messageResult);
                        json.setRequest(request);
                        json.setResponse(response);
                        json.navigate();
                    }
                }
                boundary.navigate();
            }
        } catch (Exception e) {
            InvalidParamException ex = ExceptionHandler.exceptionSearch(e, InvalidParamException.class);
            if (ex != null) {
                handleBadRequest(request, response, ex);
                return;
            }
            if (JsonBoundary.class.isAssignableFrom(invokeTarget.getTargetMethod().getReturnType())) {
                log.error("exception is thrown.", e);
                
                MessageResult messageResult = new MessageResult();
                messageResult.setMessage("Internal server error");
                messageResult.setStatus(MessageStatus.Error.getValue());
                messageResult.setCode(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
                JsonBoundary boundary = new JsonBoundary(messageResult);
                boundary.setRequest(request);
                boundary.setResponse(response);
                boundary.navigate();
            } else {
                throw e;
            }
        }
    }
    


    protected void handleBadRequest(HttpServletRequest request, HttpServletResponse response, InvalidParamException exception) throws Exception {
        MessageResult messageResult = exception.getMessageResult();
        if (messageResult.getStatus() > MessageStatus.Success.getValue()) {
            if (messageResult.getCode() == null) {
                messageResult.setStatus(MessageStatus.Error.getValue());
                messageResult.setCode(HttpStatus.SC_400_BAD_REQUEST);
            }
        }
        JsonBoundary boundary = new JsonBoundary(messageResult);
        response.setStatus(exception.getMessageResult().getStatus());
        boundary.setRequest(request);
        boundary.setResponse(response);
        boundary.navigate();
    }

    /**
     * パスワードを送っていないかチェック
     * 
     * @param send
     * @return
     */
    private boolean sendPasswordCheck(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof UsersEntity) {
            UsersEntity entity = (UsersEntity) obj;
            if (StringUtils.isNotEmpty(entity.getPassword()) && !entity.getPassword().equals(CommonWebParameter.PASSWORD_NO_EDIT)) {
                log.error("The password must not be sent.");
                return true;
            }
        }

        if (!PropertyUtil.isValueClass(obj.getClass())) {
            List<String> props = PropertyUtil.getPropertyNames(obj);
            for (String prop : props) {
                Object child = PropertyUtil.getPropertyValue(obj, prop);
                if (sendPasswordCheck(child)) {
                    return true;
                }
            }
        }
        return false;
    }
}
