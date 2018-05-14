package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.ReturnRecord;
import com.mycompany.model.dao.ReturnRecordDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    @Override
    public Integer selectMaxId(Connection conn) throws SQLException {
        String sql = "SELECT MAX(return_id) FROM returnrecord;";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        if(rs.next())
            return rs.getInt(1);
        return null;
    }
}
