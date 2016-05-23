package org.support.project.knowledge.control.admin;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.control.Control;
import org.support.project.web.annotation.Auth;
import org.support.project.web.bean.LdapInfo;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.control.service.Get;
import org.support.project.web.control.service.Post;
import org.support.project.web.dao.LdapConfigsDao;
import org.support.project.web.entity.LdapConfigsEntity;
import org.support.project.web.exception.InvalidParamException;
import org.support.project.web.logic.LdapLogic;

import net.arnx.jsonic.JSONException;

@DI(instance = Instance.Prototype)
public class LdapControl extends Control {

    private static final String CONFIG_TYPE2 = "config2";
    private static final String CONFIG_TYPE1 = "config1";
    private static final String NO_CHANGE_PASSWORD = "NO_CHANGE_PASSWORD-fXLSJ_V-ZJ2E-X6c2_iGCpkE"; // パスワードを更新しなかったことを表すパスワード

    /**
     * 設定画面を表示
     * 
     * @return
     */
    @Get
    @Auth(roles = "admin")
    public Boundary config() {
        LdapConfigsDao dao = LdapConfigsDao.get();
        LdapConfigsEntity entity = dao.selectOnKey(AppConfig.get().getSystemName());
        String configType = CONFIG_TYPE1;
        if (entity == null) {
            entity = new LdapConfigsEntity();
        } else {
            entity.setBindPassword(NO_CHANGE_PASSWORD);
            entity.setSalt("");

            if (entity.getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_LDAP_2) {
                configType = CONFIG_TYPE2;
                entity.setAuthType(LdapConfigsEntity.AUTH_TYPE_LDAP);
            } else if (entity.getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_BOTH_2) {
                configType = CONFIG_TYPE2;
                entity.setAuthType(LdapConfigsEntity.AUTH_TYPE_BOTH);
            }
        }
        setAttribute("configType", configType);
        entity.setSystemName(AppConfig.get().getSystemName());
        setAttributeOnProperty(entity);

        setAttribute("host2", entity.getHost());
        setAttribute("port2", entity.getPort());

        if (entity.getUseSsl() != null && entity.getUseSsl().intValue() == INT_FLAG.ON.getValue()) {
            setAttribute("security", "usessl");
        } else if (entity.getUseTls() != null && entity.getUseTls().intValue() == INT_FLAG.ON.getValue()) {
            setAttribute("security", "usetls");
        } else {
            setAttribute("security", "plain");
        }

        return forward("config.jsp");
    }

