package org.support.project.common.classanalysis;

/**
 * ジェネリクス(型総称)から、そのクラスを無理やり取得する
 * 
 * @author Koda
 * @param <E>
 *            type
 */
@Deprecated
public class GenericAnalysis<E> {
    /**
     * type
     */
    private Class<E> type;

    /**
     * constractor
     * 
     * @param e
     *            type
     */
    public GenericAnalysis(E... e) {
        @SuppressWarnings("unchecked")
        Class<E> type = (Class<E>) e.getClass().getComponentType();
        this.type = type;
    }

    /**
     * get type
     * 
     * @return type
     */
    public Class<E> getType() {
        return type;
    }
}
