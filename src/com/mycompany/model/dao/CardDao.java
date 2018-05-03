package com.mycompany.model.dao;

import com.mycompany.model.bean.Card;

import java.sql.Connection;
import java.sql.SQLException;

public interface CardDao extends BaseDao{
    /**
     * 这个方法会创建一个借书卡对象，不需要任何参数，全都是默认值
     * */
    Card insertCard(Connection connection) throws SQLException;
    /**
     * 用这个方法来获取刚刚创建的那个借书卡对象
     */
    Card selectMostRecentCard(Connection connection) throws SQLException;
}
