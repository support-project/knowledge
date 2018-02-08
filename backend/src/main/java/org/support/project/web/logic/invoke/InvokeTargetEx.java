package org.support.project.web.logic.invoke;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.support.project.web.common.InvokeTarget;

public class InvokeTargetEx extends InvokeTarget {
    
    private AccessType accessType = AccessType.open;
    
    public InvokeTargetEx(Class<?> targetClass, Method targetMethod, String targetPackageName,
            String classSuffix, Map<String, String> pathValue) {
        super(targetClass, targetMethod, targetPackageName, classSuffix, pathValue);
    }

    
    
    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }



    @Override
    public InvokeTarget copy() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>(getPathValue());
        InvokeTargetEx copy = new InvokeTargetEx(getTargetClass(), getTargetMethod(),
                getTargetPackageName(), getClassSuffix(), map);
        for (String role : getRoles()) {
            copy.addRole(role);
        }
        copy.setAccessType(accessType);
        return copy;
    }

}
