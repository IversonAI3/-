package com.mycompany.model.dao;

import com.mycompany.model.bean.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao extends BaseDao<User>{
    User selectByAccount(Connection conn, String acccount);
    List<User> selectByName(Connection conn, String name);
}
