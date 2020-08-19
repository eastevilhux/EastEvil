package com.good.framework.widget.popupwindow

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.widget.PopupWindow
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import com.god.uikit.R
import com.god.uikit.entity.Item
import com.god.uikit.presenter.MenuPresenter
import com.god.uikit.utils.dip2Px
import com.god.uikit.utils.screenSize
import com.god.uikit.widget.window.RightMenuPopupWindow
import com.good.framework.commons.BaseActivity

class AbbreviationPopupWindow (activity : BaseActivity<*,*>) : PopupWindow()  {
    private var activity : BaseActivity<*,*>? = null;
    companion object{
        const val TAG = "AbbreviationPopupWindow==";
    }

    init {
        this.activity = activity;
        initView();
    }


    private fun initView(){
        // 设置外部可点击
        this.isOutsideTouchable = false
        // 设置弹出窗体可点击
        this.isFocusable = true

        val dw = ColorDrawable(0x00000000)
        // 设置弹出窗体的背景
        setBackgroundDrawable(dw)
    }
}