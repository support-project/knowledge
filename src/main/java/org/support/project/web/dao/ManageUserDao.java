package org.support.project.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.DateUtils;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.web.bean.User;
import org.support.project.web.logic.impl.AuthParam;
import org.support.project.web.logic.impl.AuthParamManager;

@DI(instance = Instance.Singleton)
public class ManageUserDao extends AbstractDao {
    /** シリアルバージョン */
    private static final long serialVersionUID = 1L;

    /** ログ */
    private static final Log LOG = LogFactory.getLog(ManageUserDao.class);

    private String insertUserSql = null;
    private String updateUserSql = null;
    private String deleteUserSql = null;

    private String selectUserSql = null;
    private String selectUserOnKeySql = null;

    private String insertRoleSql = null;
    private String deleteRoleSql = null;

    private String selectRoleOnUserSql = null;

    private String createSelectUserSQL(AuthParam param) {
        if (selectUserSql == null) {
            StringBuilder builder = new StringBuilder();
            // ユーザ取得SQL生成
            builder.append("SELECT ");

            builder.append(param.getUserTableUserIdColumn().trim());

            if (StringUtils.isNotEmpty(param.getUserTableUserNameColumn())) {
                builder.append(", ").append(param.getUserTableUserNameColumn().trim());
            }

            // builder.append(", ").append(param.getUserTableUserPasswordColumn());
            // count++;

            if (StringUtils.isNotEmpty(param.getUserTableInsertUserIdColumn())) {
                builder.append(", ").append(param.getUserTableInsertUserIdColumn().trim());
            }
            if (StringUtils.isNotEmpty(param.getUserTableInsertDateTimeColumn())) {
                builder.append(", ").append(param.getUserTableInsertDateTimeColumn().trim());
            }
            if (StringUtils.isNotEmpty(param.getUserTableUpdateUserIdColumn())) {
                builder.append(", ").append(param.getUserTableUpdateUserIdColumn().trim());
            }
            if (StringUtils.isNotEmpty(param.getUserTableUpdateDateTimeColumn())) {
                builder.append(", ").append(param.getUserTableUpdateDateTimeColumn().trim());
            }

            builder.append(" FROM ").append(param.getUserTable());

            LOG.debug(builder.toString());

            selectUserSql = builder.toString();
        }
        return selectUserSql;
    }

    private String createSelectUserOnKeySQL(AuthParam param) {
        if (selectUserOnKeySql == null) {
            StringBuilder builder = new StringBuilder();

            // ユーザ取得SQL生成
            builder.append("SELECT ");

            builder.append(param.getUserTableUserIdColumn().trim());

            if (StringUtils.isNotEmpty(param.getUserTableUserNameColumn())) {
                builder.append(", ").append(param.getUserTableUserNameColumn().trim());
            }

            builder.append(", ").append(param.getUserTableUserPasswordColumn());

            if (StringUtils.isNotEmpty(param.getUserTableInsertUserIdColumn())) {
                builder.append(", ").append(param.getUserTableInsertUserIdColumn().trim());
            }
            if (StringUtils.isNotEmpty(param.getUserTableInsertDateTimeColumn())) {
                builder.append(", ").append(param.getUserTableInsertDateTimeColumn().trim());
            }
            if (StringUtils.isNotEmpty(param.getUserTableUpdateUserIdColumn())) {
                builder.append(", ").append(param.getUserTableUpdateUserIdColumn().trim());
            }
            if (StringUtils.isNotEmpty(param.getUserTableUpdateDateTimeColumn())) {
                builder.append(", ").append(param.getUserTableUpdateDateTimeColumn().trim());
            }

            builder.append(" FROM ").append(param.getUserTable());

            builder.append(" WHERE ").append(param.getUserTableUserIdColumn().trim()).append(" = ?");

            LOG.debug(builder.toString());

            selectUserOnKeySql = builder.toString();
        }
        return selectUserOnKeySql;
    }

