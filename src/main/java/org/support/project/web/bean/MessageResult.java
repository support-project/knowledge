package org.support.project.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.support.project.web.config.MessageStatus;

/**
 * クライアントに返すメッセージのオブジェクト
 * 
 * @author koda
 *
 */
public class MessageResult implements Serializable {
    /**
     * serialVersion
     */
    private static final long serialVersionUID = 1L;
    /**
     * ステータス(org.support.project.web.config.MessageStatus)
     */
    private Integer status = 0;
    /**
     * コード(HttpStatus)
     */
    private Integer code = 0;
    /**
     * メッセージ
     */
    private String message;
    /**
     * 結果(何か返す値があれば/採番したIDなど)
     */
    private String result;
    /**
     * Constructor
     */
    public MessageResult() {
    }
    /**
     * Constructor
     * @param status ステータス(org.support.project.web.config.MessageStatus)
     * @param code コード(HttpStatus)
     * @param message メッセージ
     * @param result 結果(何か返す値があれば/採番したIDなど)
     */
    public MessageResult(MessageStatus status, Integer code, String message, String result) {
        super();
        this.status = status.getValue();
        this.code = code;
        this.message = message;
        this.result = result;
    }
    /**
     * 結果の中に、子のメッセージを持ちたい場合にセット
     */
    private List<MessageResult> children = new ArrayList<MessageResult>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return the children
     */
    public List<MessageResult> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<MessageResult> children) {
        this.children = children;
    }

}
