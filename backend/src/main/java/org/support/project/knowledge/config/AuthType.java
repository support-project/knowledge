package org.support.project.knowledge.config;

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
