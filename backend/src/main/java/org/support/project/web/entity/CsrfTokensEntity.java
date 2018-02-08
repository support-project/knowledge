package org.support.project.web.entity;

import org.support.project.web.entity.gen.GenCsrfTokensEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * CSRF_TOKENS
 */
@DI(instance = Instance.Prototype)
public class CsrfTokensEntity extends GenCsrfTokensEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static CsrfTokensEntity get() {
        return Container.getComp(CsrfTokensEntity.class);
    }

    /**
     * Constructor.
     */
    public CsrfTokensEntity() {
        super();
    }

    /**
     * Constructor
     * @param processName 処理名
     * @param userId ユーザID
     */

    public CsrfTokensEntity(String processName, Integer userId) {
        super( processName,  userId);
    }

}
