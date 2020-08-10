package com.god.uikit.adapter

import android.content.Context
import android.util.Log
import android.view.Menu
import com.god.uikit.BR
import com.god.uikit.R
import com.god.uikit.commons.BaseListAdapter
import com.god.uikit.databinding.ListItemRightmenuBinding
import com.god.uikit.entity.Item
import com.god.uikit.presenter.MenuPresenter

class PopupAdapter(list: MutableList<Item>?, context: Context) :
    BaseListAdapter<ListItemRightmenuBinding, Item>(list,context, BR.popupMenuItem) {

    companion object{
        const val TAG = "PopupAdapter==>";
    }

    var presetern : MenuPresenter? = null;

    override fun getLayoutRes(): Int = R.layout.list_item_rightmenu;

    override fun setBean(t: Item?) {
        dataBinding.popupMenuItem = t;
    }

    override fun setPresenter(dataBinding: ListItemRightmenuBinding?) {
        super.setPresenter(dataBinding)
        dataBinding?.presenter = presetern;
    }

    override fun setVariableId(dataBinding: ListItemRightmenuBinding?, postion: Int) {
        super.setVariableId(dataBinding, postion)
        dataBinding?.postion = postion;
    }

}