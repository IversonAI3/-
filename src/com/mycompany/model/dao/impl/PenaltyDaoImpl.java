package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.Penalty;
import com.mycompany.model.dao.PenaltyDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PenaltyDaoImpl extends BaseDaoImpl<Penalty> implements PenaltyDao{
    @Override
    public Penalty insert(Connection conn, Penalty penalty) throws SQLException {
        PreparedStatement ps = conn
                .prepareStatement("INSERT INTO `penalty`(book_id,card_id,borrow_time,return_id,fine) " +
                        "VALUES(?,?,?,?,?)");
        ps.setInt(1,penalty.getBook_id());
        ps.setInt(2,penalty.getCard_id());
        ps.setString(3,penalty.getBorrow_time());
        ps.setInt(4,penalty.getReturn_id());
        ps.setDouble(5,penalty.getFine());
        int i = ps.executeUpdate();
        return i==1?penalty:null;
    }

    @Override
    public Penalty update(Connection conn, Penalty penalty) throws SQLException {
        return null;
    }

    @Override
    public Penalty delete(Connection conn, Penalty penalty) throws SQLException {
        return null;
    }

    @Override
    public Penalty find(Connection conn, Penalty penalty) throws SQLException {
        return null;
    }
}
