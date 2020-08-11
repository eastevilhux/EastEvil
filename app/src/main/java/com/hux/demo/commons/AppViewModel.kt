package com.hux.demo.commons

import android.app.Application
import com.good.framework.commons.EastViewModel
import com.good.framework.entity.VMData

abstract class AppViewModel<T: VMData>(application: Application) : EastViewModel<T?>(application){

}