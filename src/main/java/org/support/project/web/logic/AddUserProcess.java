package org.support.project.web.logic;

import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Singleton)
public interface AddUserProcess {
    void addUserProcess(String userKey);
}
