package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.User;
import com.mycompany.model.dao.UserDao;

import java.sql.Connection;
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
        StringBuilder sb = new StringBuilder();
        Class c = this.getClassType();
        String tableName = c.getSimpleName();
        sb.append("SELECT")
                .append("FROM ").append(tableName)
                .append(" WHERE ");
        return null;
    }

    @Override
    public List<User> selectByName(Connection conn, String name) {
        return null;
    }
}
