package com.god.uikit.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.god.uikit.R;
import com.god.uikit.databinding.DialogCalendarBinding;
import com.god.uikit.entity.DateTime;
import com.god.uikit.utils.TimeExtKt;
import com.god.uikit.utils.ViewUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarDialog extends Dialog {
    private DialogCalendarBinding dataBinding;
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
            dataBinding.setDate(date);
            dataBinding.setTime(time);
            dataBinding.setWeek(week);
        }
        countSize();
    }


    private void countSize(){
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        lp.width = ViewUtil.Companion.getScreenSize(getContext())[0] -
                ViewUtil.Companion.dip2px(getContext(),50);

        lp.height = ViewUtil.Companion.dip2px(getContext(),300);
        //lp.alpha = 0.7f; // 透明度
        //lp.alpha = 0.7f; // 透明度
        window.setAttributes(lp);
    }


    private void queryDateList(Date date){
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
            dt.setType(DateTime.TYPE_WEEK);
            dateList.add(dt);
            i++;
        }
        //根据日期计算上个月最后一天
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
