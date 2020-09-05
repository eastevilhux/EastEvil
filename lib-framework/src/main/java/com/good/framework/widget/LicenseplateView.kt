package com.good.framework.widget

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.good.framework.R
import com.good.framework.commons.BaseActivity
import com.good.framework.databinding.LayoutLicenseplateBinding
import com.good.framework.db.CityDao
import com.good.framework.db.LicensePlateDao
import com.good.framework.http.entity.LicensePlate
import com.good.framework.widget.popupwindow.AbbreviationPopupWindow
import com.good.framework.widget.popupwindow.AlphabeticPopupWindow

class LicenseplateView : FrameLayout, AbbreviationPopupWindow.OnAbbreviationListener,AlphabeticPopupWindow.OnAlphabeticListener {
    private var dataBinding : LayoutLicenseplateBinding? = null;

    var abbreviationBD : Int? = null;
    var letterBD : Int? = null;
    private var licensePlateDao : LicensePlateDao? = null;
    private var activity:BaseActivity<*,*>? = null;
    private var view:View? = null;

    private var abbreviationPopupWindow : AbbreviationPopupWindow? = null;
    private var alphabeticPopupWindow : AlphabeticPopupWindow? = null;

    private var abbreviation : LicensePlate? = null;
    private var alphabetic : LicensePlate? = null;
    private var onLicenseplateListener : OnLicenseplateListener? = null;

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        this.activity = activity;
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        this.activity = activity;
        initAttrs(attrs)
    }


    private fun initAttrs(attrs: AttributeSet){
        var ta = context!!.obtainStyledAttributes(attrs,R.styleable.LicenseplateView);
        abbreviationBD = ta.getResourceId(R.styleable.LicenseplateView_abbreviationBackground,R.drawable.shape_license_plate);
        letterBD = ta.getResourceId(R.styleable.LicenseplateView_letterBackground,R.drawable.shape_license_plate);
        init();
    }

    private fun init(){
        dataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_licenseplate,this,true);

        abbreviationBD?.let { dataBinding?.tvAbbreviation!!.setBackgroundResource(it) }
        letterBD?.let { dataBinding?.tvLetter!!.setBackgroundResource(it) };

        dataBinding?.liceseplateView = this;
    }

    fun setActivity(activity: BaseActivity<*,*>){
        this.activity = activity;
    }

    fun setView(view:View){
        this.view = view;
    }

    fun setLicensePlateDao(licensePlateDao : LicensePlateDao?){
        this.licensePlateDao = licensePlateDao;
    }

    fun setOnLicenseplateListener(onLicenseplateListener:OnLicenseplateListener){
        this.onLicenseplateListener = onLicenseplateListener;
    }

    fun onViewClick(view : View){
        when(view.id){
            R.id.tv_abbreviation->{
                //显示所有省份简称
                if(abbreviationPopupWindow == null){
                    abbreviationPopupWindow =
                        activity?.let { licensePlateDao?.let { it1 ->
                                AbbreviationPopupWindow(it,
                                    it1,this.view?:this
                                )
                            }
                        }
                    abbreviationPopupWindow?.setOnAbbreviationListener(this)
                }else{
                    if(this.view != null){
                        abbreviationPopupWindow?.show(this.view!!);
                    }else{
                        abbreviationPopupWindow?.show(this);
                    }
                }
            }
            R.id.tv_letter->{
                //显示所有简称下字母
                if(abbreviation == null){
                    activity?.showToastShort(R.string.please_select_abbreviation)
                }else{
                    if(alphabeticPopupWindow == null){
                        alphabeticPopupWindow =
                            activity?.let { AlphabeticPopupWindow(it, abbreviation!!.alphabeticList) };
                        alphabeticPopupWindow?.setOnAlphabeticListener(this);
                    }else{
                        alphabeticPopupWindow?.notifyData(abbreviation!!.alphabeticList);
                    }
                    if(this.view != null){
                        alphabeticPopupWindow?.show(this.view!!);
                    }else{
                        alphabeticPopupWindow?.show(this);
                    }
                }
            }
        }
    }

    override fun onAbbreviation(licensePlate: LicensePlate?) {
        this.abbreviation = licensePlate;
        licensePlate?.let {
            dataBinding?.tvAbbreviation!!.setText(it.abbreviation);
            if(onLicenseplateListener != null){
                onLicenseplateListener!!.onAbbreviation(it);
            }
        }
    }

    override fun onAlphabetic(licensePlate: String?) {
        licensePlate?.let {
            dataBinding?.tvLetter!!.setText(licensePlate);
            if(onLicenseplateListener != null){
                onLicenseplateListener!!.onAlphabetic(licensePlate);
            }
        }
    }

    interface OnLicenseplateListener{

        fun onAlphabetic(alphabetiic:String?);

        fun onAbbreviation(licensePlate: LicensePlate?);
    }
}