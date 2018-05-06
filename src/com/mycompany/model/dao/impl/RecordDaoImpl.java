package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.Card;
import com.mycompany.model.bean.Record;
import com.mycompany.model.dao.CardDao;
import com.mycompany.model.dao.RecordDao;
import com.mycompany.model.dao.TimestampUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RecordDaoImpl extends BaseDaoImpl<Record> implements RecordDao{

    @Override
    public Record insert(Connection conn, Record record) throws SQLException {
        // INSERT INTO `record` VALUES(record_id, card_id, book_id, borrow_time, return_time);
        String insert = "INSERT INTO `record`(card_id, book_id, borrow_time, return_time)" +
                "VALUES(?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setInt(1,record.getCard_id());
        ps.setInt(2,record.getBook_id());
        ps.setString(3, record.getBorrow_time());
        ps.setString(4,record.getReturn_time());
//        ps.setTimestamp(4,get);
        int rows = ps.executeUpdate();
        if(rows==0) // 如果受影响的行数为0，表示插入失败
            return null;
        return record;
    }

    @Override
    public List<Record> selectByCardId(Connection connection, Card card) throws SQLException {
        String sql = "SELECT * FROM `record` WHERE `card_id`=?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,card.getCard_id());
        ResultSet rs = ps.executeQuery();
        List<Record> recordList = new LinkedList<>();
        Record record;
        while(rs.next()){
            record = new Record();
            record.setCard_id(rs.getInt("card_id"));
            record.setBook_id(rs.getInt("book_id"));
            record.setRecord_id(rs.getInt("record_id"));
            record.setBorrow_time(String.valueOf(rs.getTimestamp("borrow_time")));
            record.setReturn_time(String.valueOf(rs.getTimestamp("return_time")));
            recordList.add(record);
        }
        return recordList;
    }

    @Override
    public Record update(Connection conn, Record record) throws SQLException {
        return null;
    }

    @Override
    public Record delete(Connection conn, Record record) throws SQLException {
        return null;
    }


    @Override
    public Record find(Connection conn, Record record) throws SQLException {
        return null;
    }
}
