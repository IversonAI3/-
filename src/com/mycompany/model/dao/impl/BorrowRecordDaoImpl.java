package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.BorrowRecord;
import com.mycompany.model.bean.Card;
import com.mycompany.model.dao.BorrowRecordDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BorrowRecordDaoImpl extends BaseDaoImpl<BorrowRecord> implements BorrowRecordDao {

    @Override
    public BorrowRecord insert(Connection conn, BorrowRecord record) throws SQLException {
        // INSERT INTO `borrowrecord`(book_id,card_id,borrow_time,return_time) VALUES(1, 1000000, DEFAULT, DEFAULT);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO `borrowrecord` VALUES(?,?,?,?);");
        ps.setInt(1,record.getBook_id());
        ps.setInt(2,record.getCard_id());
        ps.setString(3, record.getBorrow_time());
        ps.setString(4,record.getReturn_time());
        int rows = ps.executeUpdate();
        return rows==1?record:null;
    }

    @Override
    public List<BorrowRecord> selectByCardId(Connection connection, Card card) throws SQLException {
        String sql = "SELECT * FROM `borrowrecord` WHERE `card_id`=?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,card.getCard_id());
        ResultSet rs = ps.executeQuery();
        List<BorrowRecord> recordList = new LinkedList<>();
        BorrowRecord record;
        while(rs.next()){
            record = new BorrowRecord();
            recordList.add(initializeRecord(rs,record));
        }
        return recordList;
    }

    @Override
    public List<BorrowRecord> selectByBookId(Connection connection, Integer book_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM `borrowrecord` WHERE `book_id`=?");
        ps.setInt(1, book_id);
        ResultSet rs = ps.executeQuery();
        BorrowRecord record;
        List<BorrowRecord> recordList = new LinkedList<>();
        while(rs.next()){
            record = new BorrowRecord();
            recordList.add(initializeRecord(rs,record));
        }
        return recordList;
    }

    @Override
    public BorrowRecord selectByBookIdCardIdBorrowTime(Connection connection, Integer book_id, Integer card_id, String borrow_time) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM `borrowrecord` WHERE book_id=? AND card_id=? AND borrow_time=?");
        ps.setInt(1,book_id);
        ps.setInt(2,card_id);
        ps.setString(3,borrow_time);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            BorrowRecord record = new BorrowRecord();
            return initializeRecord(rs,record);
        }
        return null;
    }

    @Override
    public BorrowRecord update(Connection conn, BorrowRecord record) throws SQLException {
        return null;
    }

    @Override
    public BorrowRecord delete(Connection conn, BorrowRecord record) throws SQLException {
        return null;
    }

    @Override
    public BorrowRecord find(Connection conn, BorrowRecord record) throws SQLException {
        return null;
    }

    private BorrowRecord initializeRecord(ResultSet rs, BorrowRecord record) throws SQLException {
        record.setCard_id(rs.getInt("card_id"));
        record.setBook_id(rs.getInt("book_id"));
        record.setBorrow_time(String.valueOf(rs.getTimestamp("borrow_time")));
        record.setReturn_time(String.valueOf(rs.getTimestamp("return_time")));
        return record;
    }
}