    private String createInsertUserSQL(AuthParam param) {
        if (insertUserSql == null) {
            StringBuilder builder = new StringBuilder();

            // ユーザ追加SQL生成
            int count = 0;
            builder.append("INSERT INTO ").append(param.getUserTable()).append(" (");

            builder.append(param.getUserTableUserIdColumn().trim());
            count++;

            if (StringUtils.isNotEmpty(param.getUserTableUserNameColumn())) {
                builder.append(", ").append(param.getUserTableUserNameColumn().trim());
                count++;
            }

            builder.append(", ").append(param.getUserTableUserPasswordColumn().trim());
            count++;

            if (StringUtils.isNotEmpty(param.getUserTableInsertUserIdColumn())) {
                builder.append(", ").append(param.getUserTableInsertUserIdColumn().trim());
                count++;
            }
            if (StringUtils.isNotEmpty(param.getUserTableInsertDateTimeColumn())) {
                builder.append(", ").append(param.getUserTableInsertDateTimeColumn().trim());
                count++;
            }
            // if (StringUtils.isNotEmpty(param.getUserTableUpdateUserIdColumn())) {
            // builder.append(", ").append(param.getUserTableUpdateUserIdColumn());
            // count++;
            // }
            // if (StringUtils.isNotEmpty(param.getUserTableUpdateDateTimeColumn())) {
            // builder.append(", ").append(param.getUserTableUpdateDateTimeColumn());
            // count++;
            // }

            builder.append(") values ( ");
            for (int i = 0; i < count; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append("?");
            }
            builder.append(")");

            LOG.debug(builder.toString());

            insertUserSql = builder.toString();
        }
        return insertUserSql;
    }

    private String createUpdateUserSQL(AuthParam param) {
        if (updateUserSql == null) {
            StringBuilder builder = new StringBuilder();

            // ユーザ更新SQL生成
            builder.append("UPDATE ").append(param.getUserTable()).append(" SET ");

            builder.append(param.getUserTableUserPasswordColumn().trim()).append(" = ? ");

            if (StringUtils.isNotEmpty(param.getUserTableUserNameColumn())) {
                builder.append(", ").append(param.getUserTableUserNameColumn().trim()).append(" = ? ");
            }

            if (StringUtils.isNotEmpty(param.getUserTableUpdateUserIdColumn())) {
                builder.append(", ").append(param.getUserTableUpdateUserIdColumn().trim()).append(" = ? ");
            }
            if (StringUtils.isNotEmpty(param.getUserTableUpdateDateTimeColumn())) {
                builder.append(", ").append(param.getUserTableUpdateDateTimeColumn().trim()).append(" = ? ");
            }

            builder.append(" WHERE ").append(param.getUserTableUserIdColumn().trim()).append(" = ?");

            LOG.debug(builder.toString());

            updateUserSql = builder.toString();
        }
        return updateUserSql;
    }

    private String createDeleteUserSQL(AuthParam param) {
        if (deleteUserSql == null) {
            StringBuilder builder = new StringBuilder();

            // ユーザ削除SQL生成
            builder.append("DELETE FROM ").append(param.getUserTable().trim());
            builder.append(" WHERE ").append(param.getUserTableUserIdColumn().trim()).append(" = ?");

            LOG.debug(builder.toString());

            deleteUserSql = builder.toString();
        }
        return deleteUserSql;
    }

    private String createSelectRoleOnUserIdSQL(AuthParam param) {
        if (selectRoleOnUserSql == null) {
            StringBuilder builder = new StringBuilder();

            // ロール取得SQL生成
            builder.append("SELECT ");

            builder.append(param.getRoleTableRoleIdColumn().trim());

            builder.append(" FROM ").append(param.getRoleTable());

            builder.append(" WHERE ").append(param.getRoleTableUserIdColumn().trim()).append(" = ?");

            LOG.debug(builder.toString());

            selectRoleOnUserSql = builder.toString();

        }
        return selectRoleOnUserSql;
    }

    private String createInsertRoleSQL(AuthParam param) {
        if (insertRoleSql == null) {
            StringBuilder builder = new StringBuilder();

            // ロール追加SQL生成
            int count = 0;
            builder.append("INSERT INTO ").append(param.getRoleTable()).append(" (");

            builder.append(param.getRoleTableUserIdColumn().trim());
            count++;

            builder.append(", ").append(param.getRoleTableRoleIdColumn().trim());
            count++;

            if (StringUtils.isNotEmpty(param.getUserTableInsertUserIdColumn())) {
                builder.append(", ").append(param.getUserTableInsertUserIdColumn().trim());
                count++;
            }
            if (StringUtils.isNotEmpty(param.getUserTableInsertDateTimeColumn())) {
                builder.append(", ").append(param.getUserTableInsertDateTimeColumn().trim());
                count++;
            }

            builder.append(") values ( ");
            for (int i = 0; i < count; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append("?");
            }
            builder.append(")");

            LOG.debug(builder.toString());

            insertRoleSql = builder.toString();
        }
        return insertRoleSql;
    }

