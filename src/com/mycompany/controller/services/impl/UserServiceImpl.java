package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.Card;
import com.mycompany.model.bean.Record;
import com.mycompany.model.bean.User;
import com.mycompany.model.dao.*;
import com.mycompany.model.dao.impl.BookDaoImpl;
import com.mycompany.model.dao.impl.CardDaoImpl;
import com.mycompany.model.dao.impl.RecordDaoImpl;
import com.mycompany.model.dao.impl.UserDaoImpl;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements AbstractUserService<User> {
    private Connection conn;
    private UserDao userDao = new UserDaoImpl(); // 用户数据访问对象
    private RecordDao recordDao = new RecordDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    private CardDao cardDao = new CardDaoImpl();

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    // 实例化UserServiceImpl的时候获取一个数据库连接对象
    public UserServiceImpl() {
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
        return userDao.insert(conn,user);
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
     * 用户独有的方法
     * @param card 接受一个借书卡对象作为参数
     * @param user 接受一个用户作为参数，将借书卡和用户绑定
     * @return 返回一个绑定好借书卡的用户
     * */
    public User getNewCard(User user, Card card) throws SQLException {
        return userDao.updateCard(conn,user,card);
    }

    /**
     * 用户借书，包括两个操作，1.创建借书记录Record对象 2.
     * 如果书的数量少于或等于0，或者如果借书卡的额度少于或等于0 => 借书失败
     * @return 如果借书成功返回true，如果失败返回false
     * */
    public boolean borrowBook(User user, Card card, Book book) throws SQLException {
        StringBuilder sb = new StringBuilder();
        Integer quantity = book.getQuantity(); // 书的数量
        Short quota = card.getQuota(); // 借书卡的额度
        Short amount = card.getAmount(); // 已借的书的数量
        if(quantity <= 0 || quota <= 0 || amount>5 )
            return false;
        book.setQuantity(quantity-1);
        bookDao.update(conn, book);
        card.setQuota((short) (quota-1));
        card.setAmount((short) (amount+1));
        cardDao.update(conn,card);
        Record record = new Record();
        record.setBook_id(book.getBook_id());
        record.setCard_id(card.getCard_id());
        record.setBorrow_time(TimestampUtils.getBorrowTime().toString());
        if(user.getType_id()==1) // 1代表学生 2代表教师
            record.setReturn_time(TimestampUtils.getReturnTime(21).toString());
        else
            record.setReturn_time(TimestampUtils.getReturnTime(14).toString());
        System.out.println(record);
        recordDao.insert(conn, record);
        return true;
    }

    /**
     * 获得用户使用的借书卡的所有借阅记录
     * @param card 借书卡对象
     * @return 包含所有记录的集合
     * */
    public List<Record> getAllRecords(Card card) throws SQLException{
        return recordDao.selectByCardId(conn, card);
    }

    /**
     * 还书：删除这条借书记录
     */
    public Record returnBook(Record record) throws SQLException{
        Book book = bookDao.findByBookId(conn,record.getBook_id());
        Card card = cardDao.selectCardByCardId(conn,record.getCard_id());
        if(book==null || card==null)
            return null;
        book.setQuantity(book.getQuantity()+1); // 如果还书成功，书的数量+1
        card.setQuota((short) (card.getQuota()+1)); // 借书卡的限额+1
        card.setAmount((short) (card.getAmount()-1)); // 已借书数量-1
        if(bookDao.update(conn, book)==null || cardDao.update(conn,card)==null)
            return null;
        return recordDao.delete(conn, record);
    }
}
