package com.god.uikit.widget;


import android.text.InputFilter;
import android.text.Spanned;

import com.god.uikit.widget.EastCodeView;

public class ContinuousRepeatCharInputFilter implements InputFilter {
    private CharSequence lastChar = null;

    private boolean isContinuousRepeatChar;
    private boolean isContinuous;

    private EastCodeView.InputOverListener mInputOverListener;

    public ContinuousRepeatCharInputFilter(boolean isRepeat) {
        this(isRepeat, isRepeat);
    }

    public ContinuousRepeatCharInputFilter(boolean isContinuous, boolean isContinuousRepeatChar) {
        this.isContinuousRepeatChar = isContinuousRepeatChar;
        this.isContinuous = isContinuous;
    }

    public void setInputOverListener(EastCodeView.InputOverListener mInputOverListener) {
        this.mInputOverListener = mInputOverListener;
    }


    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        if (source.length() == 0 && dend > dstart) {
            if (dstart == 0) {
                lastChar = null;
            } else {
                //删除操作
                lastChar = dest.subSequence(dstart - 1, dstart);
            }

        }

        if (source == null || source.length() <= 0) {
            return "";
        }
        //不可连续数据并且不支持连续重复数据
        if (isContinuous && isContinuousRepeatChar) {
            if (lastChar != null && (lastChar.charAt(0) == source.charAt(0) - 1 || lastChar.charAt(0) == source.charAt(0) + 1 || lastChar.charAt(0) == source.charAt(0))) {
                if (mInputOverListener != null) {
                    mInputOverListener.InputHint("不支持连续字符或连续重复字符输入");
                }

                return "";
            }
        } else if (isContinuousRepeatChar) {
            //连续不重复
            if (lastChar != null && lastChar.charAt(0) == source.charAt(0)) {
                if (mInputOverListener != null) {
                    mInputOverListener.InputHint("不支持连续重复字符输入");
                }
                return "";
            }
        } else if (isContinuous) {
            if (lastChar != null && (lastChar.charAt(0) == source.charAt(0) - 1 || lastChar.charAt(0) == source.charAt(0) + 1)) {
                if (mInputOverListener != null) {
                    mInputOverListener.InputHint("不支持连续字符输入");
                }
                return "";
            }
        }

        lastChar = source;
        return source;
    }
}
