package org.support.project.di;

import org.support.project.di.DI;
import org.support.project.di.Instance;

@DI(instance = Instance.Prototype)
public class TestNoAspectObject {

    private String hoge;

    /**
     * hogeを取得します。
     * 
     * @return hoge
     */
    public String getHoge() {
        return hoge;
    }

    /**
     * hogeを設定します。
     * 
     * @param hoge
     *            hoge
     */
    public TestNoAspectObject setHoge(String hoge) {
        this.hoge = hoge;
        return this;
    }

}
