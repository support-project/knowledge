package org.support.project.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.dao.gen.GenLocalesDao;
import org.support.project.web.entity.LocalesEntity;

/**
 * ロケール
 */
@DI(instance = Instance.Singleton)
public class LocalesDao extends GenLocalesDao {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /**
     * インスタンス取得 AOPに対応
     * 
     * @return インスタンス
     */
    public static LocalesDao get() {
        return Container.getComp(LocalesDao.class);
    }

    /**
     * ロケールデータを取得
     * 
     * @param locale locale
     * @return data
     */
    public LocalesEntity selectOrCreate(Locale locale) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM LOCALES WHERE ");
        List<String> params = new ArrayList<>();
        if (locale.getLanguage() != null) {
            sql.append("LANGUAGE = ? ");
            params.add(locale.getLanguage());
        } else {
            sql.append("LANGUAGE IS NULLL ");
        }
        if (!params.isEmpty()) {
            sql.append("AND ");
        }
        if (locale.getCountry() != null) {
            sql.append("COUNTRY = ? ");
            params.add(locale.getCountry());
        } else {
            sql.append("COUNTRY IS NULLL ");
        }
        if (!params.isEmpty()) {
            sql.append("AND ");
        }
        if (locale.getVariant() != null) {
            sql.append("VARIANT = ? ");
            params.add(locale.getVariant());
        } else {
            sql.append("VARIANT IS NULLL ");
        }
        LocalesEntity entity = executeQuerySingle(sql.toString(), LocalesEntity.class, params.toArray(new String[0]));
        if (entity == null) {
            entity = new LocalesEntity();
            entity.setKey(locale.toString());
            entity.setLanguage(locale.getLanguage());
            entity.setCountry(locale.getCountry());
            entity.setVariant(locale.getVariant());
            insert(entity);
        }
        return entity;
    }

}
