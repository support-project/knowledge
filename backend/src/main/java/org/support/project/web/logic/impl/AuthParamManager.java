package org.support.project.web.logic.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

import org.support.project.common.exception.SerializeException;
import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.serialize.SerializeUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Singleton)
public class AuthParamManager {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    private String authConfig = "/auth.xml";

    private AuthParam param = null;

    public AuthParamManager() {
        init(authConfig);
    }

    public void init(String authConfig) {
        LOG.debug("Config loaded" + authConfig);
        InputStream in = null;
        try {
            try {
                in = AuthParamManager.class.getResourceAsStream(authConfig);
                param = SerializeUtils.bytesToObject(in, AuthParam.class);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (SerializeException | IOException e) {
            throw new SystemException("config load error");
        }
    }

    public AuthParam getParam() {
        if (param == null) {
            throw new SystemException("config not loaded.");
        }
        return param;
    }


}