    /**
     * リクエストの情報からLdapの設定情報を抽出（共通処理）
     * 
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws InvalidParamException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private LdapConfigsEntity loadLdapConfig() throws InstantiationException, IllegalAccessException, IOException, InvalidParamException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        LdapConfigsDao dao = LdapConfigsDao.get();
        LdapConfigsEntity entity = super.getParamOnProperty(LdapConfigsEntity.class);
        String security = getParam("security");
        if (!StringUtils.isEmpty(security)) {
            if (security.toLowerCase().equals("usessl")) {
                entity.setUseSsl(INT_FLAG.ON.getValue());
            } else if (security.toLowerCase().equals("usetls")) {
                entity.setUseTls(INT_FLAG.ON.getValue());
            }
        }

        String configType = getParam("configType");
        if (CONFIG_TYPE2.equals(configType)) {
            if (entity.getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_LDAP) {
                entity.setAuthType(LdapConfigsEntity.AUTH_TYPE_LDAP_2);
            } else if (entity.getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_BOTH) {
                entity.setAuthType(LdapConfigsEntity.AUTH_TYPE_BOTH_2);
            }

            entity.setHost(getParam("host2"));
            entity.setPort(getParam("port2", Integer.class));
            security = getParam("security2");
            if (!StringUtils.isEmpty(security)) {
                if (security.toLowerCase().equals("usessl")) {
                    entity.setUseSsl(INT_FLAG.ON.getValue());
                } else if (security.toLowerCase().equals("usetls")) {
                    entity.setUseTls(INT_FLAG.ON.getValue());
                }
            }
            entity.setBindDn(getParam("bindDn2"));
            entity.setBindPassword(getParam("bindPassword2"));
            entity.setBaseDn(getParam("baseDn2"));
            entity.setFilter(getParam("filter2"));
            entity.setIdAttr(getParam("idAttr2"));
            entity.setNameAttr(getParam("nameAttr2"));
            entity.setMailAttr(getParam("mailAttr2"));
            entity.setAdminCheckFilter(getParam("adminCheckFilter2"));
            setAttributeOnProperty(entity);
        }

        String password = entity.getBindPassword();
        if (password.equals(NO_CHANGE_PASSWORD)) {
            LdapConfigsEntity saved = dao.selectOnKey(AppConfig.get().getSystemName());
            if (saved != null) {
                String encPass = saved.getBindPassword();
                String salt = saved.getSalt();
                password = PasswordUtil.decrypt(encPass, salt);
                entity.setBindPassword(password);
            }
        }

        return entity;
    }

    /**
     * Ldap認証の設定のテスト
     * 
     * @return
     * @throws InvalidParamException
     * @throws IOException
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws LdapException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    @Post
    @Auth(roles = "admin")
    public Boundary check() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException, LdapException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        LdapConfigsDao dao = LdapConfigsDao.get();
        LdapConfigsEntity entity = dao.selectOnKey(AppConfig.get().getSystemName());
        String configType = CONFIG_TYPE1;
        if (entity == null) {
            addMsgWarn("knowledge.ldap.msg.connect.error");
            return forward("config.jsp");
        } else {
            if (entity.getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_LDAP_2) {
                configType = CONFIG_TYPE2;
            } else if (entity.getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_BOTH_2) {
                configType = CONFIG_TYPE2;
            }
        }

        LdapLogic ldapLogic = LdapLogic.get();
        if (CONFIG_TYPE2.equals(configType)) {
            boolean check = ldapLogic.check(entity);
            if (!check) {
                addMsgWarn("knowledge.ldap.msg.connect.error");
            } else {
                addMsgSuccess("knowledge.ldap.msg.connect.success2");
            }
        } else {
            String pass = entity.getBindPassword();
            if (StringUtils.isNotEmpty(entity.getSalt())) {
                pass = PasswordUtil.decrypt(pass, entity.getSalt());
            }
            LdapInfo result = ldapLogic.auth(entity, entity.getBindDn(), pass);
            if (result == null) {
                addMsgWarn("knowledge.ldap.msg.connect.error");
            } else {
                addMsgSuccess("knowledge.ldap.msg.connect.success", result.getId(), result.getName(), result.getMail(),
                        String.valueOf(result.isAdmin()));
            }
        }
        return config();
    }

    /**
     * Ldap認証の設定のテスト
     * 
     * @return
     * @throws InvalidParamException
     * @throws IOException
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws LdapException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    @Post
    @Auth(roles = "admin")
    public Boundary save() throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException, LdapException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Map<String, String> params = getParams();
        String configType = getParam("configType");
        if (CONFIG_TYPE2.equals(configType)) {
            params.put("host", params.get("host2"));
            params.put("port", params.get("port2"));
        }
        List<ValidateError> errors = LdapConfigsEntity.get().validate(params);
        if (errors != null && !errors.isEmpty()) {
            super.setResult("", errors);
            return forward("config.jsp");
        }
        LdapConfigsEntity entity = loadLdapConfig();
        LdapLogic ldapLogic = LdapLogic.get();
        boolean check = false;
        if (CONFIG_TYPE2.equals(configType)) {
            if (entity.getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_LDAP_2) {
                check = ldapLogic.check(entity);
            } else {
                check = true;
            }
        } else {
            if (entity.getAuthType().intValue() == LdapConfigsEntity.AUTH_TYPE_LDAP) {
                LdapInfo result = ldapLogic.auth(entity, entity.getBindDn(), entity.getBindPassword());
                if (result != null) {
                    check = true;
                }
            } else {
                check = true;
            }
        }
        if (!check) {
            addMsgWarn("knowledge.ldap.msg.save.error");
        } else {
            // Ldap設定を保存
            LdapConfigsDao dao = LdapConfigsDao.get();
            entity.setSystemName(AppConfig.get().getSystemName());
            String salt = PasswordUtil.getSalt();
            String passHash = PasswordUtil.encrypt(entity.getBindPassword(), salt);
            entity.setBindPassword(passHash);
            entity.setSalt(salt);
            dao.save(entity);

            entity.setBindPassword(NO_CHANGE_PASSWORD);
            setAttributeOnProperty(entity);
            addMsgSuccess("knowledge.ldap.msg.save.success");
        }
        return config();
    }

    /**
     * Ldap設定の削除
     * 
     * @return
     */
    @Post
    @Auth(roles = "admin")
    public Boundary delete() {
        LdapConfigsDao dao = LdapConfigsDao.get();
        LdapConfigsEntity entity = dao.selectOnKey(AppConfig.get().getSystemName());
        if (entity != null) {
            dao.physicalDelete(AppConfig.get().getSystemName());
        }
        entity = new LdapConfigsEntity();
        entity.setSystemName(AppConfig.get().getSystemName());
        setAttributeOnProperty(entity);

        addMsgInfo("message.success.delete.target", getResource("knowledge.ldap.title"));

        return config();
    }

}
