package com.hux.demo.model.main

import android.app.Application
import android.util.Log
import com.hux.demo.commons.AppViewModel
import com.hux.demo.constans.AppModel

class MainViewModel(application: Application) : AppViewModel<MainData>(application) {
    private val appModel =  AppModel.instance;

    override fun initData(): MainData = MainData();


    override fun initModel() {
        super.initModel()
    }
}