package com.good.framework.presenter

import com.god.uikit.presenter.BasePresenter
import com.good.framework.http.entity.City

interface CityPresenter : BasePresenter{

    fun onCity(city : City);
}