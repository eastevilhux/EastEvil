package com.good.framework.model.city

import android.content.Intent
import android.view.Gravity
import androidx.lifecycle.Observer
import com.good.framework.R
import com.good.framework.adapter.CityAdapter
import com.good.framework.commons.BaseActivity
import com.good.framework.databinding.ActivityCitylistBinding
import com.good.framework.commons.EastConstants
import com.good.framework.entity.Error
import com.good.framework.http.entity.City
import com.good.framework.presenter.CityPresenter

class CitylistActivity : BaseActivity<ActivityCitylistBinding, CitylistViewModel>() {
    private var provinceAdapter : CityAdapter? = null;
    private var cityAdapter : CityAdapter? = null;
    private var areaAdapter : CityAdapter? = null;

    override fun getLayoutRes(): Int = R.layout.activity_citylist;

    override fun getVMClass(): Class<CitylistViewModel> = CitylistViewModel::class.java;

    override fun initView() {
        dataBinding!!.titleLayout.onTitleListener = this;
        dataBinding!!.viewModel = viewModel;

        dataBinding!!.mainLayout.openDrawer(Gravity.LEFT)
        dataBinding!!.mainLayout.closeDrawer(Gravity.RIGHT);

        provinceAdapter = CityAdapter(null,this);
        cityAdapter = CityAdapter(null,this);
        areaAdapter = CityAdapter(null,this);

        dataBinding?.provinceAdapter = provinceAdapter;
        dataBinding?.cityAdapter = cityAdapter;
        dataBinding?.areaAdapter = areaAdapter;

        initCityPresenter();

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


    private fun initCityPresenter(){
        provinceAdapter?.setPresenter(object : CityPresenter {
            override fun onCity(city: City) {
                viewModel?.childCity(1,city);
                dataBinding?.mainLayout!!.closeDrawer(Gravity.LEFT);
                dataBinding?.mainLayout!!.closeDrawer(Gravity.RIGHT);
            }
        })

        cityAdapter?.setPresenter(object : CityPresenter{
            override fun onCity(city: City) {
                viewModel?.childCity(2,city);
                dataBinding?.mainLayout!!.closeDrawer(Gravity.LEFT);
                dataBinding?.mainLayout!!.openDrawer(Gravity.RIGHT);
            }

        })

        areaAdapter?.setPresenter(object : CityPresenter{
            override fun onCity(city: City) {
                viewModel?.childCity(3,city);
                dataBinding?.mainLayout!!.closeDrawer(Gravity.LEFT);
                dataBinding?.mainLayout!!.closeDrawer(Gravity.RIGHT);
            }
        })
    }

    override fun reqeustError(error: Error) {
        super.reqeustError(error)
        showToastShort(error.msg?:getString(R.string.error_network));
    }

    override fun onMenu() {
        super.onMenu()
        var intent = Intent();
        var p = viewModel?.province?.value;
        var c = viewModel?.city?.value;
        var a = viewModel?.area?.value;
        Fuck.fuck(this,p,c,a);
    }
}