package com.good.framework.commons

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.god.uikit.commons.Constants.Companion.REQEUST_CODE_ALBUM
import com.god.uikit.commons.Constants.Companion.REQUEST_CODE_CUTTING
import com.good.framework.R
import com.good.framework.http.commons.Constants
import com.good.framework.model.camera.CameraActivity
import com.good.framework.model.uploadimg.UploadImgData.Companion.CHILD_PATH_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.IMAGE_CUTHEIGHT_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.IMAGE_CUTWIDTH_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.IMAGE_HEIGHT_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.IMAGE_WIDTH_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.PROVINDER_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.ROOT_PAHT_KEY
import com.good.framework.utils.AESUtil
import com.good.framework.utils.JsonUtil
import com.good.framework.utils.RSAUtil
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


/**
 * 转换成json格式
 */
fun Any.toJSON(): String? = JsonUtil.instance.getGson().toJson(this);

/**
 * 当前是否在主线程
 */
fun isMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper();

/**
 * 通过协程  在主线程上运行
 */
fun mainThread(block: () -> Unit) = GlobalScope.launch(Dispatchers.Main) {
    block()
}

fun createRandomNumber(min: Int = 0, max: Int = 10): Int {
    return ((min + Math.random() * (max)).toInt())
}

fun String.encryptData(): String? {
    Constants.serviceKey?.let {
        var data: String? = null;
        when (Constants.encrypType) {
            Constants.EncrypType.ENCRYPE_AES -> {
                //使用AES加密数据
                data = AESUtil.aesEncrypt(this, it);
            }
            Constants.EncrypType.ENCRYPE_RSA -> {
                //使用RSA加密数据
                data = RSAUtil.encryptByPublicKey(this, it);
            }
        }
        return data;
    } ?: return this;
}

fun takePhoto(
    activity: Activity? = null,
    fragment: Fragment? = null,
    width: Int = 0,
    height: Int = 0,
    cutWidth: Int = 0,
    cutHeight: Int = 0,
    provinder:String,
    rootPaht : String,
    childPath : String
) {
    if (activity == null && fragment == null) {
        throw IllegalAccessException("no content fond");
    }
    if (activity != null && fragment != null) {
        throw IllegalAccessException("unknow witch onw to choose content");
    }
    var intent = Intent(activity, CameraActivity::class.java);
    intent.putExtra(IMAGE_WIDTH_KEY, width);
    intent.putExtra(IMAGE_HEIGHT_KEY, height);
    intent.putExtra(IMAGE_CUTWIDTH_KEY, cutWidth);
    intent.putExtra(IMAGE_CUTHEIGHT_KEY, cutHeight);
    intent.putExtra(PROVINDER_KEY,provinder);
    intent.putExtra(ROOT_PAHT_KEY,rootPaht);
    intent.putExtra(CHILD_PATH_KEY,childPath);
    activity?.let {
        it.startActivityForResult(intent, com.god.uikit.commons.Constants.REQEUST_CODE_CAMERA)
    }
    fragment?.let {
        it.startActivityForResult(intent, com.god.uikit.commons.Constants.REQEUST_CODE_CAMERA)
    }
}

fun cuttingImage(
    activity: Activity? = null,
    fragment: Fragment? = null,
    sourceFilePath : String,
    outFilePaht : String,
    aspectRatioX : Float = 16f,
    aspectRatioY : Float = 9f,
    provider : String
) {
    if (activity == null && fragment == null) {
        throw IllegalAccessException("no content fond");
    }
    if (activity != null && fragment != null) {
        throw IllegalAccessException("unknow witch onw to choose content");
    }
    var context : Context?= null;
    activity?.let {
        context = it;
    }
    fragment?.let {
        context = fragment.requireContext();
    }
    var sourceUri : Uri;
    var outUri : Uri;
    var sourceFile = File(sourceFilePath);
    var outFile = File(outFilePaht);
    if (Build.VERSION.SDK_INT >= 24) {
        sourceUri = FileProvider.getUriForFile(context!!, provider, sourceFile);
        outUri = FileProvider.getUriForFile(context!!, provider, outFile);
    } else {
        outUri = Uri.fromFile(outFile);
        sourceUri = Uri.fromFile(sourceFile);
    }
    //初始化，第一个参数：需要裁剪的图片；第二个参数：裁剪后图片
    val uCrop = UCrop.of(sourceUri, outUri);
    //初始化UCrop配置
    val options = UCrop.Options()
    //设置裁剪图片可操作的手势
    options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
    //是否隐藏底部容器，默认显示
    options.setHideBottomControls(true);
    //设置toolbar颜色
    options.setToolbarColor(ActivityCompat.getColor(context!!, R.color.colorUploadimgTitle));
    //设置状态栏颜色
    options.setStatusBarColor(ActivityCompat.getColor(context!!, R.color.colorUploadimgTitle));
    options.setFreeStyleCropEnabled(true)
    //UCrop配置
    uCrop.withOptions(options);
    //设置裁剪图片的宽高比，比如16：9
    uCrop.withAspectRatio(aspectRatioX, aspectRatioY);
    //跳转裁剪页面
    activity?.let {
        uCrop.start(it, REQUEST_CODE_CUTTING);
    }
    fragment?.let {
        uCrop.start(context!!,it);
    }
}

