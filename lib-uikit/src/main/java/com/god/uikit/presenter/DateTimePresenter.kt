package com.god.uikit.presenter

import com.god.uikit.entity.DateTime

interface DateTimePresenter : BasePresenter{

    fun onDaySelect(pos:Int,day:DateTime);

}