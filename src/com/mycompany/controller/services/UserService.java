package com.mycompany.controller.services;


import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    /* 用户的注册行为 */
    boolean register() throws SQLException;
    /* 用户的登录行为 */
    boolean login(User u) throws SQLException;
    /* 用户的修改用户名行为 */
    boolean rename(User u) throws SQLException;
    /* 用户的修改密码行为 */
    boolean changePwd(User u) throws SQLException;
    /* 用户的查询所有书的行为 */
    List<Book> showAllBooks() throws SQLException;
}
