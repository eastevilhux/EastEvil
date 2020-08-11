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

    private val map : HashMap<String, Class<*>> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        initRetrofit();
    };

    private fun initRetrofit():HashMap<String, Class<*>>{
        var m = HashMap<String, Class<*>>();
        m.put(SERVICE_KEY_ACCOUNT,AccountService::class.java)
        m.put(SERVICE_KEY_TEST,TestService::class.java);
        RetrofitConfigure.initHttp(Constants.SERVICE_URL,Constants.TOKEN_URL,Constants.HTTP_CHARSET);
        RetrofitConfigure.registerService(m);
        return m;
    }

    private val baseService : BaseService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        RetrofitFactory.instance.baseService;
    }


    fun before(): Result<KeySet> {
        return baseService.appBeforehand();
    }
}