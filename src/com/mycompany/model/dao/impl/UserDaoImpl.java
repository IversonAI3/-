package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.Card;
import com.mycompany.model.bean.User;
import com.mycompany.model.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

    @Override
    public User insert(Connection conn, User u) throws SQLException {
        PreparedStatement ps = conn
                .prepareStatement("INSERT INTO `user`(account,password,type_id) VALUES(?,?,?)");
        ps.setString(1,u.getAccount());
        ps.setString(2,u.getPassword());
        ps.setInt(3,u.getType_id());
        int i = ps.executeUpdate();
        return i==1?u:null;
    }

    @Override
    public User selectByAccount(Connection conn, String account) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM `user` WHERE `account`='").append(account).append("';");
        ResultSet rs = conn.createStatement().executeQuery(sql.toString());
        User u = null;
        if(rs.next()){
            u = new User();
            initializeUser(rs,u);
        }
        return u;
    }

    @Override
    public User selectByAccountAndPassword(Connection conn, String account, String password) throws SQLException{
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM `user` WHERE `account`='").append(account)
                .append("' AND `password`='").append(password).append("';");
        ResultSet rs = conn.createStatement().executeQuery(sql.toString());
        if(!rs.next()){
            return null;
        }
        User user = new User();
        initializeUser(rs,user);
        return user;

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
        int i = conn.createStatement().executeUpdate(sb.toString());
        return i==1?u:null;
    }

    @Override
    public User updatePassword(Connection conn, User u, String password) throws SQLException {
        // UPDATE Person SET Address = '?', City = '?' WHERE LastName = '?'
        StringBuilder sb = new StringBuilder();
        u.setPassword(password);
        sb.append("UPDATE `user` SET `password`='").append(password)
                .append("' WHERE  `account`='").append(u.getAccount()).append("';");
        int i = conn.createStatement().executeUpdate(sb.toString());
        return i==1?u:null;
    }

    @Override
    public User updateCard(Connection conn, User u, Card card) throws SQLException {
        // UPDATE Person SET Address = '?', City = '?' WHERE LastName = '?'
        StringBuilder sb = new StringBuilder();
        if(u.getCard_id()==0){ // 如果用户没有借书卡的话
            Integer cardId = card.getCard_id();
            sb.append("UPDATE `user` SET `card_id`='").append(cardId)
                    .append("' WHERE  `account`='").append(u.getAccount()).append("';");
            conn.createStatement().executeUpdate(sb.toString());
            u.setCard_id(cardId);
        }
        return u;
    }

    @Override
    public User findUserByCardId(Connection conn, Integer card_id) throws SQLException {
        PreparedStatement ps = conn
                .prepareStatement("SELECT * FROM `user` WHERE `card_id`=?");
        ps.setInt(1,card_id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            User user = new User();
            initializeUser(rs,user);
            return user;
        }
        return null;
    }

    /**
     * 通过user_id得到当前用户表中最大的借书卡号
     * */
    private Integer getMaxCardNoById(Connection conn, Integer id) throws SQLException{
        StringBuilder sql = new StringBuilder();
        Class c = this.getClassType();
        String tableName = c.getSimpleName();
        sql.append("SELECT MAX(card_id) ")
                .append("FROM `").append(tableName).append("`;");
        System.out.println(sql);
        ResultSet rs = conn.createStatement().executeQuery(sql.toString());
        if(!rs.next()){
            return null;
        }
        return rs.getInt("card_id");
    }

    @Override
    public User update(Connection conn, User user) throws SQLException {
        return null;
    }

    @Override
    public User delete(Connection conn, User user) throws SQLException {
        return null;
    }

    @Override
    public User find(Connection conn, User user) throws SQLException {
        return null;
    }

    private void initializeUser(ResultSet rs, User user) throws SQLException {
        user.setUser_id(rs.getInt("user_id"));
        user.setAccount(rs.getString("account"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setRegitime(rs.getTimestamp("regitime").toString());
        user.setCard_id(rs.getInt("card_id"));
        user.setType_id(rs.getInt("type_id"));
    }
}
