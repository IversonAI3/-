package com.mycompany.model.dao;

import com.mycompany.model.bean.Penalty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PenaltyDao extends BaseDao<Penalty>{
    List<Penalty> findByCardId(Connection conn, Integer card_id) throws SQLException;
}
