package org.support.project.ormapping.common;

import java.util.HashMap;
import java.util.Map;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * Thread毎にDBの実行ユーザを格納しておくプール Daoのinsert/updateで実行ユーザIDはここから取得する
 * 
 * Webアプリは、リクエスト毎のマルチスレッドで動くので、 Thread単位に、開始した時点でログインしたユーザIDを登録しておく。
 * 
 */
@DI(instance = Instance.Singleton)
public class DBUserPool {

    private Map<Thread, Object> userIdMap;

    public static DBUserPool get() {
        return Container.getComp(DBUserPool.class);
    }

    public DBUserPool() {
        super();
        userIdMap = new HashMap<>();
    }

    public void setUser(Integer userId) {
        synchronized (userIdMap) {
            Thread t = Thread.currentThread();
            userIdMap.put(t, userId);
        }
    }

    public void setUser(String userId) {
        synchronized (userIdMap) {
            Thread t = Thread.currentThread();
            userIdMap.put(t, userId);
        }
    }

    public Object getUser() {
        synchronized (userIdMap) {
            Thread t = Thread.currentThread();
            if (userIdMap.containsKey(t)) {
                return userIdMap.get(t);
            }
            return null;
        }
    }

    public void clearUser() {
        synchronized (userIdMap) {
            Thread t = Thread.currentThread();
            if (userIdMap.containsKey(t)) {
                userIdMap.remove(t);
            }
        }
    }

}
