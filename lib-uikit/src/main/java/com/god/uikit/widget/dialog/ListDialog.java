package com.god.uikit.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.god.uikit.R;
import com.god.uikit.adapter.ItemAdapter;
import com.god.uikit.databinding.DialogListBinding;
import com.god.uikit.entity.Item;
import com.god.uikit.utils.ViewUtil;

import java.util.List;

public class ListDialog extends Dialog implements AdapterView.OnItemClickListener {

    private DialogListBinding dataBinding;

    private ObservableField<String> title;
    private ObservableField<Boolean> haveTitle;
    private ItemAdapter adapter;

    private List<Item> itemList;


    private ListDialog(@NonNull Context context) {
        super(context);
    }

    private ListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    private ListDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private ListDialog(Builder builder){
        super(builder.context, R.style.DialogStyle);
        this.title = new ObservableField<>(builder.title);
        this.haveTitle = new ObservableField<>(builder.haveTitle);
        this.itemList = builder.itemList;
        init();
    }


    private void init(){
        dataBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.dialog_list,null,false);
        setContentView(dataBinding.getRoot());
        dataBinding.setTitle(title);
        dataBinding.setHaveTitle(haveTitle);

        adapter = new ItemAdapter(itemList,getContext());
        dataBinding.setAdapter(adapter);

        dataBinding.menuListview.setOnItemClickListener(this);
        countSize();
    }

    private void countSize(){
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        lp.width = ViewUtil.Companion.getScreenSize(getContext())[0] -
                ViewUtil.Companion.dip2px(getContext(),50);
        int height = 0;
        if(haveTitle.get()){
            height = ViewUtil.Companion.dip2px(getContext(),50);
        }
        if(itemList != null && itemList.size() > 0){
            height = height + (height * (itemList.size()-1));
            height = height + ViewUtil.Companion.dip2px(getContext(),(itemList.size() - 2));
            height = height + ViewUtil.Companion.dip2px(getContext(),10);
        }
        lp.height = height;
        window.setAttributes(lp);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public static class Builder{
        private String title;
        private boolean haveTitle;
        private Context context;
        private List<Item> itemList;

        public Builder(Context context){
            this.context = context;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder haveTitle(boolean haveTitle){
            this.haveTitle = haveTitle;
            return this;
        }

        public Builder itemList(List<Item> itemList){
            this.itemList = itemList;
            return this;
        }


        public ListDialog builder(){
            return new ListDialog(this);
        }

    }
}
