package com.good.framework.model.city

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.good.framework.commons.BaseViewModel
import com.good.framework.commons.EastViewModel
import com.good.framework.commons.mainThread
import com.good.framework.http.RetrofitFactory
import com.good.framework.http.entity.City
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CitylistViewModel(application: Application) : EastViewModel<CityData>(application) {

    val provinceList = MutableLiveData<MutableList<City>>();
    var cityList = MutableLiveData<MutableList<City>>();
    var areaList = MutableLiveData<MutableList<City>>();

    override fun initData(): CityData = CityData();

    override fun initModel() {
        super.initModel()
        queryProvince();
    }

    private fun queryProvince() = GlobalScope.launch{
        showLoading();
        var result = RetrofitFactory.instance.baseService.provinceList();
        dismissLoading()
        if(result.isSuccess){
            mainThread {
                provinceList.value = result.data;
            }
        }else{
            error(result.code,result.msg);
        }
    }
}