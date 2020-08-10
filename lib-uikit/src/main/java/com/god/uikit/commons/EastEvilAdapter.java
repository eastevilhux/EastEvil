package com.god.uikit.commons;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import java.util.List;

/**
 * RecyclerView的适配器基类
 * @author hux
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class EastEvilAdapter<T> extends RecyclerAdapter{

    private List<T> list;

    private boolean isMore;

    public EastEvilAdapter(Context context, List<T> list) {
        super(context);
        isMore = false;
        this.list = list;
    }

    public EastEvilAdapter(Context context, List<T> list, boolean isMore) {
        super(context);
        this.isMore = isMore;
        this.list = list;
    }

    @Override
    public void setBean(ViewDataBinding dataBinding, int varid, int position) {
        if(isHaveFoodView()){
            if(position < list.size()){
                dataBinding.setVariable(varid,list.get(position));
                setPresenter(dataBinding);
                setVariable(dataBinding,position);
            } else{
                setOtherView(dataBinding);
            }
        }else{
            dataBinding.setVariable(varid,list.get(position));
            setPresenter(dataBinding);
            setVariable(dataBinding,position);
        }
    }

    public void setPresenter(ViewDataBinding dataBinding){

    }

    public void setVariable(ViewDataBinding dataBinding,int position){

    }

    @Override
    public int getItemViewType(int position) {
        if(isHaveFoodView()){
            //此时，返回的总item条数将加1
            if(position >= list.size()){
                if(isMore){
                    return getLoadingType(position);
                }else{
                    return getNodataType(position);
                }
            }else{
                return getItemType(position);
            }
        }else{
            return getItemType(position);
        }
    }

    public int getItemCount() {
        return list == null ? 0 : isHaveFoodView() ? list.size()+1 : list.size();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


    /**
     * 当下标大于或等于数据集合的长度时，且此时需要加载更多，调用该方法获取加载更多的item类型
     * @author hux
     * @createTime 2019/4/23 16:51
     * @since 1.0.0
     * @see EastEvilAdapter
     * @param position
     *      下标位置
     * @return
     *       加载更多item类型,即加载更多的资源布局id
     */
    public abstract int getLoadingType(int position);

    /**
     * 当下标大于或等于数据集合的长度时，且此时不需要加载更多，调用该方法获取暂无更多数据的item类型
     * @author hux
     * @createTime 2019/4/23 16:53
     * @since EastEvilAdapter
     * @see EastEvilAdapter
     * @param position
     *      下标位置
     * @return
     *      暂无更多数据的item类型，即暂无更多资源id
     */
    public abstract int getNodataType(int position);

    public abstract int getItemType(int position);

    /**
     * 当下标大于或等于数据集合的长度时，调用该方法设置item数据内容
     * @author hux
     * @createTime 2019/4/23 16:47
     * @since 1.0.0
     * @see EastEvilAdapter
     * @param dataBinding
     *      {@link ViewDataBinding}
     * @return
     *       void
     */
    public void setOtherView(ViewDataBinding dataBinding){

    }

    /**
     * 是否需要加载最后一条提示加载的item view
     * @author hux
     * @sine 1.0.0
     * @create by hux at 2019/12/23 23:29
     */
    public boolean isHaveFoodView(){
        return true;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }
}
