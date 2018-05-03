package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.Card;
import com.mycompany.model.dao.CardDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardDaoImpl extends BaseDaoImpl implements CardDao{
    @Override
    public Card insertCard(Connection connection) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `Card` VALUES(DEFAULT, DEFAULT, DEFAULT, DEFAULT);");
        connection.createStatement().executeUpdate(sb.toString());
        return selectMostRecentCard(connection);
    }

    @Override
    public Card selectMostRecentCard(Connection connection) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT card_id, balance, amount, quota\n" +
                "FROM `card` \n" +
                "WHERE card_id = (SELECT MAX(card_id) FROM `card`);";
        System.out.println(sql);
        ResultSet rs =  connection.createStatement().executeQuery(sql);
        System.out.println("结束");
        Card card = new Card();
        rs.next();
        card.setCard_id(rs.getInt("card_id"));
        card.setAmount(rs.getShort("amount"));
        card.setBalance(rs.getDouble("balance"));
        card.setQuota(rs.getShort("quota"));
        System.out.println(card);
        return card;
    }
}
