package com.hux.demo.constans

import com.good.framework.entity.KeySet
import com.good.framework.http.RetrofitConfigure
import com.good.framework.http.RetrofitFactory
import com.good.framework.http.entity.Result
import com.good.framework.http.service.BaseService
import com.hux.demo.services.AccountService
import com.hux.demo.services.TestService

class AppModel private constructor(){

    companion object{
        val instance : AppModel by lazy(mode=  LazyThreadSafetyMode.SYNCHRONIZED){
            AppModel();
        }

        fun initModel(){
            instance.map;
        }

        private const val SERVICE_KEY_ACCOUNT = "accountService";
        private const val SERVICE_KEY_TEST = "testService";
    }

    private val map : HashMap<String, Any> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        initRetrofit();
    };

    private fun initRetrofit():HashMap<String,Any>{
        var m = HashMap<String, Class<*>>();
        m.put(SERVICE_KEY_ACCOUNT,AccountService::class.java)
        m.put(SERVICE_KEY_TEST,TestService::class.java);
        RetrofitConfigure.initHttp(Constants.SERVICE_URL,Constants.TOKEN_URL,Constants.HTTP_CHARSET);
        var map =  RetrofitConfigure.registerService(m);
        return map;
    }

    private val baseService : BaseService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        RetrofitFactory.instance.baseService;
    }

    private val accountService : AccountService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        map[SERVICE_KEY_ACCOUNT] as AccountService;
    }

    fun before(): Result<KeySet> {
        return baseService.appBeforehand();
    }

    fun test(): Result<String> {
        return accountService.test();
    }
}