package org.support.project.web.bean;

import java.io.Serializable;

/**
 * バッチ設定を保持するクラス
 * @author Koda
 */
public class Batchinfo implements Serializable {
    /** しいリアルバージョン */
    private static final long serialVersionUID = 1L;
    /**
     * バッチの名称
     */
    private String name;
    /**
     * バッチの種類
     * Normal: 普通のバッチ（コマンドラインで実行）
     * Java: 内部のJavaバッチプログラムを呼び出す
     */
    private String type;
    /**
     * 呼び出すコマンド
     * Normal: このコマンドをそのまま呼び出す
     * Java: クラス名がセットされるので、そのJavaプログラムが動くように呼び出す
     */
    private String command;
    /**
     * 呼び出し間隔
     */
    private Integer term;
    
    /** 呼び出し間隔の単位(MILLISECONDS,SECONDS,MINUTES,HOURS,DAYS) */
    private String timeUnit;
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }
    /**
     * @param command the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }
    /**
     * @return the term
     */
    public Integer getTerm() {
        return term;
    }
    /**
     * @param term the term to set
     */
    public void setTerm(Integer term) {
        this.term = term;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the timeUnit
     */
    public String getTimeUnit() {
        return timeUnit;
    }
    /**
     * @param timeUnit the timeUnit to set
     */
    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }
    
    
}
