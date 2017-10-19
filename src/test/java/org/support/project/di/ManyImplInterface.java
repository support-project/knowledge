package org.support.project.di;

import org.support.project.di.DI;

@DI(keys = { "1", "2" }, impls = { ManyImplInterfaceImpl1.class, ManyImplInterfaceImpl2.class })
public interface ManyImplInterface {
    String value();
}
