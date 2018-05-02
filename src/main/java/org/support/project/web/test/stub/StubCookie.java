package org.support.project.web.test.stub;

import javax.servlet.http.Cookie;

public class StubCookie extends Cookie {
    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;
    private String name;
    private String value;
    public StubCookie() {
        this("", "");
    }
    public StubCookie(String name, String value) {
        super(name, value);
        this.name = name;
        this.value = value;
    }

}
