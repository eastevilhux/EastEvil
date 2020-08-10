package com.good.framework.http.commons

import com.good.framework.commons.encryptData
import com.good.framework.commons.toJSON
import com.good.framework.entity.KeySet
import com.good.framework.entity.Staff
import com.good.framework.http.RetrofitFactory
import com.good.framework.http.entity.Event
import com.good.framework.http.entity.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseModel {
    private val baseService = RetrofitFactory.instance.baseService;

    suspend fun appBeforehand() : Result<KeySet> = withContext(Dispatchers.IO) {
        return@withContext baseService.appBeforehand();
    }

    fun appSplashEvent() : Result<Event> {
        return baseService.appSplashEvent(Event.TYPE_APP_SPLASH);
    }

    fun appBannerList() : Result<List<Event>>{
        return baseService.appBannerList(Event.TYPE_HOME_BANNER);
    }

    fun staffLogin(code : String,loginpsd : String): Result<Staff> {
        var map = HashMap<String,String>();
        map["staffcode"] = code;
        map["loginpsd"] = loginpsd;
        var data = map.toJSON();
        data = data?.encryptData() ?: "";
        return baseService.staffLogin(data);
    }


    fun staffInfo(): Result<Staff> {
        return baseService.staffInfo();
    }

    fun appSplashEventList() : Result<Event>{
        return baseService.splashEventList();
    }

    companion object{
        val instance : BaseModel by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            BaseModel();
        }
    }
}