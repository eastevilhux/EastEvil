package com.good.framework.http

import java.nio.charset.Charset

class HttpConfig private constructor(){
    private var TIME_OUT = 20L;

    companion object{
        val instance : HttpConfig by lazy(mode=  LazyThreadSafetyMode.SYNCHRONIZED){
            HttpConfig();
        }

        const val CODE_SUCCESS = 66;
        const val CODE_ERROR = -1;
        const val CODE_LOGIN = -2;
        const val CODE_NETWORK = 404;
        const val CODE_EMPTY = 44;
        const val CODE_SERVICE_ERROR = -4;
        var HTTP_CHARSET = Charset.forName("UTF-8");
        const val UTF8_CHARSET = "UTF-8";


        //Retrofit缓存时间为1小时
        const val MAX_AGE = 60

        //请求服务器地址URL
        var SERVICE_URL = "http://192.168.0.101:8080/lifehousems/"

        //请求Token地址URL
        var TOKEN_URL = "http://192.168.0.101:8080/token"

        fun init(url:String,tokenUrl:String,charset:String = "UTF-8"){
            SERVICE_URL = url;
            TOKEN_URL = tokenUrl;
            HTTP_CHARSET = Charset.forName(charset);
        }
    }

    fun timeOut() : Long {
        return TIME_OUT;
    }

    fun timeOut(timeOut:Long = 20L){
        this.TIME_OUT = timeOut;
    }

}