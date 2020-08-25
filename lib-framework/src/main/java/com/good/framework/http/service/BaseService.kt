package com.good.framework.http.service

import com.good.framework.entity.KeySet
import com.good.framework.entity.Staff
import com.good.framework.http.entity.City
import com.good.framework.http.entity.Event
import com.good.framework.http.entity.LicensePlate
import com.good.framework.http.entity.Result
import retrofit2.http.*


interface BaseService {
    companion object{
        val BASE_URL : String
            get() = "http://192.168.0.100:8080/lifehouse/";
    }

    @POST("life/appbeforehand")
    fun appBeforehand(): Result<KeySet>;

    /**
     * 查询App欢迎页正在进行中的活动信息
     *
     * create by hux at 2020/4/2 1:34
     * @author hux
     * @since 1.0.0
     * @param apptype
     * app类型，默认为2
     * @return
     * [<]
     */
    @POST("event/appsplash")
    fun appSplashEvent(@Query("apptype") apptype: Int): Result<Event>;


    /**
     * 查询首页banner列表
     */
    @POST("event/homebanner")
    fun appBannerList(@Query("apptype") apptype : Int) : Result<List<Event>>;

    /**
     * 员工登录接口
     */
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("staff/appstafflogin")
    fun staffLogin(@Field("data") data : String) : Result<Staff>;

    @POST("staff/staffinfo")
    fun staffInfo() : Result<Staff>;

    @GET("event/appsplashevents")
    fun splashEventList(@Field("status") status : Int = 0) : Result<Event>;

    @POST("life/licenseplate")
    fun licensePlate() : Result<MutableList<LicensePlate>>;

    @POST("life/provinces")
    fun provinceList() : Result<MutableList<City>>;

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("life/childcitylist")
    fun childCityList(@Field("pcode") pcode:Int,@Field("type") type:Int) : Result<MutableList<City>>;
}