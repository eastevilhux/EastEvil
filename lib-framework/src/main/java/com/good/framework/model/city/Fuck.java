package com.good.framework.model.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.good.framework.commons.EastConstants;
import com.good.framework.http.entity.City;

class Fuck {

    public static void fuck(Activity activity, City p,City c,City a){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EastConstants.KEY_PROVINCE,p);
        bundle.putSerializable(EastConstants.KEY_PROVINCE,c);
        bundle.putSerializable(EastConstants.KEY_PROVINCE,a);
        intent.putExtra("fuck",bundle);
        activity.setResult(EastConstants.RESULT_CODE_CITY,intent);
        activity.finish();
    }
}
