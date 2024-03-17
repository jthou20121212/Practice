package com.study.gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作者：chenZY
 * 时间：2018/3/17 18:32
 * 描述：https://github.com/leavesC
 */
public class Strategies {

    private Date date;

    private Date date2;

    public Strategies(Date date, Date date2) {
        this.date = date;
        this.date2 = date2;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.CHINA);
        return "Strategies{" +
                "date=" + simpleDateFormat.format(date) +
                ", date2=" + simpleDateFormat.format(date2) +
                '}';
    }

}
