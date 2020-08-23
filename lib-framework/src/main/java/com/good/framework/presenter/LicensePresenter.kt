package com.good.framework.presenter

import com.god.uikit.presenter.BasePresenter
import com.good.framework.http.entity.LicensePlate

interface LicensePresenter : BasePresenter{

    fun onItemClick(licensePlate: LicensePlate?);
}