package com.god.uikit.adapter

import android.content.Context
import com.god.uikit.R
import com.god.uikit.BR
import com.god.uikit.commons.BaseListAdapter
import com.god.uikit.databinding.ItemItemListBinding
import com.god.uikit.entity.Item

class ItemAdapter(list: MutableList<Item>?, context: Context?) :
    BaseListAdapter<ItemItemListBinding, Item>(list, context, BR.item) {

    override fun getLayoutRes(): Int = R.layout.item_item_list;

    override fun setBean(t: Item?) {
        dataBinding.item = t;
    }
}