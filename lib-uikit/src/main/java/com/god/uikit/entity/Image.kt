package com.god.uikit.entity

import android.util.Log
import com.god.uikit.R
import java.io.File

class Image {
    var type:Type = Type.TYPE_RESOURCE;
    var file:File? = null;
    var resource:Int? = R.drawable.icon_no_data_default;
    var url:String? = null;

    constructor(){
        type = Type.TYPE_DEFAULT;
    }

    constructor(file:File) : this() {
        this.file = file;
        this.type = Type.TYPE_FILE;
    }

    constructor(resource:Int) : this() {
        this.resource = resource;
        this.type = Type.TYPE_RESOURCE;
    }

    constructor(url:String) : this() {
        this.url = url;
        this.type = Type.TYPE_URL;
    }

    enum class Type{
        TYPE_FILE,
        TYPE_RESOURCE,
        TYPE_URL,
        TYPE_DEFAULT;
    }
}