package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.ReturnRecord;
import com.mycompany.model.dao.ReturnRecordDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReturnRecordDaoImpl extends BaseDaoImpl<ReturnRecord> implements ReturnRecordDao{
    @Override
    public ReturnRecord insert(Connection conn, ReturnRecord returnRecord) throws SQLException {
        String insert = "INSERT INTO `returnrecord`(book_id,card_id,borrow_time,return_time) VALUES(?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(insert);
        ps.setInt(1,returnRecord.getBook_id());
        ps.setInt(2,returnRecord.getCard_id());
        ps.setString(3,returnRecord.getBorrow_time());
        ps.setString(4,returnRecord.getReturn_time());
        int i = ps.executeUpdate();
        return i==1?returnRecord:null;
    }

    @Override
    public ReturnRecord update(Connection conn, ReturnRecord returnRecord) throws SQLException {
        return null;
    }

    @Override
    public ReturnRecord delete(Connection conn, ReturnRecord returnRecord) throws SQLException {
        return null;
    }

    @Override
    public ReturnRecord find(Connection conn, ReturnRecord returnRecord) throws SQLException {
        return null;
    }
}