    private String createDeleteRoleSQL(AuthParam param) {
        if (deleteRoleSql == null) {
            StringBuilder builder = new StringBuilder();

            // ロール削除SQL生成
            builder.append("DELETE FROM ").append(param.getRoleTable().trim());
            builder.append(" WHERE ").append(param.getRoleTableUserIdColumn().trim()).append(" = ?");

            LOG.debug(builder.toString());

            deleteRoleSql = builder.toString();
        }
        return deleteRoleSql;

    }

    public User getUser(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            con = super.getConnection();

            AuthParamManager manager = Container.getComp(AuthParamManager.class);
            AuthParam param = manager.getParam();
            // ユーザ取得
            String sql = createSelectUserOnKeySQL(param);
            stmt = con.prepareStatement(sql);
            stmt.setString(1, userId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getString(param.getUserTableUserIdColumn().trim()));
                user.setUserName(rs.getString(param.getUserTableUserNameColumn().trim()));
                user.setPassword(rs.getString(param.getUserTableUserPasswordColumn().trim()));

                user.setInsertUser(rs.getString(param.getUserTableInsertUserIdColumn().trim()));
                user.setInsertDatetime(rs.getTimestamp(param.getUserTableInsertDateTimeColumn().trim()));
                user.setUpdateUser(rs.getString(param.getUserTableUpdateUserIdColumn().trim()));
                user.setUpdateDatetime(rs.getTimestamp(param.getUserTableUpdateDateTimeColumn().trim()));
            }
            close(stmt, rs);

