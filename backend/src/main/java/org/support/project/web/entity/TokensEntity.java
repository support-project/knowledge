package org.support.project.web.entity;

import org.support.project.web.entity.gen.GenTokensEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * 認証トークン
 */
@DI(instance = Instance.Prototype)
public class TokensEntity extends GenTokensEntity {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * Get instance from DI container.
     * @return instance
     */
    public static TokensEntity get() {
        return Container.getComp(TokensEntity.class);
    }

    /**
     * Constructor.
     */
    public TokensEntity() {
        super();
    }

    /**
     * Constructor
     * @param token TOKEN
     */

    public TokensEntity(String token) {
        super( token);
    }

}
