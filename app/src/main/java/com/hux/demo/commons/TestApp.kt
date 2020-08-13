package com.hux.demo.commons

import android.app.Application
import com.good.framework.utils.LogUtil
import com.hux.demo.constans.AppModel

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LogUtil.debug = true;
        AppModel.initModel();
    }
}