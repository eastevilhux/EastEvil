package com.god.uikit.widget.window

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import com.god.uikit.R
import com.god.uikit.adapter.PopupAdapter
import com.god.uikit.databinding.PopupRightListMenuBinding
import com.god.uikit.entity.Item
import com.god.uikit.presenter.MenuPresenter
import com.god.uikit.utils.dip2Px


class RightMenuPopupWindow(context: Context) : PopupWindow() {
    companion object{
        const val TAG = "RightMenuPopupWindow";
    }
    private var dataBinding : PopupRightListMenuBinding? = null;

    private var adapter : PopupAdapter? = null;

    private var presenter : MenuPresenter? = null

    var myHeight : Int = 0;

    constructor(context: Context,presenter : MenuPresenter?,itemList:MutableList<Item>) : this(context) {
        this.context = context;
        this.itemList = itemList;
        this.presenter = presenter;
        init()
    }

    constructor(context: Context, presenter : MenuPresenter?, @StringRes vararg items: Int) : this(context) {
        this.context = context;
        var list = mutableListOf<Item>();
        items?.forEach {
            list.add(Item(0,it));
        }
        this.itemList = list;
        this.presenter = presenter;
        init()
    }

    fun init(){
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.popup_right_list_menu,null,false);
        // 设置视图
        this.contentView = dataBinding?.root;
        var size = itemList?.size?:0;
        var dp30 = 35.dip2Px();
        myHeight = dp30*size;
        Log.d(TAG,myHeight.toString());

        initList();
        initView();
    }


    var context : Context? = null;
    var itemList : MutableList<Item>? = null;


    private fun initView(){
        // 设置外部可点击
        this.isOutsideTouchable = false

        // 设置弹出窗体的宽和高
        this.width = 80.dip2Px();
        this.height = myHeight;

        // 设置弹出窗体可点击
        this.isFocusable = true

        val dw = ColorDrawable(0x00000000)
        // 设置弹出窗体的背景
        setBackgroundDrawable(dw)
    }

    private fun initList(){
        var params = dataBinding?.menuListview!!.layoutParams;
        params?.let {
            params.height = myHeight;
        }
        adapter = PopupAdapter(itemList,context!!);
        adapter?.presetern = presenter;
        dataBinding?.adapter = adapter;
    }


    fun show(view:View){
        showAsDropDown(view,8.dip2Px(),5.dip2Px(),Gravity.RIGHT)
    }
}
