package com.mycompany.model.dao;

import com.mycompany.model.bean.Card;
import com.mycompany.model.bean.Record;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RecordDao extends BaseDao<Record>{
    Record insert(Connection conn, Record record) throws SQLException;
    List<Record> selectByCardId(Connection connection, Card card) throws SQLException;
}
