package org.support.project.common.classanalysis;

import org.support.project.common.classanalysis.impl.ClassSearchImpl;
import org.support.project.common.exception.SystemException;
import org.support.project.di.DI;

/**
 * 指定箇所に格納されているクラスを検索
 */
@DI(impl = ClassSearchImpl.class)
public interface ClassSearch {

    /**
     * 指定パッケージ内に含まれるクラスを取得
     * 
     * @param packageName
     *            パッケージ名
     * @param subpackages
     *            サブパッケージを含めるか(trueで含める)
     * @return クラスの一覧
     * @throws SystemException
     *             SystemException
     */
    Class<?>[] classSearch(String packageName, boolean subpackages) throws SystemException;

}
