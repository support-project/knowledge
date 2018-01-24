package org.support.project.web.logic;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.directory.api.ldap.model.cursor.Cursor;
import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidAttributeValueException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.directory.ldap.client.api.exception.InvalidConnectionException;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.LdapInfo;
import org.support.project.web.entity.LdapConfigsEntity;

@DI(instance = Instance.Singleton)
public class LdapLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    /**
     * Get instance
     * 
     * @return instance
     */
    public static LdapLogic get() {
        return Container.getComp(LdapLogic.class);
    }

    /**
     * Ldapを使った認証
     * 
     * @param entity entity
     * @param id id
     * @param password password
     * @return LdapInfo
     * @throws IOException IOException
     * @throws LdapException LdapException
     */
    public LdapInfo auth(LdapConfigsEntity entity, String id, String password) throws IOException, LdapException {
        if (entity.getAuthType() == LdapConfigsEntity.AUTH_TYPE_LDAP || entity.getAuthType() == LdapConfigsEntity.AUTH_TYPE_BOTH) {
            return ldapLogin1(entity, id, password);
        } else if (entity.getAuthType() == LdapConfigsEntity.AUTH_TYPE_LDAP_2 || entity.getAuthType() == LdapConfigsEntity.AUTH_TYPE_BOTH_2) {
            return ldapLogin2(entity, id, password);
        }
        return null;
    }

    /**
     * 接続チェック
     * 
     * @param entity entity
     * @return result
     * @throws LdapException LdapException
     * @throws IOException IOException
     * @throws BadPaddingException BadPaddingException
     * @throws IllegalBlockSizeException IllegalBlockSizeException
     * @throws NoSuchPaddingException NoSuchPaddingException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeyException InvalidKeyException
     */
    public boolean check(LdapConfigsEntity entity) throws LdapException, IOException, InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        LdapConnectionConfig config = new LdapConnectionConfig();
        config.setLdapHost(entity.getHost()); // LDAP Host
        config.setLdapPort(entity.getPort()); // LDAP Port
        if (entity.getUseSsl() != null && entity.getUseSsl().intValue() == INT_FLAG.ON.getValue()) {
            config.setUseSsl(true); // Use SSL
        } else if (entity.getUseTls() != null && entity.getUseTls().intValue() == INT_FLAG.ON.getValue()) {
            config.setUseTls(true); // USE TLS
        }
        LdapConnection conn = null;
        Cursor<Entry> cursor = null;
        try {
            String pass = entity.getBindPassword();
            if (StringUtils.isNotEmpty(entity.getSalt())) {
                pass = PasswordUtil.decrypt(pass, entity.getSalt());
            }
            conn = new LdapNetworkConnection(config);
            conn.bind(entity.getBindDn(), pass); // Bind DN //Bind Password (接続確認用）
            return true;
        } catch (LdapException e) {
            // 認証失敗
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (conn != null) {
                conn.unBind();
                conn.close();
            }
        }

    }

    /**
     * Ldapログイン処理
     * 
     * はじめに、検索用のユーザでLdapに接続し、ログイン用のユーザのDNを検索する その後に、ログインを実行する。 こうすることでldapLogin1で発生したBASE DNが固定という問題を解消できる
     * 
     * @param entity entity
     * @param id id
     * @param password password
     * @return LdapInfo
     * @throws LdapException LdapException
     * @throws IOException IOException
     */
    private LdapInfo ldapLogin2(LdapConfigsEntity entity, String id, String password) throws LdapException, IOException {
        LdapConnectionConfig config = new LdapConnectionConfig();
        config.setLdapHost(entity.getHost()); // LDAP Host
        config.setLdapPort(entity.getPort()); // LDAP Port
        if (entity.getUseSsl() != null && entity.getUseSsl().intValue() == INT_FLAG.ON.getValue()) {
            config.setUseSsl(true); // Use SSL
        } else if (entity.getUseTls() != null && entity.getUseTls().intValue() == INT_FLAG.ON.getValue()) {
            config.setUseTls(true); // USE TLS
        }
        LdapConnection conn = null;
        LdapConnection conn2 = null;
        Cursor<Entry> cursor = null;
        try {
            conn = new LdapNetworkConnection(config);
            String pass = entity.getBindPassword();
            if (StringUtils.isNotEmpty(entity.getSalt())) {
                pass = PasswordUtil.decrypt(pass, entity.getSalt());
            }
            try {
                conn.bind(entity.getBindDn(), pass); // Bind DN //Bind Password (接続確認用）
            } catch (InvalidConnectionException e) {
                LOG.error(e);
                // 認証失敗(LDAPに接続できない）
                return null;
            }
            String base = entity.getBaseDn(); // Base DN
            String filter = entity.getFilter(); // filter (user idから)
            filter = filter.replace(":userid", id);

            SearchScope scope = SearchScope.SUBTREE;
            cursor = conn.search(base, filter, scope);
            String dn = null;
            while (cursor.next()) {
                Entry entry = cursor.get();
                dn = entry.getDn().toString();
                break;
            }

            if (StringUtils.isEmpty(dn)) {
                return null;
            }
            // 認証
            conn2 = new LdapNetworkConnection(config);
            conn2.bind(dn, password); // Bind DN //Bind Password (接続確認用）
            
            cursor = conn2.search(base, filter, scope);
            LdapInfo info = null;
            while (cursor.next()) {
                Entry entry = cursor.get();
                info = loadLdapInfo(entity, entry);
                break;
            }
            return info;
        } catch (LdapException | CursorException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException e) {
            // 認証失敗
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (conn != null && conn.isConnected()) {
                conn.unBind();
                conn.close();
            }
            if (conn2 != null && conn2.isConnected()) {
                conn2.unBind();
                conn2.close();
            }
        }
    }

    /*
     * はじめに作成したLdapLogin処理
     * BASE DNが固定になってしまい、サブツリー上のユーザでログイン出来ない問題がある Knowledge 0.5.3 でリリースしてしまったので、
     * それをそのまま使えるように残しておくが、 上記のldapLogin2を使うように変更を促していく
     */
    @Deprecated
    private LdapInfo ldapLogin1(LdapConfigsEntity entity, String id, String password) throws LdapException, IOException {
        LdapConnectionConfig config = new LdapConnectionConfig();
        config.setLdapHost(entity.getHost()); // LDAP Host
        config.setLdapPort(entity.getPort()); // LDAP Port
        if (entity.getUseSsl() != null && entity.getUseSsl().intValue() == INT_FLAG.ON.getValue()) {
            config.setUseSsl(true); // Use SSL
        } else if (entity.getUseTls() != null && entity.getUseTls().intValue() == INT_FLAG.ON.getValue()) {
            config.setUseTls(true); // USE TLS
        }
        LdapConnection conn = null;
        Cursor<Entry> cursor = null;
        try {
            conn = new LdapNetworkConnection(config);
            StringBuilder bindDn = new StringBuilder();
            bindDn.append(entity.getIdAttr()).append("=").append(id);
            if (StringUtils.isNotEmpty(entity.getBaseDn())) {
                bindDn.append(",").append(entity.getBaseDn());
            }
            try {
                conn.bind(bindDn.toString(), password);
            } catch (InvalidConnectionException e) {
                LOG.error(e);
                // 認証失敗(LDAPに接続できない）
                return null;
            }

            String base = entity.getBaseDn(); // Base DN
            StringBuilder filter = new StringBuilder();
            filter.append("(").append(entity.getIdAttr()).append("=").append(id).append(")");
            SearchScope scope = SearchScope.SUBTREE;
            cursor = conn.search(base, filter.toString(), scope);
            while (cursor.next()) {
                Entry entry = cursor.get();
                LdapInfo info = loadLdapInfo(entity, entry);
                return info;
            }
            return null;
        } catch (LdapException | CursorException e) {
            // 認証失敗
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (conn != null && conn.isConnected()) {
                conn.unBind();
                conn.close();
            }
        }
    }

    private LdapInfo loadLdapInfo(LdapConfigsEntity entity, Entry entry) throws LdapInvalidAttributeValueException {
        LdapInfo info = new LdapInfo();
        info.setId(entry.get(entity.getIdAttr()).getString());
        if (StringUtils.isNotEmpty(entity.getNameAttr())) {
            if (entry.get(entity.getNameAttr()) != null) {
                info.setName(entry.get(entity.getNameAttr()).getString());
            }
        }
        if (StringUtils.isNotEmpty(entity.getMailAttr())) {
            if (entry.get(entity.getMailAttr()) != null) {
                info.setMail(entry.get(entity.getMailAttr()).getString());
            }
        }

        if (StringUtils.isNotEmpty(entity.getAdminCheckFilter())) {
            String[] adminids = entity.getAdminCheckFilter().split(",");
            for (String string : adminids) {
                if (info.getId().equals(string.trim())) {
                    info.setAdmin(true);
                    break;
                }
            }
        }
        return info;
    }

}
