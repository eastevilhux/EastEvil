package com.hux.demo.services

import com.good.framework.http.entity.Result
import com.good.framework.http.service.BaseService
import retrofit2.http.POST

interface TestService{

    @POST("test/fortest")
    fun forTest(): Result<Int>;
}