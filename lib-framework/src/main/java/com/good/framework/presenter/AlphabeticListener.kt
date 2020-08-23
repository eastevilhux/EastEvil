package com.good.framework.presenter

import com.good.framework.http.entity.LicensePlate

interface AlphabeticListener {

    fun onAlphabetic(licensePlate: String?);
}