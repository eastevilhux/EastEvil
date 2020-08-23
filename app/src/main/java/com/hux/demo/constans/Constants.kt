package com.hux.demo.constans

import java.nio.charset.Charset

class Constants private constructor(){

    companion object{
        //请求服务器地址URL
        const val SERVICE_URL = "http://192.168.0.101:8080/lifehouse/"

        //请求Token地址URL
        const val TOKEN_URL = "http://192.168.0.101:8080/token"

        const val HTTP_CHARSET = "UTF-8";
    }
}