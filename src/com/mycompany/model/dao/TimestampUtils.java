package com.mycompany.model.dao;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 一个静态工厂类，提供获得当前时间和还书时间的方法
 * */
public class TimestampUtils {
    private static Timestamp currentTimestamp = new Timestamp(new Date().getTime());
    private static Timestamp futureTimestamp;

    private TimestampUtils(){}

    /**
     * 获得当前借书时间
     * @return 借书时间
     * */
    public static Timestamp getBorrowTime(){
        return currentTimestamp;
    }

    /**
     * 获得还书时间
     * @param days 借书时间加上天数等于还书时间
     * @return 还书时间
     * */
    public static Timestamp getReturnTime(int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTimestamp);
        cal.add(Calendar.DAY_OF_WEEK, days);
        futureTimestamp = new Timestamp(cal.getTime().getTime());
        return futureTimestamp;
    }
}
