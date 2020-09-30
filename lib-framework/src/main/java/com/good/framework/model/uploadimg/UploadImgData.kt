package com.good.framework.model.uploadimg

import com.good.framework.entity.VMData
import java.io.Serializable

class UploadImgData :VMData(){

    var cutWidth : Float = 0f;
    var cutHeight : Float = 0f;
    //需要裁剪的源数据
    var sourceFilePath : String? = null;
    //裁剪后的图片路径
    var cutFilePath : String? = null;
    var provider : String? = null;

    var iconFilePath : String? = null;
    var imageListPath : String? = null;

    companion object{
        const val CODE_TO_CUTTING = 0x01;
        const val CODE_TO_UPLOAD = 0x02;
    }

    enum class ImageType(private val type: Int){
        TYPE_ERROR(0),
        TYPE_SPLASH(0x01),
        TYPE_HOME_BANNER(0x02);

        fun getType(): Int {
            return type;
        }

        companion object{
            fun getType(type:Int) : ImageType{
                return when(type){
                    0->TYPE_ERROR
                    0x01->TYPE_SPLASH
                    0x02->TYPE_HOME_BANNER
                    else->TYPE_ERROR;
                }
            }
        }
    }

    enum class AppType(private val type:Int){
        APP_ERROR(0),
        APP_LIFEHOUSE(0x01),
        APP_LIFEMANAGER(0x02);

        fun getType(): Int {
            return type;
        }

        companion object{
            fun getType(type : Int): AppType {
                return when(type){
                    0->APP_ERROR
                    0x01->APP_LIFEHOUSE
                    0x02->APP_LIFEMANAGER
                    else->APP_ERROR
                }
            }
        }
    }
}