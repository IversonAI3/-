package com.mycompany.model.dao;

import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.BorrowRecord;
import com.mycompany.model.bean.Card;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BorrowRecordDao extends BaseDao<BorrowRecord>{
    BorrowRecord insert(Connection conn, BorrowRecord record) throws SQLException;
    List<BorrowRecord> selectByCardId(Connection connection, Card card) throws SQLException;
    List<BorrowRecord> selectByBookId(Connection connection, Integer book_id) throws SQLException;
    /**
     * 通过book_id, card_id和borrow_time来查询一个借书记录，只能查到一个
     * */
    BorrowRecord selectByBookIdCardIdBorrowTime(Connection connection, Integer book_id, Integer card_id, String borrow_time) throws SQLException;

    List<BorrowRecord> selectUnReturnedRecords(Connection conn) throws SQLException;

    List<BorrowRecord> selectReturnedRecords(Connection conn) throws SQLException;
}
