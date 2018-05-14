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
        sb.append("INSERT INTO `Card` VALUES(DEFAULT, DEFAULT, DEFAULT);");
        int i = connection.createStatement().executeUpdate(sb.toString());
        return i==1?selectMostRecentCard(connection):null;
    }

    @Override
    public Card selectMostRecentCard(Connection connection) throws SQLException {
        String sql = "SELECT * FROM `card` WHERE card_id = (SELECT MAX(card_id) FROM `card`);";
        ResultSet rs =  connection.createStatement().executeQuery(sql);
        Card card = null;
        if(rs.next()){
            card = new Card();
            initializeCard(rs,card);
        }
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
            initializeCard(rs,card);
        }
        return card;
    }

    @Override
    public boolean updateBalance(Connection conn, Integer card_id, Double newBalance) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE `card` SET `balance`=? WHERE card_id=?");
        ps.setDouble(1,newBalance);
        ps.setInt(2,card_id);
        int i = ps.executeUpdate();
        return i==1?true:false;
    }

    @Override
    public Card insert(Connection conn, Card card) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `Card` VALUES(DEFAULT, DEFAULT, DEFAULT);");
        int i = conn.createStatement().executeUpdate(sb.toString());
        return i==1?selectMostRecentCard(conn):null;
    }

    @Override
    public Card update(Connection conn, Card card) throws SQLException {
        String update = "UPDATE `card` SET `balance`=?, `quota`=? WHERE `card_id`=?;";
        PreparedStatement ps = conn.prepareStatement(update);
        ps.setDouble(1, card.getBalance());
        ps.setShort(2, card.getQuota());
        ps.setInt(3,card.getCard_id());
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

    private void initializeCard(ResultSet rs, Card card) throws SQLException {
        card.setCard_id(rs.getInt("card_id"));
        card.setBalance(rs.getDouble("balance"));
        card.setQuota(rs.getShort("quota"));
    }
}
