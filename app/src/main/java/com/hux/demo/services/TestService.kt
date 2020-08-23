package com.hux.demo.services

import com.good.framework.http.entity.City
import com.good.framework.http.entity.LicensePlate
import com.good.framework.http.entity.Result
import com.good.framework.http.service.BaseService
import retrofit2.http.POST

interface TestService{

    @POST("test/fortest")
    fun forTest(): Result<Int>;


    @POST("life/licenseplate")
    fun provinceList() : Result<LicensePlate>;
}