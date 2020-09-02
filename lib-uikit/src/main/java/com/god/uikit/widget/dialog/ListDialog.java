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

import java.util.ArrayList;
import java.util.List;

public class ListDialog extends Dialog implements AdapterView.OnItemClickListener {
    public static final int SELECT_TYPE_DISMISS = 0x01;
    public static final int SELECT_TYEP_SHOW = 0x02;
    public static final int SELECT_TYPE_MORE = 0x03;

    private DialogListBinding dataBinding;

    private ObservableField<String> title;
    private ObservableField<Boolean> haveTitle;
    private ItemAdapter adapter;

    private List<Item> itemList;

    private int lastSelect = -1;
    private int selectType;

    private List<Item> selectItemList;

    private int tag;

    private OnDialogItemClickListener onDialogItemClickListener;

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
        this.selectType = builder.selectType == 0 ? SELECT_TYPE_DISMISS : builder.selectType;
        this.onDialogItemClickListener = builder.onDialogItemClickListener;
        if(builder.postion == null){
            this.lastSelect = -1;
        }else{
            this.lastSelect = builder.postion;
        }
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
        dataBinding.setDialog(this);
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
        if(selectType == SELECT_TYEP_SHOW){
            itemList.get(i).setSelect(true);
            if(lastSelect != -1){
                itemList.get(lastSelect).setSelect(false);
            }
            lastSelect = i;
            adapter.setList(itemList);
            adapter.notifyDataSetChanged();
            if(onDialogItemClickListener != null){
                onDialogItemClickListener.onItemClick(i,itemList.get(i));
            }
        }else if(selectType == SELECT_TYPE_DISMISS){
            dismiss();
            if(onDialogItemClickListener != null)
                onDialogItemClickListener.onItemClick(i,itemList.get(i));
        }else{
            //多选
            if(selectItemList == null) {
                selectItemList = new ArrayList<>();
            }
            Item item = itemList.get(i);
            if(item.isSelect()){
                //判断之前是否已经有本次的选中
                if(selectItemList.indexOf(item) != -1){
                    //移除本次的添加
                    selectItemList.remove(item);
                }
                //已经选中,则本次设置为未选中
                item.setSelect(false);
            }else{
                //未选中，则设置为选中
                item.setSelect(true);
                if(selectItemList.indexOf(item) == -1){
                    selectItemList.add(item);
                }
            }
            adapter.setList(itemList);
            adapter.notifyDataSetChanged();

        }
    }

    public void onViewClick(View view){
        dismiss();
        if(view.getId() == R.id.tv_enter){
            if(onDialogItemClickListener != null){
                onDialogItemClickListener.onEnter(selectItemList);
            }
        }
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public int getSelectType() {
        return selectType;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

    public void title(String title){
        this.title.set(title);
    }

    public void haveTitle(boolean haveTitle){
        this.haveTitle.set(haveTitle);
    }

    public OnDialogItemClickListener getOnDialogItemClickListener() {
        return onDialogItemClickListener;
    }

    public void setOnDialogItemClickListener(OnDialogItemClickListener onDialogItemClickListener) {
        this.onDialogItemClickListener = onDialogItemClickListener;
    }

    public static class Builder{
        private String title;
        private boolean haveTitle;
        private Context context;
        private List<Item> itemList;
        private Integer postion;
        private int selectType;
        private OnDialogItemClickListener onDialogItemClickListener;

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

        public Builder currentItem(Integer postion){
            this.postion = postion;
            return this;
        }

        public Builder selectType(int selectType){
            this.selectType = selectType;
            return this;
        }

        public Builder onDialogItemClickListener(OnDialogItemClickListener onDialogItemClickListener){
            this.onDialogItemClickListener = onDialogItemClickListener;
            return this;
        }

        public ListDialog builder(){
            return new ListDialog(this);
        }

    }

    public interface OnDialogItemClickListener{

        void onItemClick(int position,Item item);

        void onEnter(List<Item> selectItemList);

    }

}
