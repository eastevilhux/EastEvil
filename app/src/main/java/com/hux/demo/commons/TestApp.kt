package com.hux.demo.commons

import android.app.Application
import com.hux.demo.constans.AppModel

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppModel.initModel();
    }
}