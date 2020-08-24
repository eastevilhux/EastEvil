package com.good.framework.adapter

import android.content.Context
import com.god.uikit.commons.BaseListAdapter
import com.good.framework.BR
import com.good.framework.R
import com.good.framework.databinding.ItemListCityBinding
import com.good.framework.http.entity.City

class CityAdapter(list: MutableList<City>?, context: Context) :
    BaseListAdapter<ItemListCityBinding, City>(list, context, BR.city) {

    override fun getLayoutRes(): Int = R.layout.item_list_city;

    override fun setBean(t: City?) {
        dataBinding.city = t;
    }
}