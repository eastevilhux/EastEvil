package com.god.uikit.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.god.uikit.R
import com.god.uikit.databinding.LayoutSelectBinding
import com.god.uikit.utils.dip2Px

class SelectLayout : FrameLayout {

    private var dataBinding : LayoutSelectBinding? = null;

    private val haveIcon = ObservableField<Boolean>();
    private val haveItem = ObservableField<Boolean>();
    private val haveMenu = ObservableField<Boolean>();
    private val haveContent = ObservableField<Boolean>();
    private val haveArrow = ObservableField<Boolean>();

    private var itemText = ObservableField<String>();
    private var menuText = ObservableField<String>();
    private var contentText = ObservableField<String>();

    private var itemTextColor : Int = Color.rgb(55,55,55);
    private var contentTextColor : Int = Color.rgb(55,55,55);
    private var menuTextColor : Int = Color.rgb(55,55,55);
    private var arrowImgSize : Int = 35.dip2Px();

    lateinit var tempBd : Drawable;
    var arrowBd : Drawable? = null;
    var iconBd : Drawable? = null;

    private var contentGravity = ObservableField<Int>();

    constructor(context: Context) : super(context) {}
    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs)
    }

    @SuppressLint("Recycle")
    private fun initAttrs(attrs: AttributeSet) {
        var ta = context!!.obtainStyledAttributes(attrs,R.styleable.SelectLayout);

        haveIcon.set(ta.getBoolean(R.styleable.SelectLayout_isHaveIcon,false));
        haveItem.set(ta.getBoolean(R.styleable.SelectLayout_haveItem,true));
        haveContent.set(ta.getBoolean(R.styleable.SelectLayout_haveContent,false));
        haveArrow.set(ta.getBoolean(R.styleable.SelectLayout_have_img_arrow,true));
        haveMenu.set(ta.getBoolean(R.styleable.SelectLayout_haveMenu,false));

        itemText.set(ta.getString(R.styleable.SelectLayout_itemText));
        contentText.set(ta.getString(R.styleable.SelectLayout_contentText));
        menuText.set(ta.getString(R.styleable.SelectLayout_menuText));

        itemTextColor = ta.getColor(R.styleable.SelectLayout_itemTextColor, Color.rgb(150,150,150));
        contentTextColor = ta.getColor(R.styleable.SelectLayout_contentTextColor, Color.rgb(55,55,55));
        menuTextColor = ta.getColor(R.styleable.SelectLayout_menuTextColor, Color.rgb(55,55,55));
        arrowImgSize = ta.getDimensionPixelSize(R.styleable.SelectLayout_arrowImgSize,0);
        if(arrowImgSize == 0){
            arrowImgSize = 35.dip2Px();
        }

        if(haveArrow.get() == true){
            var defArrow = BitmapDrawable(
                context.resources,
                BitmapFactory.decodeResource(resources, R.drawable.icon_arrow_def)
            )
            arrowBd = ta.getDrawable(R.styleable.SelectLayout_arrow_image);
            arrowBd = arrowBd?.let { arrowBd } ?: defArrow;
        }

        if(haveIcon.get() == true){
            var defIcon = BitmapDrawable(
                context.resources,
                BitmapFactory.decodeResource(resources,R.drawable.icon_no_data_default)
            );
            iconBd = ta.getDrawable(R.styleable.SelectLayout_iconSrc);
            iconBd = iconBd?.let { iconBd } ?: defIcon;
        }

        val gravity = ta.getInteger(R.styleable.SelectLayout_content_gravity, 1)
        when (gravity) {
            1 -> contentGravity.set(Gravity.LEFT or Gravity.CENTER_VERTICAL);
            2 -> contentGravity.set(Gravity.CENTER);
            3 -> contentGravity.set(Gravity.RIGHT or Gravity.CENTER_VERTICAL);
            else -> contentGravity.set(Gravity.CENTER);
        }

        ta.recycle();
        init()
    }

    private fun init() {
        dataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_select,this,true);

        dataBinding?.haveArrow = haveArrow;
        dataBinding?.haveIcon = haveIcon;
        dataBinding?.haveItem = haveItem;
        dataBinding?.haveContent = haveContent;
        dataBinding?.haveMenu = haveMenu;
        dataBinding?.itemText = itemText;
        dataBinding?.contentText = contentText;
        dataBinding?.menuText = menuText;

        dataBinding?.tvItem!!.setTextColor(itemTextColor);
        dataBinding?.tvContent!!.setTextColor(contentTextColor);
        dataBinding?.tvMenutext!!.setTextColor(menuTextColor);

        if(haveArrow.get() == true){
            dataBinding?.ivArrow!!.setImageDrawable(arrowBd);
            var lp = dataBinding?.ivArrow!!.layoutParams;
            lp.width = arrowImgSize;
            lp.height = arrowImgSize;
            dataBinding?.ivArrow!!.layoutParams = lp;
        }
        if(haveIcon.get() == true){
            dataBinding?.ivIconimg!!.setImageDrawable(iconBd);
        }
        if(haveContent.get() == true){
            dataBinding?.tvContent!!.gravity = contentGravity.get()?:Gravity.CENTER;
        }
    }

    fun setItemText(itemText:String){
        this.itemText.set(itemText);
    }

    fun setContentText(contentText:String){
        this.contentText.set(contentText);
    }

    fun setMenuText(menuText:String){
        this.menuText.set(menuText);
    }
}