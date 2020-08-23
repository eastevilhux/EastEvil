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
import com.good.framework.adapter.LicensePlateAdapter
import com.good.framework.commons.BaseActivity
import com.good.framework.commons.mainThread
import com.good.framework.databinding.PopupAbbreviationBinding
import com.good.framework.db.LicensePlateDao
import com.good.framework.http.RetrofitFactory
import com.good.framework.http.entity.LicensePlate
import com.good.framework.presenter.LicensePresenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AbbreviationPopupWindow(context: Context)  : PopupWindow(),LicensePresenter {

    private var dataBinding : PopupAbbreviationBinding? = null;

    private var activity : BaseActivity<*,*>? = null;
    private lateinit var lpDao: LicensePlateDao;
    private lateinit var adapter : LicensePlateAdapter;
    private var view: View? = null;

    private var onAbbreviationListener : OnAbbreviationListener? = null;

    companion object{
        const val TAG = "AbbreviationPopupWindow==";
    }

    constructor(activity:BaseActivity<*,*>,lpDao: LicensePlateDao,view:View? = null) : this(activity) {
        this.activity = activity;
        this.lpDao = lpDao;
        this.view = view;
        initView();
    }



    private fun initView(){
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(activity),
           R.layout.popup_abbreviation,null,false);
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

        adapter = LicensePlateAdapter(null,activity);
        adapter.setPresenter(this)
        dataBinding!!.adapter = adapter;
        showList();
    }


    private fun showList(){
        var list = lpDao.queryBuilder().list();
        if(list.isNullOrEmpty()){
            //本地不存在数据，调用接口获取车牌数据
            activity?.showLoading();
            reqeustLicensePlateList();
        }else{
            //本地已有数据
            showLicensePlate(list);
        }
    }

    private fun reqeustLicensePlateList() = GlobalScope.launch{
        var result = RetrofitFactory.instance.baseService.licensePlate();
        mainThread {
            activity?.dismissLoading();
            if(result.isSuccess){
                //组合简介下字母列表
                result.data?.let { showLicensePlate(it) };
            }else{
                activity?.showToastShort(result.msg?:"service error");
            }
        }
    }

    private fun showLicensePlate(list : MutableList<LicensePlate>){
        adapter.list = list;
        adapter.notifyDataSetChanged();
        var size = adapter?.list.size;
        var line : Int = 0;
        if(size %8 == 0){
            line = size/8;
        }else{
            line = size/8 + 1;
        }
        width = screenSize(activity!!)[0];
        var tempWidth = (width-7.dip2Px())/8;
        height = tempWidth * line;
        show();
    }
    override fun onItemClick(licensePlate: LicensePlate?) {
        dismiss()
        licensePlate?.let {
            if(onAbbreviationListener != null){
                onAbbreviationListener!!.onAbbreviation(it);
            }
        }
    }

    fun show(view : View){
        showAsDropDown(view,0,0,Gravity.TOP);
    }

    fun show(){
        view?.let {
            show(it);
        }
    }

    fun setOnAbbreviationListener(onAbbreviationListener: OnAbbreviationListener){
        this.onAbbreviationListener = onAbbreviationListener;
    }

    interface OnAbbreviationListener{

        fun onAbbreviation(licensePlate: LicensePlate?);
    }
}