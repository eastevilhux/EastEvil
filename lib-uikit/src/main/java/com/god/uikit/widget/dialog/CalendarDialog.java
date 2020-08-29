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

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.god.uikit.R;
import com.god.uikit.adapter.DayAdapter;
import com.god.uikit.databinding.DialogCalendarBinding;
import com.god.uikit.entity.DateTime;
import com.god.uikit.presenter.DateTimePresenter;
import com.god.uikit.utils.TimeExtKt;
import com.god.uikit.utils.ViewUtil;

import org.jetbrains.annotations.NotNull;

import java.sql.Array;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarDialog extends Dialog implements DateTimePresenter {

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
     * 是否包含秒
     */
    private boolean containSecond = false;

    private ObservableField<Date> date;
    private ObservableField<String> time;
    private ObservableField<String> week;
    private ObservableField<Boolean> haveTime;

    private List<DateTime> dateList;

    private DayAdapter dayAdapter;

    private boolean isNowMoth = false;

    private DateTime lastSelectDay;

    private CalendarDialog(@NonNull Context context,Builder builder) {
        super(context, R.style.DialogStyle);
        this.allowAfter = builder.allowAfter;
        this.allowBefor = builder.allowBefor;
        this.haveTime = new ObservableField<>(builder.haveTime);
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
        if(haveTime.get()){
            if(containSecond){
                time = new ObservableField<>(TimeExtKt.currentTime("HH:mm:ss"));
            }else{
                time = new ObservableField<>(TimeExtKt.currentTime("HH:mm"));
            }
            week = new ObservableField<>(TimeExtKt.weekStr());
            dataBinding.setTime(time);
            dataBinding.setWeek(week);

            initTime();
        }
        dayAdapter = new DayAdapter(null,getContext());
        dayAdapter.setPresenter(this);

        date = new ObservableField<>(TimeExtKt.currentDate());
        dataBinding.setDate(date);
        dataBinding.setAdapter(dayAdapter);
        dataBinding.setDialog(this);
        dataBinding.setHaveTime(haveTime);
        dialogDate = new Date();
        isNowMoth = true;
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
        if(haveTime.get()){
            height = height + ViewUtil.Companion.dip2px(getContext(),70);
        }
        lp.height = height;
        window.setAttributes(lp);
    }


    private void queryDateList(Date nowDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(dateList == null){
            dateList = new ArrayList<>();
        }else{
            dateList.removeAll(dateList);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        //对比现在得时间和传入得时间是否为同年同月
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        cal.setTime(new Date());
        int nowYear = cal.get(Calendar.YEAR);
        int nowMonth = cal.get(Calendar.MONTH);
        if(year == nowYear && month == nowMonth){
            //传入得时间为此时所对应得年月
            isNowMoth = true;
        }else{
            isNowMoth = false;
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
        month = c.get(Calendar.MONTH);
        month = month + 1;
        c.set(Calendar.MONTH,month);
        year = c.get(Calendar.YEAR);
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
        int maxDay = TimeExtKt.getDayOfMonth(y,m+1);
        int nowDay = c.get(Calendar.DAY_OF_MONTH);
        int day = 1;
        while(day <= maxDay){
            dt = new DateTime();
            dt.setType(DateTime.TYPE_DATE);
            dt.setStatus(DateTime.STATE_USED);
            dt.setDay(day);
            dt.setSelected(false);
            if(isNowMoth){
                if(day == nowDay){
                    dt.setSelected(true);
                    lastSelectDay = dt;
                }
            }
            dateList.add(dt);
            day++;
        }
        int endWeek = TimeExtKt.getMonthEndWeek(y,m+1);
        int size = 7 - endWeek;
        Log.d(TAG,"endWeek=>"+endWeek+",SIZE=>"+size);
        day = 1;
        while(day <= size){
            dt = new DateTime();
            dt.setType(DateTime.TYPE_DATE);
            dt.setStatus(DateTime.STATE_UNUSED);
            dt.setDay(day);
            dateList.add(dt);
            day++;
        }
        dayAdapter.setList(dateList);
        dayAdapter.notifyDataSetChanged();
    }

    private void initTime(){
        List<String> hourList = new ArrayList<>(24);
        List<String> minuteList = new ArrayList<>(60);

    }


    public void onViewClick(View view){
        if(view.getId() == R.id.iv_lastmonth){
            if(!allowBefor){
                return;
            }
            if(lastSelectDay != null){
                lastSelectDay.setSelected(false);
            }
            Calendar c = Calendar.getInstance();
            c.setTime(dialogDate);
            c.add(Calendar.MONTH,-1);
            dialogDate = c.getTime();
            queryDateList(dialogDate);
            date.set(dialogDate);
        }else if(view.getId() == R.id.iv_nextmonth){
            if(!allowAfter){
                return;
            }
            if(lastSelectDay != null){
                lastSelectDay.setSelected(false);
            }
            Calendar c = Calendar.getInstance();
            c.setTime(dialogDate);
            c.add(Calendar.MONTH,+1);
            dialogDate = c.getTime();
            queryDateList(dialogDate);
            date.set(dialogDate);
        }
    }

    @Override
    public void onDaySelect(int pos, @NotNull DateTime day) {
        if(day.getType() != DateTime.TYPE_WEEK && day.getStatus() == DateTime.STATE_USED){
            if(lastSelectDay != null){
                lastSelectDay.setSelected(false);
            }
            day.setSelected(true);
            lastSelectDay = day;
            Calendar c = Calendar.getInstance();
            c.setTime(dialogDate);
            c.set(Calendar.DAY_OF_MONTH,day.getDay());
            dialogDate = c.getTime();
            date.set(dialogDate);
            dayAdapter.notifyDataSetChanged();
        }
    }

    public static class Builder{
        private Context context;

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
