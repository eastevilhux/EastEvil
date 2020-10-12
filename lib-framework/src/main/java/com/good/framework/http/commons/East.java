package com.good.framework.http.commons;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class East {

    public static ParameterizedType asParameterizedType(Type type){
        Type t = type;
        ParameterizedType pt = (ParameterizedType) t.getClass().getGenericSuperclass();
        Log.d("hux==E>PT",pt.toString());
        return pt;
    }
}
