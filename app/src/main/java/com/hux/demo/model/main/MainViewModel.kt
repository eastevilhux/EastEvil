package com.hux.demo.model.main

import android.app.Application
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.good.framework.commons.mainThread
import com.good.framework.http.HttpConfig
import com.good.framework.http.RetrofitFactory
import com.good.framework.http.commons.Constants
import com.hux.demo.commons.AppViewModel
import com.hux.demo.commons.TestApp
import com.hux.demo.constans.AppModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(application: Application) : AppViewModel<MainData>(application) {
    private val appModel =  AppModel.instance;

    val beforDate = MutableLiveData<Int>();

    override fun initData(): MainData = MainData();


    override fun initModel() {
        super.initModel()
        before();
    }

    private fun before() = GlobalScope.launch{
        var result = RetrofitFactory.instance.baseService.appBeforehand();
        if(result.isSuccess){
            Constants.serviceKey = result.data?.serviceKey;
            Constants.appKey = result.data?.appkey;
            Constants.encrypKey = result.extended;
            Constants.setRSAType();
            mainThread {
                beforDate.value = HttpConfig.CODE_SUCCESS;
            }
            test();
        }
    }

    private fun test() = GlobalScope.launch{
        var result = appModel.test();
        Log.d("fuck===>","${result.data?.test1},${result.data?.test2},${result.data?.test3}");
    }


    fun uploadeImage(iconFile : File?, imageList : MutableList<File>?){
        Log.d("WHAT?","ASDFASDF");
    }
}