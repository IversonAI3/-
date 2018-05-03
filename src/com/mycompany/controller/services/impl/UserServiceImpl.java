package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.model.bean.AbstractUser;
import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.User;
import com.mycompany.model.dao.UserDao;
import com.mycompany.model.dao.impl.UserDaoImpl;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements AbstractUserService<User> {
    private Connection conn;
    private UserDao userDao = new UserDaoImpl(); // 用户数据访问对象

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    // 实例化UserServiceImpl的时候获取一个数据库连接对象
    public UserServiceImpl() throws SQLException{
        this.conn = JdbcUtils.getConnection();
    }

    @Override
    public User register(User user) throws SQLException {
        String account = user.getAccount();
        User u = findByAccount(account);
        if(u!=null){
            System.out.println("用户已经存在"+u.getAccount()+"，不允许注册");
            return null;
        }
        System.out.println("用户不存在，可以注册, 调用UserServiceImple insert()方法");
        return userDao.insertUser(conn,user);
    }

    @Override
    public User findByAccount(String account) {
        try {
            return userDao.selectByAccount(conn,account);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User login(User user) throws SQLException {
        User u = userDao.selectByAccount(conn,user.getAccount());
        return u;
    }

    @Override
    public User rename(User user, String name) throws SQLException {
        return userDao.updateName(conn,user,name);
    }

    @Override
    public User changePwd(User user, String pwd) throws SQLException {
        return userDao.updatePassword(conn,user,pwd);
    }


    @Override
    public List<Book> showAllBooks() throws SQLException {
        return null;
    }

    @Override
    public User findByAccountAndPassword(String account, String pwd) throws SQLException {
        User u = userDao.selectByAccountAndPassword(conn,account,pwd);
        System.out.println("UserServiceImpl.findByAccountAndPassword: "+u);
        return u;

//        sb.append("SELECT * FROM `user` WHERE `account` = '")
//                .append(account)
//                .append("' AND `password` = '").append(password).append("';");
//        System.out.println(sb.toString());
//        try {
//            ResultSet resultSet = conn.createStatement().executeQuery(sb.toString());
//            if(!resultSet.next()){
//                user = null;
//            }else{
//                user = new User();
//                user.setAccount(resultSet.getString("account"));
//                user.setPassword(resultSet.getString("password"));
//                ((User)user).setType_id(resultSet.getInt("type_id"));
//                System.out.println(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return (User) user;
    }


    /**
     * 登录：至少需要三个参数: 账号,密码,账户类型
     * */
//    private User createRecord(User u) throws SQLException {
//        StringBuilder sb = new StringBuilder();
//        sb.append("INSERT INTO `user` VALUEs(DEFAULT, '")
//                .append(u.getAccount()).append("', DEFAULT, '")
//                .append(u.getPassword()).append("', NULL, 1);");
//        System.out.println(sb);
//        conn.createStatement().executeUpdate(sb.toString());
//        return u;
//    }
}
