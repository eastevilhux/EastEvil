package com.god.uikit.adapter

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.god.uikit.R
import com.god.uikit.BR
import com.god.uikit.commons.BaseListAdapter
import com.god.uikit.databinding.ListItemDayBinding
import com.god.uikit.entity.DateTime
import com.god.uikit.presenter.DateTimePresenter
import com.god.uikit.utils.ViewUtil
import com.god.uikit.utils.dip2Px

class DayAdapter(list: MutableList<DateTime>?, context: Context?) :
    BaseListAdapter<ListItemDayBinding, DateTime>(list, context, BR.day) {

    private var presenter : DateTimePresenter? = null;

    private var parasm : LinearLayout.LayoutParams? = null;

    init {
        var width = ViewUtil.getScreenSize(context!!);
        var size = (width[0] - 6.dip2Px())/7;
        parasm = LinearLayout.LayoutParams(size,size);
    }

    override fun getLayoutRes(): Int = R.layout.list_item_day;

    override fun setBean(t: DateTime?) {
        dataBinding.day = t;
    }

    override fun setViewParams(view: View?) {
        super.setViewParams(view)
        view?.layoutParams = parasm;
    }

    override fun setVariableId(dataBinding: ListItemDayBinding?, postion: Int) {
        super.setVariableId(dataBinding, postion)
        dataBinding?.setVariable(BR.presenter,presenter);
        dataBinding?.setVariable(BR.position,postion);
    }

    fun setPresenter(presenter: DateTimePresenter){
        this.presenter = presenter;
    }
}