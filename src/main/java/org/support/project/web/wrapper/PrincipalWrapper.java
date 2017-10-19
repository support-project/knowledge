package org.support.project.web.wrapper;

import java.security.Principal;

/**
 * Principalのラッパークラス
 * 
 * @author Koda
 *
 */
public class PrincipalWrapper implements Principal {
    /** 元 */
    private Principal principal;
    /** ユーザ名称 */
    private String userName;

    /**
     * コンストラクタ
     * 
     * @param principal principal
     * @param userName userName
     */
    public PrincipalWrapper(Principal principal, String userName) {
        super();
        this.principal = principal;
        this.userName = userName;
    }

    @Override
    public String getName() {
        return this.userName;
    }

}
