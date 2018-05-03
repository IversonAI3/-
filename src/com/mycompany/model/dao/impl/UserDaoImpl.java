package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.User;
import com.mycompany.model.dao.UserDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{


    @Override
    public User insertUser(Connection conn, User u) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `user` VALUES(DEFAULT, '")
                .append(u.getAccount()).append("', DEFAULT, '")
                .append(u.getPassword()).append("', NULL, 1);");
        System.out.println(sb);
        conn.createStatement().executeUpdate(sb.toString());
        return selectByAccount(conn,u.getAccount());
    }


    @Override
    public User selectByAccount(Connection conn, String acccount) {
        StringBuilder sql = new StringBuilder();
        Class c = this.getClassType();
        String tableName = c.getSimpleName();
        sql.append("SELECT * ")
                .append("FROM `").append(tableName)
                .append("` WHERE `account`='").append(acccount).append("';");
        System.out.println(sql);
        ResultSet rs;
        try {
            rs = conn.createStatement().executeQuery(sql.toString());
            if(!rs.next()){
                return null;
            }
            User user = new User();
            user.setUserId(rs.getInt(1));
            user.setAccount(rs.getString(2));
            user.setName(rs.getString(3));
            user.setPassword(rs.getString(4));
            user.setCard_id(rs.getInt(5));
            user.setType_id(rs.getInt(6));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User selectByAccountAndPassword(Connection conn, String account, String password) {
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
            User user = new User();
            user.setUserId(rs.getInt(1));
            user.setAccount(rs.getString(2));
            user.setName(rs.getString(3));
            user.setPassword(rs.getString(4));
            user.setCard_id(rs.getInt(5));
            user.setType_id(rs.getInt(6));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> selectByName(Connection conn, String name) {
        return null;
    }

    @Override
    public User updateName(Connection conn, User u, String name) throws SQLException {
        // UPDATE Person SET Address = '?', City = '?' WHERE LastName = '?'
        StringBuilder sb = new StringBuilder();
        u.setName(name);
        sb.append("UPDATE `user` SET `name`='").append(name)
        .append("' WHERE  `account`='").append(u.getAccount()).append("';");
        System.out.println(sb);
        conn.createStatement().executeUpdate(sb.toString());
        return u;
    }

    @Override
    public User updatePassword(Connection conn, User u, String password) throws SQLException {
        // UPDATE Person SET Address = '?', City = '?' WHERE LastName = '?'
        StringBuilder sb = new StringBuilder();
        u.setPassword(password);
        sb.append("UPDATE `user` SET `password`='").append(password)
                .append("' WHERE  `account`='").append(u.getAccount()).append("';");
        System.out.println(sb);
        conn.createStatement().executeUpdate(sb.toString());
        return u;
    }

    @Override
    public User updateCard(Connection conn, User u) throws SQLException {
        // UPDATE Person SET Address = '?', City = '?' WHERE LastName = '?'
        StringBuilder sb = new StringBuilder();
        System.out.println(u);
        Integer cardID = getMaxCardNoById(conn,u.getUserId())+1;
        u.setCard_id(cardID);
        sb.append("UPDATE `user` SET `card_id`='").append(cardID)
                .append("' WHERE  `account`='").append(u.getAccount()).append("';");
        System.out.println(sb);
        conn.createStatement().executeUpdate(sb.toString());
        return u;
    }

    /**
     * 通过user_id得到当前用户表中最大的借书卡号
     * */
    private Integer getMaxCardNoById(Connection conn, Integer id){
        StringBuilder sql = new StringBuilder();
        Class c = this.getClassType();
        String tableName = c.getSimpleName();
        sql.append("SELECT MAX(card_id) ")
                .append("FROM `").append(tableName).append("`;");
        System.out.println(sql);
        ResultSet rs;
        try {
            rs = conn.createStatement().executeQuery(sql.toString());
            if(!rs.next()){
                return null;
            }
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
