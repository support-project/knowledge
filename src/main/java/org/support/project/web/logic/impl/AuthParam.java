package org.support.project.web.logic.impl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.support.project.common.serialize.Serialize;
import org.support.project.common.serialize.SerializerValue;

@Serialize(value = SerializerValue.Jaxb)
@XmlRootElement
public class AuthParam implements Serializable {

    private String userTable;
    private String userTableUserIdColumn;
    private String userTableUserNameColumn;
    private String userTableUserPasswordColumn;

    private String passwordEncType;

    private String roleTable;
    private String roleTableUserIdColumn;
    private String roleTableRoleIdColumn;

    private String roleFunctionTable;
    private String roleFunctionTableRoleIdColumn;
    private String roleFunctionTableFunctionIdColumn;

    private String userTableInsertUserIdColumn;
    private String userTableInsertDateTimeColumn;
    private String userTableUpdateUserIdColumn;
    private String userTableUpdateDateTimeColumn;

    private String roleTableInsertUserIdColumn;
    private String roleTableInsertDateTimeColumn;
    private String roleTableUpdateUserIdColumn;
    private String roleTableUpdateDateTimeColumn;

    private String roleFunctionTableInsertUserIdColumn;
    private String roleFunctionTableInsertDateTimeColumn;
    private String roleFunctionTableUpdateUserIdColumn;
    private String roleFunctionTableUpdateDateTimeColumn;

    public String getUserTable() {
        return userTable;
    }

    public void setUserTable(String userTable) {
        this.userTable = userTable;
    }

    public String getUserTableUserIdColumn() {
        return userTableUserIdColumn;
    }

    public void setUserTableUserIdColumn(String userTableUserIdColumn) {
        this.userTableUserIdColumn = userTableUserIdColumn;
    }

    public String getUserTableUserPasswordColumn() {
        return userTableUserPasswordColumn;
    }

    public void setUserTableUserPasswordColumn(String userTableUserPasswordColumn) {
        this.userTableUserPasswordColumn = userTableUserPasswordColumn;
    }

    public String getPasswordEncType() {
        return passwordEncType;
    }

    public void setPasswordEncType(String passwordEncType) {
        this.passwordEncType = passwordEncType;
    }

    public String getRoleTable() {
        return roleTable;
    }

    public void setRoleTable(String roleTable) {
        this.roleTable = roleTable;
    }

    public String getRoleTableUserIdColumn() {
        return roleTableUserIdColumn;
    }

    public void setRoleTableUserIdColumn(String roleTableUserIdColumn) {
        this.roleTableUserIdColumn = roleTableUserIdColumn;
    }

    public String getRoleTableRoleIdColumn() {
        return roleTableRoleIdColumn;
    }

    public void setRoleTableRoleIdColumn(String roleTableRoleIdColumn) {
        this.roleTableRoleIdColumn = roleTableRoleIdColumn;
    }

    public String getUserTableInsertUserIdColumn() {
        return userTableInsertUserIdColumn;
    }

    public void setUserTableInsertUserIdColumn(String userTableInsertUserIdColumn) {
        this.userTableInsertUserIdColumn = userTableInsertUserIdColumn;
    }

    public String getUserTableInsertDateTimeColumn() {
        return userTableInsertDateTimeColumn;
    }

    public void setUserTableInsertDateTimeColumn(String userTableInsertDateTimeColumn) {
        this.userTableInsertDateTimeColumn = userTableInsertDateTimeColumn;
    }

    public String getUserTableUpdateUserIdColumn() {
        return userTableUpdateUserIdColumn;
    }

    public void setUserTableUpdateUserIdColumn(String userTableUpdateUserIdColumn) {
        this.userTableUpdateUserIdColumn = userTableUpdateUserIdColumn;
    }

    public String getUserTableUpdateDateTimeColumn() {
        return userTableUpdateDateTimeColumn;
    }

