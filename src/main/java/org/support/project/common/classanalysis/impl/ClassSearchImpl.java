package org.support.project.common.classanalysis.impl;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.support.project.common.classanalysis.ClassSearch;
import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

/**
 * クラス検索
 * 
 * @author Koda
 * 
 */
public class ClassSearchImpl implements ClassSearch {
    /** ログ */
    private static Log log = LogFactory.getLog(ClassSearchImpl.class);
    private ClassLoader classLoader;

    @Override
    public Class<?>[] classSearch(String packageName, boolean subpackages) throws SystemException {
        try {
            String resourceName = packageNameToResourceName(packageName);
            URL url = classLoader.getResource(resourceName);
            if (url == null) {
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                JavaFileManager fm = compiler.getStandardFileManager(new DiagnosticCollector<JavaFileObject>(), null, null);

                // 一覧に含めるオブジェクト種別。以下はクラスのみを含める。
                Set<JavaFileObject.Kind> kinds = new HashSet<JavaFileObject.Kind>() {
                    {
                        add(JavaFileObject.Kind.CLASS);
                    }
                };
                Iterable<JavaFileObject> iterable;
                List<Class<?>> classes = new ArrayList<>();
                iterable = fm.list(StandardLocation.PLATFORM_CLASS_PATH, packageName, kinds, subpackages);

                for (JavaFileObject javaFileObject : iterable) {
                    if (javaFileObject.getKind() == JavaFileObject.Kind.CLASS) {
                        String name = javaFileObject.getName();
                        int start = 0;
                        if (name.lastIndexOf(".jar/") != -1) {
                            start = name.lastIndexOf(".jar/") + ".jar/".length();
                        }
                        String clname = name.substring(start); // クラス名取得
                        if (clname.indexOf(".class") != -1) {
                            int end = clname.indexOf(".class") + ".class".length();
                            clname = clname.substring(0, end);
                        }
                        clname = resourceNameToClassName(clname);
                        log.info(clname);
                        classes.add(Class.forName(clname));
                    }
                }
                return classes.toArray(new Class<?>[0]);

            } else {
                return findClasses(packageName).toArray(new Class<?>[0]);
            }
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    // public static void main(String[] args) throws Exception {
    // ClassSearchImpl classFinder = new ClassSearchImpl();
    // // classFinder.printClasses(args[0]);
    //
    // for (Class<?> clazz : classFinder.findClasses(args[0])) {
    // System.out.println(clazz);
    // }
    // }

    public ClassSearchImpl() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public ClassSearchImpl(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    // public void printClasses(String rootPackageName) throws Exception {
    // String resourceName = rootPackageName.replace('.', '/');
    // URL url = classLoader.getResource(resourceName);
    // System.out.println("URL = " + url);
    // System.out.println("URLConnection = " + url.openConnection());
    // }

    private String fileNameToClassName(String name) {
        return name.substring(0, name.length() - ".class".length());
    }

    private String resourceNameToClassName(String resourceName) {
        return fileNameToClassName(resourceName).replace('/', '.');
    }

    private boolean isClassFile(String fileName) {
        return fileName.endsWith(".class");
    }

    private String packageNameToResourceName(String packageName) {
        return packageName.replace('.', '/');
    }

    public List<Class<?>> findClasses(String rootPackageName) throws Exception {
        String resourceName = packageNameToResourceName(rootPackageName);
        URL url = classLoader.getResource(resourceName);

        if (url == null) {
            return new ArrayList<Class<?>>();
        }

        String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
            return findClassesWithFile(rootPackageName, new File(url.toURI().getPath()));
        } else if ("jar".equals(protocol)) {
            return findClassesWithJarFile(rootPackageName, url);
        }

        throw new IllegalArgumentException("Unsupported Class Load Protodol[" + protocol + "]");
    }

    private List<Class<?>> findClassesWithFile(String packageName, File dir) throws Exception {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (dir != null) {
            String[] children = dir.list();
            if (children != null) {
                for (String path : children) {
                    File entry = new File(dir, path);
                    if (entry.isFile() && isClassFile(entry.getName())) {
                        classes.add(classLoader.loadClass(packageName + "." + fileNameToClassName(entry.getName())));
                    } else if (entry.isDirectory()) {
                        classes.addAll(findClassesWithFile(packageName + "." + entry.getName(), entry));
                    }
                }
            }
        }

        return classes;
    }

    private List<Class<?>> findClassesWithJarFile(String rootPackageName, URL jarFileUrl) throws Exception {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        JarURLConnection jarUrlConnection = (JarURLConnection) jarFileUrl.openConnection();
        JarFile jarFile = null;

        try {
            jarFile = jarUrlConnection.getJarFile();
            Enumeration<JarEntry> jarEnum = jarFile.entries();

            String packageNameAsResourceName = packageNameToResourceName(rootPackageName);

            while (jarEnum.hasMoreElements()) {
                JarEntry jarEntry = jarEnum.nextElement();
                if (jarEntry.getName().startsWith(packageNameAsResourceName) && isClassFile(jarEntry.getName())) {
                    classes.add(classLoader.loadClass(resourceNameToClassName(jarEntry.getName())));
                }
            }
        } finally {
            if (jarFile != null) {
                jarFile.close();
            }
        }

        return classes;
    }

}
