package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.AbstractUser;
import com.mycompany.model.bean.User;
import com.mycompany.model.dao.UserDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{


    @Override
    public int insert(Connection conn, User user) throws SQLException {
        return 0;
    }

    @Override
    public int update(Connection conn, User user) throws SQLException {
        return 0;
    }

    @Override
    public User selectByAccount(Connection conn, String acccount) {
        StringBuilder sql = new StringBuilder();
        Class c = this.getClassType();
        String tableName = c.getSimpleName();
        sql.append("SELECT")
                .append("FROM ").append(tableName)
                .append(" WHERE ");
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
}
