package com.god.uikit.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.god.uikit.R
import com.god.uikit.BR
import com.god.uikit.commons.BaseListAdapter
import com.god.uikit.databinding.ListItemImageBinding
import com.god.uikit.entity.Image
import com.god.uikit.presenter.ImagePresenter
import com.god.uikit.utils.ViewUtil
import com.god.uikit.utils.dip2Px
import com.god.uikit.utils.screenSize

class ImageAdapter(list: MutableList<Image>?, context: Context?,presenter:ImagePresenter) :
    BaseListAdapter<ListItemImageBinding, Image>(list, context, BR.item) {
    var params :  ViewGroup.LayoutParams? = null;
    private var presenter:ImagePresenter? = null;

    init {
        var size = ViewUtil.getScreenSize(context!!)[0];
        size = (size - 3.dip2Px())/4;
        params = ViewGroup.LayoutParams(size,size);
        this.presenter = presenter;
    }


    override fun getLayoutRes(): Int = R.layout.list_item_image;

    override fun setBean(t: Image?){
        dataBinding.item = t;
        dataBinding.presenter = presenter;
    }

    override fun setVariableId(dataBinding: ListItemImageBinding?, postion: Int) {
        super.setVariableId(dataBinding, postion)
        dataBinding?.setVariable(BR.pos,postion);
    }

    override fun setViewParams(view: View?, position: Int) {
        super.setViewParams(view, position)
        view?.layoutParams = params;
    }
}