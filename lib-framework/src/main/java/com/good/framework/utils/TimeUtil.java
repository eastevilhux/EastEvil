package com.good.framework.utils;
////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                  //
////////////////////////////////////////////////////////////////////

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TO DO
 * createTime : 2018/6/5 17:33
 * update by admin on 2018/6/5.
 * version : $VARIABLE_NAME
 *
 * @see
 */

public class TimeUtil {

    private static final String NOR_FORMAT = "yyyy-MM-dd HH:mm:ss";



    private final static long YEAR = 1000 * 60 * 60 * 24 * 365L;
    private final static long MONTH = 1000 * 60 * 60 * 24 * 30L;
    private final static long DAY = 1000 * 60 * 60 * 24L;
    private final static long HOUR = 1000 * 60 * 60L;
    private final static long MINUTE = 1000 * 60L;

    /**
     * 获取当前时间,格式:yyyy-MM-dd HH:mm:ss
     * @author admin
     * @createTime 2018/6/5 17:38
     * @since 0.0.1
     * @return
     *      yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat(NOR_FORMAT);
        return sdf.format(new Date());
    }

    /**
     * 根据指定时间格式获取当前时间
     * @author hux
     * @createTime 2018/6/5 17:39
     * @since 0.0.1
     * @param format
     *      指定时间格式字符串
     * @return
     *      当前时间对应的指定时间字符串
     */
    public static String getCurrentTime(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 将时间戳格式的字符串转换未指定格式的实时间字符串
     * @author hux
     * @createTime 2018/6/19 16:10
     * @since 0.0.1
     * @param time
     *      时间戳字符串
     */
    public static String formatTime(String time){
        if(time == null)
            return time;
        SimpleDateFormat sdf = new SimpleDateFormat(NOR_FORMAT);
        return sdf.format(new Date(Long.parseLong(time)));
    }

    /**
     * 将时间戳格式的字符串转换未指定格式的实时间字符串
     * @author hux
     * @createTime 2018/6/19 16:10
     * @since 0.0.1
     * @param longTime
     *      时间戳字符串
     * @param format
     *      指定时间格式
     * @return
     *      转换后的时间字符串
     */
    public static String formatTime(String longTime,String format){
        if(StringUtil.isEmpty(longTime)){
            return longTime;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(StringUtil.isEmpty(longTime)){
            return sdf.format(new Date());
        }else{
            return sdf.format(new Date(Long.parseLong(longTime)));
        }
    }

    /**
     * 将Date类型的时间格式转换为指定格式的时间字符串
     * @author hux
     * @createTime 2018/12/31 1:35
     * @since 1.0.0
     * @param date
     *      {@link Date}
     * @param format
     *      指定时间格式字符串
     * @return
     *      void
     */
    public static String formatTime(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    public static long formatTimeToLong(String time){
        SimpleDateFormat sdf = new SimpleDateFormat(NOR_FORMAT);
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static long formatTimeToLong(String time,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式时间的字符串转换为指定格式的时间字符串
     * @author hux
     * @createTime 2018/12/5 13:42
     * @since 1.0.0
     * @param time
     *      yyyy-MM-dd HH:mm:ss格式字符串
     * @param format
     *      指定的格式
     * @return
     *      指定格式的时间字符串
     */
    public static String parseTime(String time,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(NOR_FORMAT);
        try {
            return sdf.format(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }


    /**
     * 将字符串按指定格式转换为指定格式的时间字符串
     * @author hux
     * @createTime 2018/12/5 13:42
     * @since 1.0.0
     * @param time
     *      yyyy-MM-dd HH:mm:ss格式字符串
     * @param format
     *      指定的格式
     * @return
     *      指定格式的时间字符串
     */
    public static String parseTime(String time,String oldFormat,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        try {
            Date date = sdf.parse(time);
            sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String paraseLongTime(long time){
        SimpleDateFormat sdf = new SimpleDateFormat(NOR_FORMAT);
        Date date = new Date(time);
        return sdf.format(date);
    }

    public static String paraseLongTime(String format,long time){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(time);
        return sdf.format(date);
    }



    /**
     * 根据毫秒时间戳来格式化字符串,今天显示今天、昨天显示昨天、前天显示前天.<br/>
     * 早于前天的显示具体年-月-日，如2017-06-12；
     * @author hux
     * @createTime 2018/6/22 16:38
     * @since 0.0.1
     * @param timeStamp
     *      时间戳字符串
     * @return
     *      时间显示文本
     */
    public static String formatTime(long timeStamp) {
        long curTimeMillis = System.currentTimeMillis();
        Date curDate = new Date(curTimeMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        int todayHoursSeconds = calendar.get(Calendar.HOUR) * 60 * 60;
        int todayMinutesSeconds = calendar.get(Calendar.MINUTE) * 60;
        int todaySeconds = calendar.get(Calendar.SECOND);
        int todayMillis = (todayHoursSeconds + todayMinutesSeconds + todaySeconds) * 1000;
        long todayStartMillis = curTimeMillis - todayMillis;
        if(timeStamp >= todayStartMillis) {
            return "今天";
        }
        int oneDayMillis = 24 * 60 * 60 * 1000;
        long yesterdayStartMilis = todayStartMillis - oneDayMillis;
        if(timeStamp >= yesterdayStartMilis) {
            return "昨天";
        }
        long yesterdayBeforeStartMilis = yesterdayStartMilis - oneDayMillis;
        if(timeStamp >= yesterdayBeforeStartMilis) {
            return "前天";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return  sdf.format(new Date(timeStamp));
    }

    /**
     * 获取星期日期
     * @author hux
     * @createTime 2018/7/6 14:11
     * @since TimeUtil
     * @return
     */
    public static String getWeekStr(){
        Calendar calendar = Calendar.getInstance();
        int week =  calendar.get(Calendar.DAY_OF_WEEK)+1;
        switch (week){
            case 0:
                return "星期一";
            case 1:
                return "星期二";
            case 2:
                return "星期三";
            case 3:
                return "星期四";
            case 4:
                return "星期五";
            case 5:
                return "星期六";
            case 6:
                return "星期日";
            default:
                return "星期一";
        }
    }

    /**
     * 获取某年某月一共有多少天
     * @author hux
     * @createTime 2018/7/13 14:16
     * @since 0.0.1
     * @param year
     *      年份
     * @param month
     *      月份
     * @return
     *      指定月份一共有多少天
     */
    public static int getDayOfMonth(int year,int month){
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        return c.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 根据指定的年份和月份获取当月第一天为星期几，下标以0开始，即获取的星期数需要减1才为正常显示的数据
     * @author hux
     * @createTime 2019/4/10 13:35
     * @since 1.0.0
     * @param year
     *      年份
     * @param month
     *      月份
     * @return
     *      指定月份第一天所属的星期数值
     */
    public static int getMonthStartWeek(int year,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前月份第一天为星期几，下标以0开始，即获取的星期数需要减1才为正常显示的数据
     * @author hux
     * @createTime 2019/4/10 13:48
     * @since 1.0.0
     * @return
     *      本月第一天所属的星期数值
     */
    public static int getCurrentMonthStartWeek(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 将int类型星期值转换为字节类型星期字符串
     * @author hux
     * @createTime 2019/4/10 13:18
     * @since 1.0.0
     * @param week
     *      星期[1-7]
     * @return
     *      星期字符串
     */
    public static String intWeekToString(int week){
        switch (week) {
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "日";
            default:
                return "一";
        }
    }

    /**
     * 计算传入的时间距离当前时间间隔多久
     * @author hux
     * @createTime 2019/4/23 17:52
     * @since 1.0.0
     * @param time
     *      时间
     * @return
     *       距离多久
     */
    public static String natureTime(long time){
        Date date = new Date(time);
        return natureTime(date);
    }

    /**
     * 计算传入的时间距离当前时间间隔多久
     * @author hux
     * @createTime 2019/4/23 17:52
     * @since 1.0.0
     * @param time
     *      时间
     * @return
     *       距离多久
     */
    public static String natureLongTime(String time){
        Date date = new Date(Long.parseLong(time));
        return natureTime(date);
    }


    /**
     * 计算传入的时间距离当前时间间隔多久
     * @author hux
     * @createTime 2019/4/23 17:52
     * @since 1.0.0
     * @param date
     *      时间
     * @return
     *       距离多久
     */
    public static String natureTime(Date date){
        Date now = new Date();
        long between = now.getTime() - date.getTime();
        StringBuilder sb = new StringBuilder(32);
        if (between > YEAR){
            return ((between - YEAR) / YEAR + 1) + "年前";
        }
        if (between > MONTH){
            return ((between - MONTH) / MONTH + 1) + "月前";
        }
        if (between > DAY){
            return ((between - DAY) / DAY + 1) + "天前";
        }
        if (between > HOUR){
            return ((between - HOUR) / HOUR + 1) + "小时前";
        }
        if (between > MINUTE){
            return ((between - MINUTE) / MINUTE + 1) + "分钟前";
        }
        return "刚刚";
    }


    public static String timeLess(String time1,String time2){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(sdf.parse(time1));
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(sdf.parse(time2));
            long time = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
            return formatDuring(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算两个时间之间距离多久,时间1必须在时间2之后
     * @author hux
     * @createTime 2019/7/16 14:42
     * @since 1.0.0
     * @param time1
     *      时间1
     * @param time2
     *      时间2
     * @return
     *      x天x小时x分x秒
     */
    public static String timeLess(long time1,long time2){
        long time = time1 - time2;
        return formatDuring(time);
    }

    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        StringBuilder sb = new StringBuilder(128);
        if(days > 0){
            sb.append(days).append("天");
        }
        if(hours > 0){
            sb.append(hours).append("小时");
        }
        if(minutes > 0){
            sb.append(minutes).append("分");
        }
        if(seconds > 0){
            sb.append(seconds).append("秒");
        }
        return sb.toString();
    }
}
