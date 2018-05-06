package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.Card;
import com.mycompany.model.dao.CardDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardDaoImpl extends BaseDaoImpl<Card> implements CardDao{
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

    @Override
    public Card selectCardByCardId(Connection connection, int card_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM `card` WHERE `card_id`=?");
        ps.setInt(1,card_id);
        ResultSet rs = ps.executeQuery();
        Card card = null;
        if(rs.next()){
            card = new Card();
            card.setCard_id(rs.getInt("card_id"));
            card.setBalance(rs.getDouble("balance"));
            card.setQuota(rs.getShort("quota"));
            card.setAmount(rs.getShort("amount"));
        }
        return card;
    }

    @Override
    public Card insert(Connection conn, Card card) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `Card` VALUES(DEFAULT, DEFAULT, DEFAULT, DEFAULT);");
        conn.createStatement().executeUpdate(sb.toString());
        return selectMostRecentCard(conn);
    }

    @Override
    public Card update(Connection conn, Card card) throws SQLException {
        String update = "UPDATE `card` SET `balance`=?, `amount`=?, `quota`=? WHERE `card_id`=?;";
        PreparedStatement ps = conn.prepareStatement(update);
        ps.setDouble(1, card.getBalance());
        ps.setShort(2,card.getAmount());
        ps.setShort(3,card.getQuota());
        ps.setInt(4,card.getCard_id());
        int num =ps.executeUpdate();
        return num==1?card:null;
    }

    @Override
    public Card delete(Connection conn, Card card) throws SQLException {
        return null;
    }

    @Override
    public Card find(Connection conn, Card card) throws SQLException {
        return null;
    }
}
