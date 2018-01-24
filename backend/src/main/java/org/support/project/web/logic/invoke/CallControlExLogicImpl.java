package org.support.project.web.logic.invoke;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.common.InvokeSearch;
import org.support.project.web.common.InvokeTarget;
import org.support.project.web.config.SystemConfigValue;
import org.support.project.web.logic.impl.CallControlLogicImpl;

@DI(instance = Instance.Singleton)
public class CallControlExLogicImpl extends CallControlLogicImpl {
    @Override
    protected InvokeSearch getInvokeSearch() {
        if (_invokeSearch == null) {
            _invokeSearch = Container.getComp(InvokeSearchEx.class);
        }
        return _invokeSearch;
    }

    @Override
    protected boolean auth(InvokeTarget invokeTarget, HttpServletRequest request, HttpServletResponse response, String path, String pathInfo) {
        boolean auth = super.auth(invokeTarget, request, response, path, pathInfo);
        if (!auth) {
            return auth;
        }
        if (invokeTarget instanceof InvokeTargetEx) {
            InvokeTargetEx ex = (InvokeTargetEx) invokeTarget;
            AccessType accessType = ex.getAccessType();
            if (AccessType.close == accessType) {
                AccessUser loginedUser = HttpUtil.getLoginedUser(request);
                if (loginedUser == null) {
                    return false;
                }
            } else if (AccessType.closeAble == accessType) {
                if (SystemConfigValue.get().isClose()) {
                    AccessUser loginedUser = HttpUtil.getLoginedUser(request);
                    if (loginedUser == null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
