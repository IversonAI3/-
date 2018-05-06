package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.Admin;
import com.mycompany.model.dao.AdminDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao{
    @Override
    public Admin selectByAccount(Connection conn, String account) throws SQLException {
        StringBuilder sql = new StringBuilder();
        Class c = this.getClassType();
        String tableName = c.getSimpleName();
        sql.append("SELECT * ")
                .append("FROM `").append(tableName)
                .append("` WHERE `account`='").append(account).append("';");
        System.out.println(sql);
        ResultSet rs;
        try {
            rs = conn.createStatement().executeQuery(sql.toString());
            if(!rs.next()){
                return null;
            }
            Admin admin = new Admin();
            admin.setAdmin_id(rs.getInt("admin_id"));
            admin.setAccount(rs.getString("account"));
            admin.setPassword(rs.getString("password"));
            admin.setName(rs.getString("name"));
            return admin;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Admin selectByAccountAndPassword(Connection conn, String account, String password) {
        StringBuilder sql = new StringBuilder();
        Class c = this.getClassType();
        String tableName = c.getSimpleName();
        sql.append("SELECT * ")
                .append("FROM `").append(tableName)
                .append("` WHERE `account`='").append(account)
                .append("' AND `password`='").append(password).append("';");
        System.out.println(sql);
        ResultSet rs;
        try {
            rs = conn.createStatement().executeQuery(sql.toString());
            if(!rs.next()){
                return null;
            }
            Admin admin = new Admin();
            admin.setAdmin_id(rs.getInt("admin_id"));
            admin.setAccount(rs.getString("account"));
            admin.setPassword(rs.getString("password"));
            admin.setName(rs.getString("name"));
            return admin;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Admin管理员的增删改不允许用过程序操作
     * */
    @Override
    public Admin insert(Connection conn, Admin admin) throws SQLException {
        return null;
    }

    @Override
    public Admin update(Connection conn, Admin admin) throws SQLException {
        return null;
    }

    @Override
    public Admin delete(Connection conn, Admin admin) throws SQLException {
        return null;
    }

    @Override
    public Admin find(Connection conn, Admin admin) throws SQLException {
        return null;
    }
}
