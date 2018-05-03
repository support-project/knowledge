package org.support.project.web.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.support.project.common.classanalysis.ClassSearch;
import org.support.project.common.classanalysis.impl.ClassSearchImpl;
import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.config.HttpMethod;
import org.support.project.web.control.service.Delete;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.control.service.Put;

@DI(instance = Instance.Singleton)
public class InvokeSearch {
    /** ログ */
    private static Log LOG = LogFactory.getLog(InvokeSearch.class);

    /** 文字列に対するターゲット */
    private Map<String, InvokeTarget> invokeGetTargets;
    private Map<String, InvokeTarget> invokePostTargets;
    private Map<String, InvokeTarget> invokePutTargets;
    private Map<String, InvokeTarget> invokeDeleteTargets;

    /**
     * コンストラクタ
     */
    public InvokeSearch() {
        super();
        invokeGetTargets = new TreeMap<>();
        invokePostTargets = new TreeMap<>();
        invokePutTargets = new TreeMap<>();
        invokeDeleteTargets = new TreeMap<>();
    }

    /**
     * 指定のパッケージ内のクラスを読み込み、 パス文字列でアクセスした際に、実行するターゲットを追加する
     * 
     * @param targetPackageName ターゲットのパッケージ
     * @param classSuffix クラスのサフィックス
     */
    public void addTarget(String targetPackageName, String classSuffix) {
        this.addTarget(targetPackageName, classSuffix, true);
    }

