package org.support.project.web.dao;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.ormapping.dao.AbstractDao;
import org.support.project.web.logic.impl.AuthParam;
import org.support.project.web.logic.impl.AuthParamManager;

public class ManageFunctionDao extends AbstractDao {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    private String selectFunctionAccessRoleSQL = null;

    
    private String createSelectFunctionAccessRoleSQL(AuthParam param) {
        if (selectFunctionAccessRoleSQL == null) {
            StringBuilder builder = new StringBuilder();

            //ロール取得SQL生成
            builder.append("SELECT ");
            
            builder.append(param.getRoleFunctionTableRoleIdColumn().trim());
            
            builder.append(" FROM ").append(param.getRoleFunctionTable());
            
            builder.append(" WHERE ").append(param.getRoleFunctionTableFunctionIdColumn().trim()).append(" = ?");
            
            LOG.debug(builder.toString());
            
            selectFunctionAccessRoleSQL = builder.toString();

        }
        return selectFunctionAccessRoleSQL;
    }    
    
    
    public List<String> getAccessRoleIds(String function) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> roleIds = new ArrayList<>();
        try {
            con = super.getConnection();
            
            AuthParamManager manager = Container.getComp(AuthParamManager.class);
            AuthParam param = manager.getParam();
            
            //インサート用のSQL生成
            String sql = createSelectFunctionAccessRoleSQL(param);
            //SQL実行
            stmt = con.prepareStatement(sql);
            stmt.setString(1, function);
            
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
