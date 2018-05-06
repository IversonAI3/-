package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.CardService;
import com.mycompany.model.bean.Card;
import com.mycompany.model.dao.BaseDao;
import com.mycompany.model.dao.CardDao;
import com.mycompany.model.dao.impl.CardDaoImpl;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class CardServiceImpl implements CardService{
    private CardDao cardDao = new CardDaoImpl();
    private Connection connection ;

    public CardServiceImpl() {
        connection = JdbcUtils.getConnection();
    }

    @Override
    public Card createCard() {
        try {
            Card card = cardDao.insertCard(connection);
            System.out.println(card);
            return card;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Card getCardById(int card_id) {
        try {
            return cardDao.selectCardByCardId(connection,card_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
