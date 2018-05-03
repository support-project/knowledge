package org.support.project.web.config;

public enum AuthType {
    None, Basic, Digest, NTLM, Samba, Form;

    public int getValue() {
        return ordinal();
    }

    public static AuthType getType(int type) {
        AuthType[] values = values();
        return values[type];
    }

}
