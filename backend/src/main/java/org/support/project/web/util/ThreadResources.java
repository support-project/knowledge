package org.support.project.web.util;

import java.lang.invoke.MethodHandles;
import java.util.Locale;

import org.support.project.common.config.Resources;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.AccessUser;
import org.support.project.web.config.CommonWebParameter;

/**
 * Thread = リクエスト 単位でリソースを切り替える必要があるので、それの制御を楽にするためのクラス
 * 
 * @author Koda
 *
 */
@DI(instance = Instance.Singleton)
public class ThreadResources {
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    public static ThreadResources get() {
        return Container.getComp(ThreadResources.class);
    }

    public Resources getResources() {
        AccessUser loginedUser = (AccessUser) ThredUserPool.get().getInfo(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY);
        if (loginedUser != null && loginedUser.getLocale() != null) {
            return Resources.getInstance(loginedUser.getLocale());
        }
        return Resources.getInstance(Locale.getDefault());
    }

}
