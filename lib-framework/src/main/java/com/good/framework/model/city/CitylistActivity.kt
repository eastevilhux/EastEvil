package com.good.framework.model.city

import android.view.Gravity
import androidx.lifecycle.Observer
import com.good.framework.R
import com.good.framework.adapter.CityAdapter
import com.good.framework.commons.BaseActivity
import com.good.framework.databinding.ActivityCitylistBinding

class CitylistActivity : BaseActivity<ActivityCitylistBinding, CitylistViewModel>() {
    private var provinceAdapter : CityAdapter? = null;
    private var cityAdapter : CityAdapter? = null;
    private var areaAdapter : CityAdapter? = null;

    override fun getLayoutRes(): Int = R.layout.activity_citylist;

    override fun getVMClass(): Class<CitylistViewModel> = CitylistViewModel::class.java;

    override fun initView() {
        dataBinding!!.titleLayout.onTitleListener = this;

        dataBinding!!.mainLayout.openDrawer(Gravity.LEFT)
        dataBinding!!.mainLayout.closeDrawer(Gravity.RIGHT);

        provinceAdapter = CityAdapter(null,this);
        cityAdapter = CityAdapter(null,this);
        areaAdapter = CityAdapter(null,this);

        dataBinding?.provinceAdapter = provinceAdapter;
        dataBinding?.cityAdapter = cityAdapter;
        dataBinding?.areaAdapter = areaAdapter;


        viewModel?.provinceList!!.observe(this, Observer {
            provinceAdapter?.list = it;
            provinceAdapter?.notifyDataSetChanged();
        })

        viewModel?.cityList!!.observe(this, Observer {
            cityAdapter?.list = it;
            cityAdapter?.notifyDataSetChanged();
        })

        viewModel?.areaList!!.observe(this, Observer {
            areaAdapter?.list = it;
            areaAdapter?.notifyDataSetChanged();
        })
    }
}