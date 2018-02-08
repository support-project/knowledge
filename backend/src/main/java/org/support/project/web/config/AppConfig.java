package org.support.project.web.config;

import java.util.ArrayList;
import java.util.List;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.util.StringUtils;
import org.support.project.web.bean.Batchinfo;
import org.support.project.web.bean.LabelValue;

/**
 * Web Applicationの設定を保持するクラス
 * 
 * @author Koda
 *
 */
public class AppConfig extends org.support.project.common.config.AppConfig {
    /** インスタンス */
    private static AppConfig appConfig = null;

    /**
     * インスタンスを取得
     * 
     * @return instance
     */
    public static AppConfig get() {
        if (appConfig == null) {
            appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        }
        return appConfig;
    }

    /** パスワードのハッシュを生成する際おイテレーション回数 */
    private static final int DEFAULT_HASH_ITERATIONS = 100;
    /** テンポラリディレクトリのパス */
    private String tmpPath;
    /** テンポラリディレクトリのパス置換処理しかたどうかのフラグ */
    private boolean convTmpPath = false;
    /** ファイルアップロード時の最大容量 */
    private int uploadMaxMBSize;

    /** マイグレーション処理が置かれるパッケージ名 */
    private String migratePackage;

    /** HASH_ITERATIONS */
    private Integer hashIterations = DEFAULT_HASH_ITERATIONS;
    /** HASH_SIZE_BITS */
    // private Integer hashSizeBits;

    /** web applicationがインストールされているパス */
    private static String webRealPath = "";

    /** 言語設定 */
    private List<LabelValue> languages = new ArrayList<>();
    /** バッチプログラムの設定 */
    private List<Batchinfo> batchs = new ArrayList<>();

    /** ユーザ登録後の拡張処理 */
    private String addUserProcess;
    
    /** 言語設定変更後のページ */
    private String afterLangSelectPage;
    
    /** メンテナンス中かどうか */
    private static boolean maintenanceMode = false;
    
    
    /**
     * @return the tmpPath
     */
    public String getTmpPath() {
        if (StringUtils.isEmpty(tmpPath)) {
            return "";
        }
        if (!convTmpPath) {
            String path = tmpPath;
            tmpPath = convPath(path);
            convTmpPath = true;
        }
        return tmpPath;
    }

    /**
     * @param tmpPath the tmpPath to set
     */
    public void setTmpPath(String tmpPath) {
        this.tmpPath = tmpPath;
    }

    /**
     * @return the uploadMaxMBSize
     */
    public int getUploadMaxMBSize() {
        return uploadMaxMBSize;
    }

    /**
     * @param uploadMaxMBSize the uploadMaxMBSize to set
     */
    public void setUploadMaxMBSize(int uploadMaxMBSize) {
        this.uploadMaxMBSize = uploadMaxMBSize;
    }

    /**
     * @return the hashIterations
     */
    public Integer getHashIterations() {
        return hashIterations;
    }

    /**
     * @param hashIterations the hashIterations to set
     */
    public void setHashIterations(Integer hashIterations) {
        this.hashIterations = hashIterations;
    }

    /**
     * @return the webRealPath
     */
    public String getWebRealPath() {
        return webRealPath;
    }

    /**
     * @param webRealPath the webRealPath to set
     */
    public static void setWebRealPath(String webRealPath) {
        AppConfig.webRealPath = webRealPath;
    }

    /**
     * @return the languages
     */
    public List<LabelValue> getLanguages() {
        return languages;
    }

    /**
     * @param languages the languages to set
     */
    public void setLanguages(List<LabelValue> languages) {
        this.languages = languages;
    }

    /**
     * @return the migratePackage
     */
    public String getMigratePackage() {
        return migratePackage;
    }

    /**
     * @param migratePackage the migratePackage to set
     */
    public void setMigratePackage(String migratePackage) {
        this.migratePackage = migratePackage;
    }

    /**
     * @return the batchs
     */
    public List<Batchinfo> getBatchs() {
        return batchs;
    }

    /**
     * @param batchs the batchs to set
     */
    public void setBatchs(List<Batchinfo> batchs) {
        this.batchs = batchs;
    }

    /**
     * Get addUserProcess
     * @return the addUserProcess
     */
    public String getAddUserProcess() {
        return addUserProcess;
    }

    /**
     * Set addUserProcess
     * @param addUserProcess the addUserProcess to set
     */
    public void setAddUserProcess(String addUserProcess) {
        this.addUserProcess = addUserProcess;
    }

    /**
     * @return the afterLangSelectPage
     */
    public String getAfterLangSelectPage() {
        return afterLangSelectPage;
    }

    /**
     * @param afterLangSelectPage the afterLangSelectPage to set
     */
    public void setAfterLangSelectPage(String afterLangSelectPage) {
        this.afterLangSelectPage = afterLangSelectPage;
    }

    /**
     * @return the maintenanceMode
     */
    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

    /**
     * @param mode the maintenanceMode to set
     */
    public void setMaintenanceMode(boolean mode) {
        maintenanceMode = mode;
    }

}
