package com.god.uikit.presenter

import com.god.uikit.entity.Image

interface ImagePresenter : BasePresenter {

    fun onImage(image:Image,pos:Int);
}