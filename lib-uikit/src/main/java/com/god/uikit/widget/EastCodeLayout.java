package com.god.uikit.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class EastCodeLayout extends ViewGroup {
    private static final String TAG = "EastCodeLayout==>";


    public EastCodeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EastCodeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        int layoutWidth = 0;    // 容器已经占据的宽度
        int layoutHeight = 0;   // 容器已经占据的宽度
        int maxChildHeight = 0; //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少
        View child = null;
        for(int i = 0; i<count; i++) {
            child = getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();
            if(layoutWidth < getWidth()){
                //如果一行没有排满，继续往右排列
                left = layoutWidth;
                right = left+childMeasureWidth;
                top = layoutHeight;
                bottom = top+childMeasureHeight;
            } else{
                //排满后换行
                layoutWidth = 0;
                layoutHeight += maxChildHeight;
                maxChildHeight = 0;

                left = layoutWidth;
                right = left+childMeasureWidth;
                top = layoutHeight;
                bottom = top+childMeasureHeight;
            }
            layoutWidth += childMeasureWidth;  //宽度累加
            if(childMeasureHeight>maxChildHeight){
                maxChildHeight = childMeasureHeight;
            }
            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension( getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }



}
