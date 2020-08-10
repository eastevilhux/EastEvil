package com.god.uikit.presenter

import com.god.uikit.entity.Item

interface MenuPresenter : BasePresenter{

    fun onMenuItemClick(item: Item,position:Int);
}