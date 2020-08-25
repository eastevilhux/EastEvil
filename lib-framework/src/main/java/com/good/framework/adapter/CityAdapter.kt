package com.good.framework.adapter

import android.content.Context
import com.god.uikit.commons.BaseListAdapter
import com.good.framework.BR
import com.good.framework.R
import com.good.framework.databinding.ItemListCityBinding
import com.good.framework.http.entity.City
import com.good.framework.presenter.CityPresenter

class CityAdapter(list: MutableList<City>?, context: Context) :
    BaseListAdapter<ItemListCityBinding, City>(list, context, BR.city) {

    private var presenter: CityPresenter? = null;

    override fun getLayoutRes(): Int = R.layout.item_list_city;

    override fun setBean(t: City?) {
        dataBinding.city = t;
    }

    override fun setPresenter(dataBinding: ItemListCityBinding?) {
        super.setPresenter(dataBinding)
        dataBinding?.setVariable(BR.presenter,presenter);
    }

    fun setPresenter(presenter: CityPresenter){
        this.presenter = presenter;
    }
}