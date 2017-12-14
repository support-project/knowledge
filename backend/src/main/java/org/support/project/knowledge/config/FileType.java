package org.support.project.knowledge.config;

public enum FileType {
    NotCovered, // 対象外(パースしない)
    Other, // その他(Tikaでパースしてみる)
    Text, Word, Excel, PowerPoint, HTML, PDF, Java;

    public int getValue() {
        return ordinal();
    }

    public static FileType getType(int type) {
        FileType[] values = values();
        return values[type];
    }

}
