package org.support.project.knowledge.config;

public enum MailHookCondition {
    Recipient, Title;

    public int getValue() {
        return ordinal() + 1;
    }
    
}
