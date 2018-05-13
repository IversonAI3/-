package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.model.bean.*;
import com.mycompany.model.dao.*;
import com.mycompany.model.dao.impl.*;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.*;
import java.util.List;

public class UserServiceImpl implements AbstractUserService<User> {
    private Connection conn;
    private UserDao userDao = new UserDaoImpl(); // 用户数据访问对象
    private BorrowRecordDao borrowRecordDao = new BorrowRecordDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    private CardDao cardDao = new CardDaoImpl();
    private ReturnRecordDao returnRecordDao = new ReturnRecordDaoImpl();
    private PenaltyDao penaltyDao = new PenaltyDaoImpl();

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    public void setBorrowRecordDao(BorrowRecordDao borrowRecordDao) {
        this.borrowRecordDao = borrowRecordDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setCardDao(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public void setReturnRecordDao(ReturnRecordDao returnRecordDao) {
        this.returnRecordDao = returnRecordDao;
    }

    public void setPenaltyDao(PenaltyDao penaltyDao) {
        this.penaltyDao = penaltyDao;
    }

    // 实例化UserServiceImpl的时候获取一个数据库连接对象
    public UserServiceImpl() {
        this.conn = JdbcUtils.getConnection();
    }

    @Override
    public User register(User user) throws SQLException {
        User u = findByAccount(user.getAccount());
        if(u!=null){
            return null; // 查询到用户，表示不允许注册，注册失败返回null
        }
        System.out.println("用户不存在，可以注册, 调用UserServiceImple insert()方法");
        return userDao.insert(conn,user);
    }

    @Override
    public User findByAccount(String account) throws SQLException{
        return userDao.selectByAccount(conn,account);
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
        return bookDao.selectAll(conn);
    }

    @Override
    public User findByAccountAndPassword(String account, String pwd) throws SQLException {
        User u = userDao.selectByAccountAndPassword(conn,account,pwd);
        System.out.println("UserServiceImpl.findByAccountAndPassword: "+u);
        return u;
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
     * 检查重复借同一本书
     * 禁止用一个用户在相同时间点 借相同的书
     * @return 如果已经存在相同的借书记录，返回BorrowRecord对象
     */
    public BorrowRecord checkDupilicateBorrow(Card card, Book book) throws SQLException {
        BorrowRecord br = borrowRecordDao.selectByBookIdCardIdBorrowTime(conn,book.getBook_id()
                , card.getCard_id(), TimestampUtils.getBorrowTime().toString());
        return br;
    }

    public Integer checkCardByUserId(User user) throws SQLException{
        CallableStatement cstmt = conn.prepareCall("{call checkcard(?)}");
        if(user.getUser_id()==null)
            return null;
        cstmt.setInt(1,user.getUser_id());
        ResultSet rs = cstmt.executeQuery();
        if(rs.next())
            return rs.getInt("card_id");
        return null;
    }

    /**
     * 用户借书，包括两个操作，1.创建借书记录Record对象 2.
     * 如果书的数量少于或等于0，或者如果借书卡的额度少于或等于0 => 借书失败
     * @return 如果借书成功返回true，如果失败返回false
     * */
    public BorrowRecord borrowBook(User user, Card card, Book book) throws SQLException {
        BorrowRecord borrowRecord = null;
        try{
            conn.setAutoCommit(false);
            StringBuilder sb = new StringBuilder();
            Integer quantity = book.getQuantity(); // 书的数量
            Short quota = card.getQuota(); // 借书卡的额度
            if(quantity <= 0 || quota <= 0)
                return null;
            book.setQuantity(quantity-1); // 书的数量-1
            card.setQuota((short) (quota-1)); // 借书额度-1
            bookDao.update(conn, book);
            cardDao.update(conn,card);
            BorrowRecord record = new BorrowRecord();
            record.setBook_id(book.getBook_id());
            record.setCard_id(card.getCard_id());
            record.setBorrow_time(TimestampUtils.getBorrowTime().toString());
            int days = user.getType_id()==1?21:14; // 用户类型是1：表示学生，可借书21天，否则是教师，可借书14天
            record.setReturn_time(TimestampUtils.getReturnTime(days).toString());
            borrowRecord = borrowRecordDao.insert(conn,record);
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
        return borrowRecord;
    }

    /**
     * 获得用户使用的借书卡的所有借阅记录
     * @param card 借书卡对象
     * @return 包含所有记录的集合
     * */
    public List<BorrowRecord> getAllBorrowRecords(Card card) throws SQLException{
        return borrowRecordDao.selectByCardId(conn, card);
    }

    /**
     * 还书：创建一条换书记录
     */
    public ReturnRecord returnBook(BorrowRecord borrowRecord) throws SQLException{
        Book book = bookDao.findByBookId(conn,borrowRecord.getBook_id());
        Card card = cardDao.selectCardByCardId(conn,borrowRecord.getCard_id());
        if(book==null || card==null)
            return null;
        book.setQuantity(book.getQuantity()+1); // 如果还书成功，书的数量+1
        card.setQuota((short) (card.getQuota()+1)); // 借书卡的限额+1
        if(bookDao.update(conn, book)==null || cardDao.update(conn,card)==null)
            return null;
        ReturnRecord returnRecord = new ReturnRecord();
        returnRecord.setBook_id(borrowRecord.getBook_id());
        returnRecord.setCard_id(borrowRecord.getCard_id());
        returnRecord.setBorrow_time(borrowRecord.getBorrow_time());
        returnRecord.setReturn_time(TimestampUtils.getBorrowTime().toString()); // getBorrowTime返回的是当前时间
        return returnRecordDao.insert(conn,returnRecord);
    }

    public List<Penalty> showPenalties(Integer card_id) throws SQLException{
        return penaltyDao.findByCardId(conn,card_id);
    }

    public Card updateCard(Card card) throws SQLException{
        return cardDao.update(conn,card);
    }
}
