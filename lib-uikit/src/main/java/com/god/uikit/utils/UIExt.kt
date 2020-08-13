package com.god.uikit.utils

import android.app.Activity
import android.graphics.Point
import android.util.Log
import android.view.Display
import androidx.fragment.app.Fragment
import com.god.uikit.R
import com.god.uikit.commons.Constants
import com.god.uikit.commons.Glide4Engine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy


fun toAlubm(
    activity:Activity? = null,
    fragment:Fragment? = null,
    mimeType : Int = 0,
    maxSelectable:Int = 1
){
    if(activity ==null && fragment== null)
        throw NullPointerException("the content is null");
    if(activity != null && fragment != null)
        throw IllegalAccessException("unknow witich one to choose for content");
    var matisse : Matisse? = null;
    if(activity != null)
        matisse = Matisse.from(activity);
    if(fragment != null)
        matisse = Matisse.from(fragment);
    matisse?.let {
        var selectionCreator = when(mimeType){
            0->it.choose(MimeType.ofAll())
            1->it.choose(MimeType.ofImage())
            2->it.choose(MimeType.ofVideo())
            else ->it.choose(MimeType.ofAll())
        }
        selectionCreator.showSingleMediaType(true);
        //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
        selectionCreator.capture(true)
        selectionCreator.captureStrategy(CaptureStrategy(true,"PhotoPicker"))
        selectionCreator.countable(true)
        selectionCreator.maxSelectable(maxSelectable)
        selectionCreator.thumbnailScale(0.8f)//界面中缩略图的质量
        selectionCreator.theme(R.style.Matisse_Zhihu)
        selectionCreator.imageEngine(Glide4Engine())
            //请求码
        selectionCreator.forResult(Constants.REQEUST_CODE_ALBUM);
    }
}

fun screenSize(activity: Activity):Array<Int>{
    val display: Display = activity.getWindowManager().getDefaultDisplay()
    val point = Point()
    display.getSize(point)
    Log.d("SCREEN_SIZE=>","${point.x} X ${point.y}")
    return arrayOf(point.x,point.y)
}

