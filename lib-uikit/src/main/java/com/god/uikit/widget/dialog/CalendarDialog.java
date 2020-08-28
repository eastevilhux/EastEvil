package com.god.uikit.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.god.uikit.R;
import com.god.uikit.adapter.DayAdapter;
import com.god.uikit.databinding.DialogCalendarBinding;
import com.god.uikit.entity.DateTime;
import com.god.uikit.utils.TimeExtKt;
import com.god.uikit.utils.ViewUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarDialog extends Dialog {

    private static final String TAG = "CalendarDialog==>";

    private DialogCalendarBinding dataBinding;

    private Date dialogDate;

    private String title;

    /**
     * 是否允许选择之前的时间
     */
    private boolean allowBefor = true;

    /**
     * 是否允许选择之后的时间
     */
    private boolean allowAfter = true;

    /**
     * 是否含有时间选择器
     */
    private boolean haveTime = false;

    /**
     * 是否包含秒
     */
    private boolean containSecond = false;

    private ObservableField<String> date;
    private ObservableField<String> time;
    private ObservableField<String> week;

    private List<DateTime> dateList;

    private DayAdapter dayAdapter;

    private CalendarDialog(@NonNull Context context,Builder builder) {
        super(context, R.style.DialogStyle);
        this.allowAfter = builder.allowAfter;
        this.allowBefor = builder.allowBefor;
        this.haveTime = builder.haveTime;
        this.containSecond = builder.containSecond;
        this.title = builder.title;
        init();
    }

    private CalendarDialog(@NonNull Context context, int themeResId, String title) {
        super(context, themeResId);
    }

    private CalendarDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, String title) {
        super(context, cancelable, cancelListener);
    }


    private void init(){
        dataBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.dialog_calendar,null,false);
        setContentView(dataBinding.getRoot());
        if(haveTime){
            date = new ObservableField<>(TimeExtKt.currentTime("yyyy-MM-dd"));
            if(containSecond){
                time = new ObservableField<>(TimeExtKt.currentTime("HH:mm:ss"));
            }else{
                time = new ObservableField<>(TimeExtKt.currentTime("HH:mm"));
            }
            week = new ObservableField<>(TimeExtKt.weekStr());
            //dataBinding.setDate(date);
            dataBinding.setTime(time);
            dataBinding.setWeek(week);
        }
        dayAdapter = new DayAdapter(null,getContext());
        dataBinding.setAdapter(dayAdapter);
        dataBinding.setDialog(this);
        dialogDate = new Date();
        queryDateList(dialogDate);
        countSize();
    }


    private void countSize(){
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        lp.width = ViewUtil.Companion.getScreenSize(getContext())[0] -
                ViewUtil.Companion.dip2px(getContext(),50);

        int size = dateList.size() % 7;
        if(size != 0){
            size = dateList.size() / 7 + 1;
        }else{
            size = dateList.size() / 7;
        }
        int width = ViewUtil.Companion.getScreenSize(getContext())[0];
        int tempSize = (width- ViewUtil.Companion.dip2px(getContext(),6))/7;
        int height = tempSize * size;
        if(haveTime){
            height = height + ViewUtil.Companion.dip2px(getContext(),70);
        }
        lp.height = height;
        window.setAttributes(lp);
    }


    private void queryDateList(Date nowDate){
        if(dateList == null){
            dateList = new ArrayList<>();
        }else{
            dateList.removeAll(dateList);
        }
        //生成星期
        int i = 1;
        DateTime dt = null;
        while(i <= 7){
            dt = new DateTime();
            dt.setWeek(i);
            dt.setType(DateTime.TYPE_WEEK);
            dateList.add(dt);
            i++;
        }
        //根据日期计算上个月最后一天
        Calendar c = Calendar.getInstance();
        c.setTime(nowDate);
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        c.set(Calendar.MONTH,month);
        int year = c.get(Calendar.YEAR);
        c.add(Calendar.MONTH, -1);
        month = c.get(Calendar.MONTH);
        int lastDay = TimeExtKt.getDayOfMonth(year,month);
        //获取传入时间开始的星期
        c.setTime(nowDate);
        int week = TimeExtKt.getMonthStartWeek(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1);
        if(week != 1){
            //当前月份第一天不为星期一，增加上个月最后得天数用以作为候补
            int size = 7 - (7-week) - 1;
            int tempDay = lastDay - size + 1;
            while(tempDay <=  lastDay){
                dt = new DateTime();
                dt.setType(DateTime.TYPE_DATE);
                dt.setStatus(DateTime.STATE_UNUSED);
                dt.setDay(tempDay);
                dateList.add(dt);
                tempDay++;
            }
        }
        c.setTime(nowDate);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int maxDay = TimeExtKt.getDayOfMonth(y,m);
        int day = 1;
        while(day <= maxDay){
            dt = new DateTime();
            dt.setType(DateTime.TYPE_DATE);
            dt.setStatus(DateTime.STATE_USED);
            dt.setDay(day);
            dateList.add(dt);
            day++;
        }
        int endWeek = TimeExtKt.getMonthEndWeek(y,m+1);
        day = 1;
        while(endWeek < 7){
            dt = new DateTime();
            dt.setType(DateTime.TYPE_DATE);
            dt.setStatus(DateTime.STATE_UNUSED);
            dt.setDay(day);
            dateList.add(dt);
            day++;
            endWeek++;
        }
        dayAdapter.setList(dateList);
        dayAdapter.notifyDataSetChanged();
    }


    public void onViewClick(View view){
        if(view.getId() == R.id.iv_lastmonth){
            Calendar c = Calendar.getInstance();
            c.setTime(dialogDate);
            c.set(Calendar.MONTH,-1);
            dialogDate = c.getTime();
            queryDateList(c.getTime());
        }else if(view.getId() == R.id.iv_nextmonth){
            Calendar c = Calendar.getInstance();
            c.setTime(dialogDate);
            c.set(Calendar.MONTH,+1);
            dialogDate = c.getTime();
            queryDateList(c.getTime());
        }
    }

    public static class Builder{
        private Context context;

        /**
         * 是否允许选择之前的时间
         */
        private boolean allowBefor;

        /**
         * 是否允许选择之后的时间
         */
        private boolean allowAfter;

        /**
         * 是否含有时间选择器
         */
        private boolean haveTime;

        /**
         * 是否包含秒
         */
        private boolean containSecond;

        private String title;

        public Builder(Context context){
            this.context = context;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder allowBefor(boolean allowBefor){
            this.allowBefor = allowBefor;
            return this;
        }

        public Builder allowAfter(boolean allowAfter){
            this.allowAfter = allowAfter;
            return this;
        }

        public Builder haveTime(boolean haveTime){
            this.haveTime = haveTime;
            return this;
        }

        public Builder containSecond(boolean containSecond){
            this.containSecond = containSecond;
            return this;
        }


        public CalendarDialog builder(){
            return new CalendarDialog(context,this);
        }

    }
}
