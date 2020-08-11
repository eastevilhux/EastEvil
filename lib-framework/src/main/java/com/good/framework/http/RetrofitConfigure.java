package com.good.framework.http;

import com.good.framework.http.interceptor.HttpInterceptor;
import com.good.framework.http.interceptor.LogInterceptor;
import com.good.framework.http.retrofit.adapter.EastCallAdapterFactory;
import com.good.framework.http.retrofit.convert.EastConverterFactory;
import com.good.framework.http.service.BaseService;
import com.good.framework.utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitConfigure {
    public static HashMap<String,Class<?>> map;

    public static void initHttp(String url,String tokenUrl,String charset){
        HttpConfig.Companion.init(url,tokenUrl,charset);
    }

    public static void registerService(HashMap<String,Class<?>> map){
        RetrofitConfigure.map = map;
        if(RetrofitConfigure.map == null || RetrofitConfigure.map.isEmpty()){
            throw new NullPointerException("the service is null");
        }

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(HttpConfig.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new LogInterceptor())
                .addNetworkInterceptor(new HttpInterceptor())
                .build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.Companion.getSERVICE_URL())
                .addConverterFactory(EastConverterFactory.Companion.create(JsonUtil.Companion.getInstance().getGson())) //添加gson转换器
                .addCallAdapterFactory(new EastCallAdapterFactory()) //添加rxjava转换器
                .client(okHttpClient)
                .build();

        for(Map.Entry<String, Class<?>> entry : RetrofitConfigure.map.entrySet()){
            mRetrofit.create(entry.getValue());
        }
    }

}