            if (user != null) {
                // ロール取得
                List<String> roleIds = getRoles(userId);
                user.setRoleIds(roleIds);
            }
        } finally {
            close(stmt, rs, con);
        }
        return user;
    }

    public List<User> listUsers() throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            con = super.getConnection();

            AuthParamManager manager = Container.getComp(AuthParamManager.class);
            AuthParam param = manager.getParam();
            // ユーザ一覧取得
            String sql = createSelectUserSQL(param);
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString(param.getUserTableUserIdColumn().trim()));
                user.setUserName(rs.getString(param.getUserTableUserNameColumn().trim()));

                user.setInsertUser(rs.getString(param.getUserTableInsertUserIdColumn().trim()));
                user.setInsertDatetime(rs.getTimestamp(param.getUserTableInsertDateTimeColumn().trim()));
                user.setUpdateUser(rs.getString(param.getUserTableUpdateUserIdColumn().trim()));
                user.setUpdateDatetime(rs.getTimestamp(param.getUserTableUpdateDateTimeColumn().trim()));

                users.add(user);
            }
        } finally {
            close(stmt, rs, con);
        }

        // ロール取得
        for (User user : users) {
            List<String> roleIds = getRoles(user.getUserId());
            user.setRoleIds(roleIds);
        }

        return users;
    }

    public int insertUser(String userId, String password, String userName, String performer, String... roleIds) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int result = -1;
        try {
            con = super.getConnection();

            AuthParamManager manager = Container.getComp(AuthParamManager.class);
            AuthParam param = manager.getParam();

            // ユーザ登録
            String sql = createInsertUserSQL(param);
            int count = 1;
            stmt = con.prepareStatement(sql);
            stmt.setString(count++, userId);
            if (StringUtils.isNotEmpty(param.getUserTableUserNameColumn())) {
                stmt.setString(count++, userName);
            }
            // String encPassword = this.encryptionPassword(password);
            stmt.setString(count++, password);

            if (StringUtils.isNotEmpty(param.getUserTableInsertUserIdColumn())) {
                stmt.setString(count++, performer);
            }
            if (StringUtils.isNotEmpty(param.getUserTableInsertDateTimeColumn())) {
                stmt.setTimestamp(count++, new Timestamp(DateUtils.now().getTime()));
            }
            result += stmt.executeUpdate();

            stmt.close();

            // ロール登録
            sql = createInsertRoleSQL(param);

            for (String roleId : roleIds) {
                count = 1;
                stmt = con.prepareStatement(sql);
                stmt.setString(count++, userId);
                stmt.setString(count++, roleId);

                if (StringUtils.isNotEmpty(param.getUserTableInsertUserIdColumn())) {
                    stmt.setString(count++, performer);
                }
                if (StringUtils.isNotEmpty(param.getUserTableInsertDateTimeColumn())) {
                    stmt.setTimestamp(count++, new Timestamp(DateUtils.now().getTime()));
                }
                result += stmt.executeUpdate();
            }

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            close(stmt, rs, con);
        }
        return result;
    }

    public int updateUser(String userId, String password, String userName, String performer, String... roleIds) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int result = -1;
        try {
            con = super.getConnection();

            AuthParamManager manager = Container.getComp(AuthParamManager.class);
            AuthParam param = manager.getParam();

            // ロール削除
            String sql = createDeleteRoleSQL(param);
            int count = 1;
            stmt = con.prepareStatement(sql);

            // ここでは物理削除にする(論理削除では無いので、実行者のパラメータは使っていない)
            stmt.setString(count++, userId);
            result += stmt.executeUpdate();

            stmt.close();

            // ロール登録
            sql = createInsertRoleSQL(param);

            for (String roleId : roleIds) {
                count = 1;
                stmt = con.prepareStatement(sql);
                stmt.setString(count++, userId);
                stmt.setString(count++, roleId);

                if (StringUtils.isNotEmpty(param.getUserTableInsertUserIdColumn())) {
                    stmt.setString(count++, performer);
                }
                if (StringUtils.isNotEmpty(param.getUserTableInsertDateTimeColumn())) {
                    stmt.setTimestamp(count++, new Timestamp(DateUtils.now().getTime()));
                }
                result += stmt.executeUpdate();
            }

            // ユーザ更新
            sql = createUpdateUserSQL(param);
            count = 1;
            stmt = con.prepareStatement(sql);
            // String encPassword = this.encryptionPassword(password);
            stmt.setString(count++, password);

            if (StringUtils.isNotEmpty(param.getUserTableUserNameColumn())) {
                stmt.setString(count++, userName);
            }

            if (StringUtils.isNotEmpty(param.getUserTableUpdateUserIdColumn())) {
                stmt.setString(count++, performer);
            }
            if (StringUtils.isNotEmpty(param.getUserTableUpdateDateTimeColumn())) {
                stmt.setTimestamp(count++, new Timestamp(DateUtils.now().getTime()));
            }

            stmt.setString(count++, userId);
            result = stmt.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;

        } finally {
            close(stmt, rs, con);
        }
        return result;
    }

    public int deleteUser(String userId, String performer) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int result = -1;
        try {
            con = super.getConnection();

            AuthParamManager manager = Container.getComp(AuthParamManager.class);
            AuthParam param = manager.getParam();

            // ロール削除
            String sql = createDeleteRoleSQL(param);
            int count = 1;
            stmt = con.prepareStatement(sql);

            // ここでは物理削除にする(論理削除では無いので、実行者のパラメータは使っていない)
            stmt.setString(count++, userId);
            result += stmt.executeUpdate();

            stmt.close();

            // ユーザ削除
            sql = createDeleteUserSQL(param);
            // SQL実行
            count = 1;
            stmt = con.prepareStatement(sql);

            // ここでは物理削除にする(論理削除では無いので、実行者のパラメータは使っていない)
            stmt.setString(count++, userId);
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;

        } finally {
            close(stmt, rs, con);
        }
        return result;
    }

    public List<String> getRoles(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> roleIds = new ArrayList<>();
        try {
            con = super.getConnection();

            AuthParamManager manager = Container.getComp(AuthParamManager.class);
            AuthParam param = manager.getParam();

            // インサート用のSQL生成
            String sql = createSelectRoleOnUserIdSQL(param);
            // SQL実行
            stmt = con.prepareStatement(sql);
            stmt.setString(1, userId);

            rs = stmt.executeQuery();

            while (rs.next()) {
                String role = rs.getString(param.getRoleTableRoleIdColumn().trim());
                roleIds.add(role);
            }
        } finally {
            close(stmt, rs, con);
        }
        return roleIds;
    }

}
