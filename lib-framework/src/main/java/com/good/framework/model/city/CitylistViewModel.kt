package com.good.framework.model.city

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.good.framework.R
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

    val province = MutableLiveData<City>();
    val city = MutableLiveData<City>();
    val area = MutableLiveData<City>();

    val cityName = MutableLiveData<String>();

    override fun initData(): CityData = CityData();

    override fun initModel() {
        super.initModel()
        cityName.value = getString(R.string.please_select_city)
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

    fun childCity(type:Int,city:City){
        queryChildCity(type,city);
    }

    private fun queryChildCity(type:Int,tcity:City) = GlobalScope.launch{
        if(type != 3){
            showLoading();
            var queryType : Int = type + 1;
            var result = RetrofitFactory.instance.baseService.childCityList(tcity.code,queryType);
            dismissLoading();
            if(result.isSuccess){
                mainThread {
                    if(type == 1){
                        cityList.value = result.data;
                        province.value = tcity;
                        city.value = null;
                        area.value = null;
                    }else if(type == 2){
                        areaList.value = result.data;
                        city.value = tcity;
                        area.value = null;
                    }
                }
            }else{
                error(result.code,result.msg);
            }
        }else{
            //选择了区域，选择完成
            mainThread {
                area.value = tcity;
            }

        }
        mainThread {
            cityName.value = "${province.value?.name?:""}${city.value?.name?:""}${area.value?.name?:""}"
        }

    }
}