    /**
     * 指定のパッケージ内のクラスを読み込み、 パス文字列でアクセスした際に、実行するターゲットを追加する
     * 
     * @param targetPackageName ターゲットのパッケージ
     * @param classSuffix クラスのサフィックス
     * @param subpackages サブパッケージを対象にするか
     */
    public void addTarget(String targetPackageName, String classSuffix, boolean subpackages) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Add targget from oackage. [" + targetPackageName + "]");
        }
        ClassSearch classSearch = new ClassSearchImpl();
        Class<?>[] classes = classSearch.classSearch(targetPackageName, subpackages);
        if (classes != null) {
            for (Class<?> class1 : classes) {
                if (class1.getName().endsWith(classSuffix)) {
                    // サフィックスが同じものみを処理する
                    int mod = class1.getModifiers();
                    if (!class1.isInterface() && !Modifier.isAbstract(mod)) {
                        // インタフェースとアブストラクトクラスは除外
                        addTarget(class1, targetPackageName, classSuffix);
                    }
                }
            }
        }
    }

    /**
     * 指定のクラスを、実行ターゲットに追加する
     * 
     * @param class1 クラス
     * @param targetPackageName 検索するパッケージ名
     * @param classSuffix サフィックス
     */
    private void addTarget(Class<?> class1, String targetPackageName, String classSuffix) {
        // 以下の規則で、実行ターゲットを登録する
        // {subpackage}.{Classname(Suffixを除外)}/{methodName}
        StringBuilder builder = new StringBuilder();
        String packageName = class1.getPackage().getName();
        if (packageName.length() > targetPackageName.length() && packageName.startsWith(targetPackageName)) {
            packageName = packageName.substring(targetPackageName.length() + 1);
            builder.append(packageName);
            builder.append(".");
        }
        String className = class1.getName().substring(class1.getPackage().getName().length() + 1, class1.getName().length() - classSuffix.length());
        builder.append(className);

        Method[] methods = class1.getMethods();
        if (methods != null) {
            for (Method method : methods) {
                // Get/Post/Put/Delete が指定されていないものは対象外とする
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Get) {
                        Get get = (Get) annotation;
                        addGetTarget(class1, method, targetPackageName, classSuffix, builder.toString(), get);
                    }
                    if (annotation instanceof Post) {
                        Post post = (Post) annotation;
                        addPostTarget(class1, method, targetPackageName, classSuffix, builder.toString(), post);
                    }
                    if (annotation instanceof Put) {
                        Put put = (Put) annotation;
                        addPutTarget(class1, method, targetPackageName, classSuffix, builder.toString(), put);
                    }
                    if (annotation instanceof Delete) {
                        Delete delete = (Delete) annotation;
                        addDeleteTarget(class1, method, targetPackageName, classSuffix, builder.toString(), delete);
                    }
                }
            }
        }
    }

    private void addDeleteTarget(Class<?> class1, Method method, String targetPackageName, String classSuffix, String call, Delete delete) {
        String path = delete.path();
        addTarget(class1, method, targetPackageName, classSuffix, call, path, invokeDeleteTargets);
    }

    private void addPutTarget(Class<?> class1, Method method, String targetPackageName, String classSuffix, String call, Put put) {
        String path = put.path();
        addTarget(class1, method, targetPackageName, classSuffix, call, path, invokePutTargets);
    }

    private void addPostTarget(Class<?> class1, Method method, String targetPackageName, String classSuffix, String call, Post post) {
        String path = post.path();
        addTarget(class1, method, targetPackageName, classSuffix, call, path, invokePostTargets);
    }

    private void addGetTarget(Class<?> class1, Method method, String targetPackageName, String classSuffix, String call, Get get) {
        String path = get.path();
        addTarget(class1, method, targetPackageName, classSuffix, call, path, invokeGetTargets);
    }

    private void addTarget(Class<?> class1, Method method, String targetPackageName, String classSuffix, String call, String path,
            Map<String, InvokeTarget> invokeTargets) {
        String key = call + "/" + method.getName().toLowerCase(); // 大文字・小文字は無視
        if (StringUtils.isNotEmpty(path)) {
            key = path;
        }
        InvokeTarget invokeTarget = new InvokeTarget(class1, method, targetPackageName, classSuffix, new LinkedHashMap<>());
        if (invokeTargets.containsKey(key)) {
            InvokeTarget exists =  invokeTargets.get(key);
            if (exists.getTargetClass().getName().equals(invokeTarget.getTargetClass().getName())
                    && exists.getTargetMethod().getName().equals(invokeTarget.getTargetMethod().getName())) {
                //なぜか、同じクラスの同じメソッドが二回登録されることがあるのでスキップ
                LOG.info("same target duplicate add. [" + key + "]");
                return;
            }
            // 既に指定のパスが使われている
            LOG.error("Target duplicated. [" + key + "]");
            LOG.error("class:" + invokeTarget.getTargetClass().getName() + "  method:" + invokeTarget.getTargetMethod().getName());
            LOG.error("class:" + exists.getTargetClass().getName() + "  method:" + exists.getTargetMethod().getName());
            throw new SystemException("Target duplicated. [" + key + "]");
        }
        // 大文字／小文字は判定しない
        invokeTargets.put(key.toLowerCase(), invokeTarget);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Add targget. [" + key + "]");
        }
    }

    /**
     * パス文字列から、対応するコントローラーのインスタンスと、 実行するメソッドを取得する
     * 
     * @param method HttpMethod
     * @param path パス文字列
     * @param pathInfo 
     * @return 実行するターゲット
     */
    public InvokeTarget getController(HttpMethod method, String path, String pathInfo) {
        Map<String, InvokeTarget> invokeTargets = invokeDeleteTargets;
        if (method == HttpMethod.get) {
            invokeTargets = invokeGetTargets;
        } else if (method == HttpMethod.post) {
            invokeTargets = invokePostTargets;
        } else if (method == HttpMethod.put) {
            invokeTargets = invokePutTargets;
        }
        path = path.toLowerCase(); // 大文字・小文字は無視
        // そのものズバリのパスが無い場合、、、
        // // /api/knowledges/{:knowledgeId}/event を管理できるように拡張
        InvokeTarget target = null;
        if (StringUtils.isNotEmpty(pathInfo)) {
            String access = path + pathInfo;
            LOG.trace(access);
            List<String> sortedKeys = new ArrayList<String>(invokeTargets.keySet());
            //Collections.reverse(sortedKeys);
            Collections.sort(sortedKeys, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    int pathcnt1 = o1.split("/").length;
                    int pathcnt2 = o2.split("/").length;
                    if (pathcnt1 > pathcnt2) {
                        return -1;
                    } else if (pathcnt1 < pathcnt2) {
                        return 1;
                    }
                    return o1.compareTo(o2);
                }
            });
            for (String key : sortedKeys) {
                if (key.startsWith(path)) {
                    if (key.equals(path)) {
                        target = invokeTargets.get(key);
                        break;
                    }
                    String[] paths1 = key.split("/");
                    String[] paths2 = access.split("/");
                    if (paths1.length == paths2.length) {
                        boolean same = true;
                        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
                        for (int i = 0; i < paths1.length; i++) {
                            if (!paths1[i].toLowerCase().equals(paths2[i].toLowerCase())) {
                                if (paths1[i].startsWith(":")) {
                                    paramMap.put(paths1[i].substring(1), paths2[i]);
                                } else {
                                    same = false;
                                    break;
                                }
                            }
                        }
                        if (same) {
                            target = invokeTargets.get(key);
                            target.getPathValue().putAll(paramMap);
                            break;
                        }
                    }
                }
            }
        } else {
            target = invokeTargets.get(path);
        }
        if (target != null) {
            InvokeTarget copy = target.copy();
            return copy;
        }
        return null;
    }

}
