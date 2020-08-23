package com.good.framework.adapter

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.god.uikit.commons.BaseListAdapter
import com.god.uikit.utils.dip2Px
import com.god.uikit.utils.screenSize
import com.good.framework.R
import com.good.framework.BR
import com.good.framework.commons.BaseActivity
import com.good.framework.databinding.ItemListAlphabeticBinding
import com.good.framework.presenter.AlphabeticListener

class AlphabeticAdapter(list: MutableList<String>?, context: Context?) :
    BaseListAdapter<ItemListAlphabeticBinding, String>(list, context, BR.alphabetic) {

    private var lp : LinearLayout.LayoutParams? = null;
    private var presenter: AlphabeticListener? = null;

    init {
        var width : Int = screenSize(context as BaseActivity<*,*>)[0];
        width = (width - 5.dip2Px())/6;
        lp = LinearLayout.LayoutParams(width,width);
    }

    override fun getLayoutRes(): Int = R.layout.item_list_alphabetic;

    override fun setBean(t: String?) {
        dataBinding?.alphabetic = t;
    }

    override fun setViewParams(view: View?) {
        super.setViewParams(view)
        view?.layoutParams = lp;
    }

    override fun setPresenter(dataBinding: ItemListAlphabeticBinding?) {
        super.setPresenter(dataBinding)
        dataBinding?.presenter = presenter;
    }

    fun setPresenter(presenter: AlphabeticListener?){
        this.presenter = presenter;
    }

}