package com.god.uikit.utils

import java.text.SimpleDateFormat
import java.util.*

fun currentTimeMillis() : Long {
    return System.currentTimeMillis();
}

fun currentTime() : String{
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(Date());
}

fun currentDate() : Date{
    return Date();
}

/**
 * 获取前一个月最后一天
 */
fun endMonthDay() : Int{
    val c = Calendar.getInstance()
    c[Calendar.DAY_OF_MONTH] = 0
    return c.get(Calendar.DAY_OF_MONTH);
}

/**
 * 获取指定时间对应的月份最后一天
 */
fun endMonthDay(dateTime : String): Int {
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    val c = Calendar.getInstance()
    c.time = sdf.parse(dateTime);
    c[Calendar.MONTH] = c[Calendar.MONTH] + 1;
    c[Calendar.DAY_OF_MONTH] = 0
    return c.get(Calendar.DAY_OF_MONTH);
}

fun currentWeek(): Int {
    val c = Calendar.getInstance()
    var week = c[Calendar.DAY_OF_WEEK];
    if(week == 1){
        return 7;
    }else{
        return week-1;
    }
}

fun week(dateTime: String): Int {
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    val c = Calendar.getInstance()
    c.time = sdf.parse(dateTime);
    var week = c[Calendar.DAY_OF_WEEK];
    if(week == 1){
        return 7;
    }else{
        return week-1;
    }
}

fun weekStr() : String{
    var week = currentWeek();
    when(week){
        1->return "星期一";
        2->return "星期二";
        3->return "星期三";
        4->return "星期四";
        5->return "星期五";
        6->return "星期六";
        7->return "星期七";
        else->throw IllegalAccessException("unknow week");
    }
}

fun weekStr(dateTime: String) : String{
    var week = week(dateTime);
    when(week){
        1->return "星期一";
        2->return "星期二";
        3->return "星期三";
        4->return "星期四";
        5->return "星期五";
        6->return "星期六";
        7->return "星期七";
        else->throw IllegalAccessException("unknow week");
    }
}

fun main(args: Array<String>) {
    System.out.println(weekStr("2020-08-21 22:1:1"))
}