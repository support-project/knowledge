package org.support.project.knowledge.sample;

import org.apache.directory.api.ldap.model.cursor.Cursor;
import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

public class ApacheLdapSample {

    public static void main(String[] args) throws Exception {
        LdapConnectionConfig sslConfig = new LdapConnectionConfig();
        sslConfig.setLdapHost("localhost"); // LDAP Host
        // sslConfig.setUseSsl(true); // Use SSL
        // sslConfig.setUseTls(true); // USE TLS
        sslConfig.setLdapPort(10389); // LDAP Port

        LdapConnection conn = new LdapNetworkConnection(sslConfig);
        Cursor<Entry> cursor = null;
        try {
            conn.bind("uid=admin,ou=system", "secret"); // Bind DN //Bind Password (接続確認用）

            String base = "dc=example,dc=com"; // Base DN
            String filter = "(uid=user1)"; // filter (user idから)
            // User id attribute
            // User name attribute
            // Mail address attribute
            // Additional filter condition
            // Enable TLS
            // Enable SSL

            SearchScope scope = SearchScope.SUBTREE;

            cursor = conn.search(base, filter, scope);
            while (cursor.next()) {
                Entry entry = cursor.get();
                System.out.println(entry.getDn());
                System.out.println(entry.get("uid").toString());
                System.out.println(entry.get("cn").toString());
                if (entry.get("mail") != null) {
                    System.out.println(entry.get("mail").toString());
                }
            }
            cursor.close();
            conn.unBind();

            // ユーザーで認証
            conn.bind("uid=user1,dc=example,dc=com", "test1234"); // Bind DN //Bind Password

            System.out.println("OK");

            cursor = conn.search(base, filter, scope);
            while (cursor.next()) {
                Entry entry = cursor.get();
                System.out.println(entry.get("uid").toString());
                System.out.println(entry.get("cn").toString());
                if (entry.get("mail") != null) {
                    System.out.println(entry.get("mail").toString());
                }
            }

        } catch (CursorException | LdapException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            conn.unBind();
            conn.close();
        }

    }

}
