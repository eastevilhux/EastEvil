package com.good.framework.model.uploadimg

import com.good.framework.entity.VMData
import java.io.Serializable

class UploadImgData :VMData(){
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