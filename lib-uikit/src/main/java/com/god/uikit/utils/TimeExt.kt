package com.god.uikit.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun currentTimeMillis() : Long {
    return System.currentTimeMillis();
}

fun currentTime() : String{
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(Date());
}


fun currentTime(sdf:String) : String{
    var sdf = SimpleDateFormat(sdf);
    return sdf.format(Date());
}


fun currentDate() : Date{
    return Date();
}


fun String.toLongTimeStamp() : Long? {
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    var date = sdf.parse(this);
    date?.let {
        return date.time;
    }?:return null;
}

fun String.toLongTimeStamp(sdf:String): Long? {
    var sdf = SimpleDateFormat(sdf);
    var date = sdf.parse(this);
    date?.let {
        return date.time;
    }?:return null;
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
        week = 7;
    }else{
        week -= 1;
    }
    return week;
}

fun week(dateTime: String): Int {
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    val c = Calendar.getInstance()
    c.time = sdf.parse(dateTime);
    var week = c[Calendar.DAY_OF_WEEK];
    if(week == 1){
        week = 7;
    }else{
        week -= 1;
    }
    return week;
}

fun Long.formatTime() : String{
    var sdf = SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
    return sdf.format(Date(this));
}

fun Long.formatTime(sdf:String) : String{
    var sdf = SimpleDateFormat(sdf);
    return sdf.format(Date(this));
}

fun Date.format(): String {
    var sdf = SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
    return sdf.format(this);
}

fun Date.format(sdf:String): String {
    var sdf = SimpleDateFormat(sdf);
    return sdf.format(this);
}

/**
 * 获取指定年月第一天星期
 */
fun getMonthStartWeek(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar[Calendar.YEAR] = year
    calendar[Calendar.MONTH] = month - 1;
    calendar[Calendar.DAY_OF_MONTH] = 1
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    var time = sdf.format(calendar.time);
    return week(time);
}

/**
 * 获取指定年月最后一天星期
 */
fun getMonthEndWeek(year:Int,month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar[Calendar.YEAR] = year
    calendar[Calendar.MONTH] = month - 1;
    calendar[Calendar.DAY_OF_MONTH] = getDayOfMonth(year,month);
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    var time = sdf.format(calendar.time);
    return week(time);
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
        7->return "星期日";
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
        7->return "星期日";
        else->throw IllegalAccessException("unknow week");
    }
}

/**
 * 获取某年某月有多少天
 * @param year
 * @param month
 * @return
 */
fun getDayOfMonth(year: Int, month: Int): Int {
    val c = Calendar.getInstance()
    c[year, month] = 0 //输入类型为int类型
    return c[Calendar.DAY_OF_MONTH]
}

fun main(args: Array<String>) {
    var fuck = "asddfasdf";
    try {
        var fuckNum = fuck.toInt();
    }catch (e : Exception){
        e.printStackTrace();
        System.out.println("fucking now");
    }
}