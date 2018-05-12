package com.mycompany.model.dao;

import com.mycompany.model.bean.Admin;
import com.mycompany.model.bean.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AdminDao extends BaseDao<Admin>{
    /**
     * 通过账号来查找一个Admin，账号是唯一的
     * @return 返回查找到的Admin对象
     * */
    Admin selectByAccount(Connection conn, String account) throws SQLException;
    /**
     * 通过账号和密码来查找一个用户，可以用来验证账号密码
     * @return 返回查找到的User对象
     * */
    Admin selectByAccountAndPassword(Connection conn, String account, String password) throws SQLException;
}
