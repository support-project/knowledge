package org.support.project.common.bat;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

/**
 * Javaのプログラムをバッチで実行(別プロセスでjavaを起動する)
 * 
 * @author koda
 */
@DI(instance = Instance.Prototype)
public class JavaJob implements Job {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /**
     * インスタンス取得
     * 
     * @return instance
     */
    public static JavaJob get() {
        return Container.getComp(JavaJob.class);
    }

    /** Jarが格納されているディレクトリ */
    private List<File> jarDirs = new ArrayList<>();
    /** クラスパスのディレクトリ */
    private List<File> classPathDirs = new ArrayList<>();

    /** 実行するメインクラス */
    private String mainClass;
    /** メイン関数に渡す引数 */
    private List<String> params = new ArrayList<>();
    /** カレントディレクトリ */
    private File currentDirectory = null;

    /** 環境変数 */
    private Map<String, String> environment = new LinkedHashMap<>();

    /** javaコマンドで -jar で実行するかどうか */
    private boolean jarOption;

    /** コンソール出力を渡すリスナー */
    private ConsoleListener consoleListener = null;
    
    /** ヒープ・メモリ全体の起動時のサイズ */
    private int xms = -1;
    /** ヒープ・メモリ全体の最大サイズ */
    private int xmx = -1;
    /** NEW領域のサイズ */
    private int xmn = -1;

    /**
     * 実行するメインクラスを設定します。
     * 
     * @param mainClass
     *            実行するメインクラス
     * @return JavaJob
     */
    public JavaJob setMainClass(String mainClass) {
        this.mainClass = mainClass;
        return this;
    }

    /**
     * クラスパスのディレクトリを追加
     * 
     * @param dir
     *            JavaJob
     * @return JavaJob
     */
    public JavaJob addClassPathDir(File dir) {
        this.classPathDirs.add(dir);
        return this;
    }

    /**
     * クラスパスのディレクトリを追加
     * 
     * @param dir
     *            dir
     * @return JavaJob
     */
    public JavaJob addjarDir(File dir) {
        this.jarDirs.add(dir);
        return this;
    }

    /**
     * 引数追加
     * 
     * @param param
     *            param
     * @return JavaJob
     */
    public JavaJob addParam(String param) {
        this.params.add(param);
        return this;
    }

    /**
     * 環境変数に値を追加
     * 
     * @param key
     *            key
     * @param value
     *            value
     * @return JavaJob
     */
    public JavaJob addEnvironment(String key, String value) {
        this.environment.put(key, value);
        return this;
    }

    @Override
    public JobResult execute() throws Exception {
        BatJob batJob = new BatJob();
        if (currentDirectory != null) {
            batJob.setCurrentDirectory(currentDirectory);
        }

        batJob.addCommand("java");
        if (xms != -1) {
            batJob.addCommand("-Xms" + xms + "m");
        }
        if (xmx != -1) {
            batJob.addCommand("-Xmx" + xmx + "m");
        }
        if (xmn != -1) {
            batJob.addCommand("-Xmn" + xmn + "m");
        }
        
        
        
        if (!jarDirs.isEmpty() || !classPathDirs.isEmpty()) {
            batJob.addCommand("-classpath");
            batJob.addCommand(makeClassPath());
        }
        if (jarOption) {
            batJob.addCommand("-jar");
        }
        batJob.addCommand(mainClass);

        if (consoleListener != null) {
            batJob.setConsoleListener(consoleListener);
        }

        if (!environment.isEmpty()) {
            Iterator<String> iterator = environment.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                batJob.addEnvironment(key, environment.get(key));
            }
        }

        return batJob.execute();
    }

    /**
     * クラスパスの生成
     * 
     * @return classpath string
     */
    private String makeClassPath() {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (File dir : jarDirs) {
            if (count > 0) {
                builder.append(File.pathSeparator);
            }
            appendLibPath(builder, dir);
            count++;
        }
        for (File dir : classPathDirs) {
            String path = dir.getPath();
            builder.append(File.pathSeparator);
            builder.append(path);
        }
        return builder.toString();
    }

    /**
     * 再帰的にクラスパスを取得
     * 
     * @param builder
     *            StringBuilder
     * @param dir
     *            directory
     */
    private void appendLibPath(StringBuilder builder, File dir) {
        LOG.trace(dir);
        String path = dir.getPath() + File.separator + "*";
        builder.append(path);

        File[] files = dir.listFiles();
        if (files != null) {
            for (File child : files) {
                if (child.isDirectory()) {
                    appendLibPath(builder, child);
                }
            }
        }
    }

    /**
     * カレントディレクトリをセット
     * 
     * @param currentDirectory
     *            currentDirectory
     */
    public void setCurrentDirectory(File currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    /**
     * Jarのディレクトリをセット
     * 
     * @param jarOption
     *            jarOption
     */
    public void setJarOption(boolean jarOption) {
        this.jarOption = jarOption;
    }

    /**
     * set console listener
     * 
     * @param consoleListener
     *            the consoleListener to set
     */
    public void setConsoleListener(ConsoleListener consoleListener) {
        this.consoleListener = consoleListener;
    }

    /**
     * Get xms
     * @return the xms
     */
    public int getXms() {
        return xms;
    }

    /**
     * Set xms
     * @param xms the xms to set
     */
    public void setXms(int xms) {
        this.xms = xms;
    }

    /**
     * Get xmx
     * @return the xmx
     */
    public int getXmx() {
        return xmx;
    }

    /**
     * Set xmx
     * @param xmx the xmx to set
     */
    public void setXmx(int xmx) {
        this.xmx = xmx;
    }

    /**
     * Get xmn
     * @return the xmn
     */
    public int getXmn() {
        return xmn;
    }

    /**
     * Set xmn
     * @param xmn the xmn to set
     */
    public void setXmn(int xmn) {
        this.xmn = xmn;
    }

}
