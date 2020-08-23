package com.good.framework.adapter

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.god.uikit.commons.BaseListAdapter
import com.god.uikit.utils.dip2Px
import com.god.uikit.utils.screenSize
import com.good.framework.R
import com.good.framework.BR
import com.good.framework.commons.BaseActivity
import com.good.framework.databinding.ItemLicenseplateBinding
import com.good.framework.http.entity.LicensePlate
import com.good.framework.presenter.LicensePresenter

class LicensePlateAdapter(list: MutableList<LicensePlate>?, context: Context?) :
    BaseListAdapter<ItemLicenseplateBinding, LicensePlate>(list, context, BR.licensePlate) {

    private var lp : LinearLayout.LayoutParams? = null;
    private var presenter:LicensePresenter? = null;

    init {
        var width : Int = screenSize(context as BaseActivity<*,*>)[0];
        width = (width - 7.dip2Px())/8;
        lp = LinearLayout.LayoutParams(width,width);
    }

    override fun getLayoutRes(): Int = R.layout.item_licenseplate;

    override fun setViewParams(view: View?) {
        super.setViewParams(view)
        view?.layoutParams = lp;
    }

    override fun setPresenter(dataBinding: ItemLicenseplateBinding?) {
        super.setPresenter(dataBinding)
        dataBinding?.presenter = presenter;
    }

    override fun setBean(t: LicensePlate?) {
        dataBinding?.licensePlate = t;
    }

    fun setPresenter(presenter: LicensePresenter){
        this.presenter = presenter;
    }
}