package com.hux.demo.services

import com.good.framework.entity.KeySet
import com.good.framework.http.entity.Result
import com.good.framework.http.service.BaseService
import com.hux.demo.entity.Test
import retrofit2.http.POST

interface AccountService{

    @POST("life/test")
    fun test(): Result<Test>;
}