    public void setUserTableUpdateDateTimeColumn(String userTableUpdateDateTimeColumn) {
        this.userTableUpdateDateTimeColumn = userTableUpdateDateTimeColumn;
    }

    public String getRoleTableInsertUserIdColumn() {
        return roleTableInsertUserIdColumn;
    }

    public void setRoleTableInsertUserIdColumn(String roleTableInsertUserIdColumn) {
        this.roleTableInsertUserIdColumn = roleTableInsertUserIdColumn;
    }

    public String getRoleTableInsertDateTimeColumn() {
        return roleTableInsertDateTimeColumn;
    }

    public void setRoleTableInsertDateTimeColumn(String roleTableInsertDateTimeColumn) {
        this.roleTableInsertDateTimeColumn = roleTableInsertDateTimeColumn;
    }

    public String getRoleTableUpdateUserIdColumn() {
        return roleTableUpdateUserIdColumn;
    }

    public void setRoleTableUpdateUserIdColumn(String roleTableUpdateUserIdColumn) {
        this.roleTableUpdateUserIdColumn = roleTableUpdateUserIdColumn;
    }

    public String getRoleTableUpdateDateTimeColumn() {
        return roleTableUpdateDateTimeColumn;
    }

    public void setRoleTableUpdateDateTimeColumn(String roleTableUpdateDateTimeColumn) {
        this.roleTableUpdateDateTimeColumn = roleTableUpdateDateTimeColumn;
    }

    public String getUserTableUserNameColumn() {
        return userTableUserNameColumn;
    }

    public void setUserTableUserNameColumn(String userTableUserNameColumn) {
        this.userTableUserNameColumn = userTableUserNameColumn;
    }

    public String getRoleFunctionTable() {
        return roleFunctionTable;
    }

    public void setRoleFunctionTable(String roleFunctionTable) {
        this.roleFunctionTable = roleFunctionTable;
    }

    public String getRoleFunctionTableRoleIdColumn() {
        return roleFunctionTableRoleIdColumn;
    }

    public void setRoleFunctionTableRoleIdColumn(String roleFunctionTableRoleIdColumn) {
        this.roleFunctionTableRoleIdColumn = roleFunctionTableRoleIdColumn;
    }

    public String getRoleFunctionTableFunctionIdColumn() {
        return roleFunctionTableFunctionIdColumn;
    }

    public void setRoleFunctionTableFunctionIdColumn(String roleFunctionTableFunctionIdColumn) {
        this.roleFunctionTableFunctionIdColumn = roleFunctionTableFunctionIdColumn;
    }

    public String getRoleFunctionTableInsertUserIdColumn() {
        return roleFunctionTableInsertUserIdColumn;
    }

    public void setRoleFunctionTableInsertUserIdColumn(String roleFunctionTableInsertUserIdColumn) {
        this.roleFunctionTableInsertUserIdColumn = roleFunctionTableInsertUserIdColumn;
    }

    public String getRoleFunctionTableInsertDateTimeColumn() {
        return roleFunctionTableInsertDateTimeColumn;
    }

    public void setRoleFunctionTableInsertDateTimeColumn(String roleFunctionTableInsertDateTimeColumn) {
        this.roleFunctionTableInsertDateTimeColumn = roleFunctionTableInsertDateTimeColumn;
    }

    public String getRoleFunctionTableUpdateUserIdColumn() {
        return roleFunctionTableUpdateUserIdColumn;
    }

    public void setRoleFunctionTableUpdateUserIdColumn(String roleFunctionTableUpdateUserIdColumn) {
        this.roleFunctionTableUpdateUserIdColumn = roleFunctionTableUpdateUserIdColumn;
    }

    public String getRoleFunctionTableUpdateDateTimeColumn() {
        return roleFunctionTableUpdateDateTimeColumn;
    }

    public void setRoleFunctionTableUpdateDateTimeColumn(String roleFunctionTableUpdateDateTimeColumn) {
        this.roleFunctionTableUpdateDateTimeColumn = roleFunctionTableUpdateDateTimeColumn;
    }

}
