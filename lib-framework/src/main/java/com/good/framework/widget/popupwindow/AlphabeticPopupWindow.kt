package com.good.framework.widget.popupwindow

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import com.god.uikit.utils.dip2Px
import com.god.uikit.utils.screenSize
import com.good.framework.R
import com.good.framework.adapter.AlphabeticAdapter
import com.good.framework.commons.BaseActivity
import com.good.framework.databinding.PopupAlphabeticBinding
import com.good.framework.db.LicensePlateDao
import com.good.framework.http.entity.LicensePlate
import com.good.framework.presenter.AlphabeticListener

class AlphabeticPopupWindow(context: Context)  : PopupWindow(),AlphabeticListener {

    private var activity : BaseActivity<*,*>? = null;
    private lateinit var dataBinding : PopupAlphabeticBinding;
    private lateinit var adapter : AlphabeticAdapter;
    private var list : MutableList<String>? = null;
    private var onAlphabeticListener : OnAlphabeticListener? = null;

    constructor(activity: BaseActivity<*, *>, list:MutableList<String>?) : this(activity) {
        this.activity = activity;
        this.list = list;
        initView();
    }

    private fun initView(){
        dataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.popup_alphabetic,null,false);
        // 设置视图
        this.contentView = dataBinding?.root;

        // 设置外部可点击
        this.isOutsideTouchable = false
        // 设置弹出窗体可点击
        this.isFocusable = true
        var color :Int = 0xFF000000.toInt();
        val dw = ColorDrawable(color)
        // 设置弹出窗体的背景
        setBackgroundDrawable(dw)

        adapter = AlphabeticAdapter(list,activity);
        adapter.setPresenter(this)
        adapter.list?.let {
            var size = adapter?.list.size;
            var line : Int = 0;
            if(size % 6 == 0){
                line = size/6;
            }else{
                line = size/6 + 1;
            }
            width = screenSize(activity!!)[0];
            var tempWidth = (width-5.dip2Px())/6;
            height = tempWidth * line;
        }
        dataBinding.adapter = adapter;
    }

    fun notifyData(list: MutableList<String>){
        adapter.list = list;
        adapter.list?.let {
            var size = adapter?.list.size;
            var line : Int = 0;
            if(size % 6 == 0){
                line = size/6;
            }else{
                line = size/6 + 1;
            }
            width = screenSize(activity!!)[0];
            var tempWidth = (width-5.dip2Px())/6;
            height = tempWidth * line;
        }
        adapter.notifyDataSetChanged();
    }

    fun show(view : View){
        showAsDropDown(view,0,0, Gravity.TOP);
    }

    fun setOnAlphabeticListener(onAlphabeticListener: OnAlphabeticListener){
        this.onAlphabeticListener = onAlphabeticListener;
    }



    interface OnAlphabeticListener{

        fun onAlphabetic(licensePlate: String?);
    }

    override fun onAlphabetic(alphabetic: String?) {
        dismiss();
        if(onAlphabeticListener != null){
            onAlphabeticListener!!.onAlphabetic(alphabetic);
        }
    }
}