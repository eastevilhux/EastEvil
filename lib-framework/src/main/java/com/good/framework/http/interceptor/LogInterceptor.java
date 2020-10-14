package com.good.framework.http.interceptor;

import com.good.framework.commons.EastExtKt;
import com.good.framework.utils.JsonUtil;
import com.good.framework.utils.LogUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;

public class LogInterceptor implements Interceptor {

    public static String TAG = "LogInterceptor";

    public static int httpId = 0;

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        httpId = EastExtKt.createRandomNumber(1000,9999);

        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtil.d(TAG,httpId+"----------Start----------------");
        LogUtil.d(TAG,httpId+"==URL=>"+request.url());
        String method=request.method();
        LogUtil.d(TAG,httpId+"==METHOD=>"+method);
        LogUtil.d(TAG,httpId+"==REQUEST=>"+request.body().toString());

        Set<String> headers = request.headers().names();
        if(headers != null && !headers.isEmpty()){
            Iterator<String> iter = headers.iterator();
            Map<String,String> map = new HashMap<>();
            String key = null;
            while(iter.hasNext()){
                key = iter.next();
                map.put(key,request.header(key));
            }
            LogUtil.e(TAG,httpId+"==HEADERS=>"+ JsonUtil.Companion.getInstance().getGson().toJson(map));
        }
        if(request.method().equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                LogUtil.e(TAG, httpId+"==PARAMS=>"+sb.toString());
            }
        }
        LogUtil.e(TAG,httpId+"==REPONSE=>" + content);
        LogUtil.e(TAG,httpId+"----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}