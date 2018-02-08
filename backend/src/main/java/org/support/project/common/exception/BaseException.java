package org.support.project.common.exception;

import org.support.project.common.config.CommonBaseParameter;
import org.support.project.common.config.Resources;
import org.support.project.common.util.StringUtils;

/**
 * Exceptionの基底クラス
 * 
 * 比較的規模の大きい開発では、開発に参加するすべてのプログラマのレベルを高く保つことは困難だと思います。
 * そのため、Javaをはじめたばかりのプログラマの書いたメソッドによって、スローされるべきExceptionがcatchされ
 * 握りつぶされてしまい本来の例外処理までExceptionが届かず、システムに不具合が起きたときの原因追求が 困難だったというのはよくある例です。
 * 
 * 特にEclipseのようなIDE（統合開発環境）を利用すればExceptionのtry-catch文がないことを警告し、try-catch文の テンプレートなどを自動生成してくれますが、
 * 自動生成されたtry-catch文をそのままにしておけばExceptionは
 * そこで握りつぶされてしまいます。
 * 
 * 最近の傾向としては、Exceptionを握りつぶされないようにするため、Exceptionは実行時例外（RuntimeException）を
 * 継承したものを利用するようにして、開発中は一般の開発者に例外を意識させないようにクラスを実装させ、一部の
 * 開発者のみが例外処理をまとめて行うクラスを実装するのが流行になっています。
 * 
 * エラーメッセージの内容は、リソースファイルから取得します
 *
 */
public abstract class BaseException extends RuntimeException {
    /**
     * シリアルバージョン
     */
    private static final long serialVersionUID = 1L;
    /**
     * メッセージのキー
     */
    protected String key;
    /**
     * メッセージ取得の際のパラメータ
     */
    protected String[] params;
    /**
     * cause
     */
    protected Throwable cause;

    /**
     * エラーメッセージを取得するリソースファイル
     */
    protected Resources resources;

    /**
     * メッセージ取得のリソースファイル
     * 
     * @return message resources
     */
    protected Resources getResources() {
        if (resources == null) {
            resources = Resources.getInstance(CommonBaseParameter.APP_RESOURCE);
        }
        return resources;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        if (key != null) {
            builder.append(getResources().getResource(key, params));
        }
        if (cause != null && StringUtils.isNotEmpty(cause.getMessage())) {
            builder.append(" [cause] ").append(cause.getMessage());
        }
        return builder.toString();
    }

    @Override
    public String getLocalizedMessage() {
        StringBuilder builder = new StringBuilder();
        if (key != null) {
            builder.append(getResources().getResource(key, params));
        }
        if (cause != null && StringUtils.isNotEmpty(cause.getLocalizedMessage())) {
            builder.append(" [cause] ").append(cause.getLocalizedMessage());
        }
        return builder.toString();
    }

    /**
     * コンストラクタ
     */
    public BaseException() {
        this.key = this.getClass().getName();
    }

    /**
     * コンストラクタ
     * 
     * @param resources resources
     * @param key key
     * @param cause cause
     * @param params params
     */
    public BaseException(Resources resources, String key, Throwable cause, String... params) {
        super(key, cause);
        this.key = key;
        this.params = params;
        this.cause = cause;
        this.resources = resources;
    }

    /**
     * コンストラクタ
     * 
     * @param key key
     * @param cause cause
     * @param params params
     */
    public BaseException(String key, Throwable cause, String... params) {
        this(null, key, cause, params);
    }

    /**
     * コンストラクタ
     * 
     * @param key key
     * @param cause cause
     */
    public BaseException(String key, Throwable cause) {
        this(null, key, cause, null);
    }

    /**
     * コンストラクタ
     * 
     * @param key key
     */
    public BaseException(String key) {
        this(null, key, null, null);
    }

    /**
     * コンストラクタ
     * 
     * @param cause cause
     */
    public BaseException(Throwable cause) {
        this(null, cause.getLocalizedMessage(), cause, null);
    }

    /**
     * コンストラクタ
     * 
     * @param key key
     * @param params params
     */
    public BaseException(String key, String... params) {
        this(null, key, null, params);
    }

    /**
     * コンストラクタ
     * 
     * @param resources resources
     * @param key key
     * @param cause cause
     */
    public BaseException(Resources resources, String key, Throwable cause) {
        this(resources, key, cause, null);
    }

    /**
     * コンストラクタ
     * 
     * @param resources resources
     * @param key key
     */
    public BaseException(Resources resources, String key) {
        this(resources, key, null, null);
    }

    /**
     * コンストラクタ
     * 
     * @param resources resources
     * @param key key
     * @param params params
     */
    public BaseException(Resources resources, String key, String... params) {
        this(resources, key, null, params);
    }
}
