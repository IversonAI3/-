package com.mycompany.controller.services;

import com.mycompany.model.bean.Card;

public interface CardService {
    /**
     * 这个方法为用户创建一个借书卡
     * */
    Card createCard();
    Card getCardById(int card_id);
}
