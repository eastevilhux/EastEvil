package com.god.uikit.entity;

import android.provider.CalendarContract;

import java.io.Serializable;
import java.util.Objects;

public class DateTime implements Serializable {

    public static final int TYPE_WEEK = 1;

    public static final int TYPE_DATE = 2;

    public static final int STATE_USED = 1;

    public static final int STATE_UNUSED = 2;

    private int year;

    private int month;

    private int day;

    private int hour;

    private int minute;

    private int second;

    private int week;

    private int type;

    private int status;

    private String dayTime;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDayTime() {
        if(type == TYPE_WEEK){
            switch (week){
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
                    return "WHAT??";
            }
        }else{
            return String.valueOf(day);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateTime dateTime = (DateTime) o;
        return year == dateTime.year &&
                month == dateTime.month &&
                day == dateTime.day &&
                hour == dateTime.hour &&
                minute == dateTime.minute &&
                second == dateTime.second &&
                week == dateTime.week &&
                type == dateTime.type &&
                status == dateTime.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day, hour, minute, second, week, type, status);
    }
}
