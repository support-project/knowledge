package org.support.project.web.logic.invoke;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.common.InvokeSearch;
import org.support.project.web.common.InvokeTarget;

@DI(instance = Instance.Singleton)
public class InvokeSearchEx extends InvokeSearch {
    /** ログ */
    private static Log LOG = LogFactory.getLog(MethodHandles.lookup());

    @Override
    protected InvokeTarget createInvokeTarget(Class<?> class1, Method method, String targetPackageName, String classSuffix) {
        InvokeTargetEx invokeTarget = new InvokeTargetEx(class1, method, targetPackageName, classSuffix, new LinkedHashMap<>());
        
        // Access情報をセット
        Close close = method.getAnnotation(Close.class);
        if (close != null) {
            LOG.info("            " + "AccessType: close");
            invokeTarget.setAccessType(AccessType.close);
        } else {
            CloseAble closeAble = method.getAnnotation(CloseAble.class);
            if (closeAble != null) {
                LOG.info("            " + "AccessType: closeAble");
                invokeTarget.setAccessType(AccessType.closeAble);
            } else {
                // Launchで起動すると、なぜかAnnotationが取得できない
                // → アノテーションクラスをKnowledgeからWebに移動したら取得できるようになった
                // → アノテーションは、ライブラリのプロジェクトで作成する
                LOG.info("            " + "AccessType: open");
                invokeTarget.setAccessType(AccessType.open);
            }
        }
        return invokeTarget;
    }
    
}
