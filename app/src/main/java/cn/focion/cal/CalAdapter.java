package cn.focion.cal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

/**
 * 列表适配器
 * 
 * @author Pirate
 * @since 2015.03.28.
 * @version 1.0.1
 */
public class CalAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    
    /**
     * 上下文
     */
    protected Context mContext;
    
    /**
     * 是否加载更多
     */
    protected boolean isLoad = false;
    
    private final int TYPE_HEADER = 1;
    
    private final int TYPE_ITEM = 0;
    
    private final int TYPE_FOOTER = -1;
    
    private CalUtil calUtil;
    
    /**
     * 父类构造
     *
     * @param context
     *            上下文
     */
    public CalAdapter(Context context, CalUtil calUtil) {
        mContext = context;
        this.calUtil = calUtil;
    }
    
    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return newHolder(View.inflate(parent.getContext(),
                                              R.layout.layout_year,
                                              null),
                                 TYPE_HEADER);
            case TYPE_ITEM:
                return newHolder(View.inflate(parent.getContext(),
                                              R.layout.layout_day,
                                              null),
                                 TYPE_ITEM);
            
            case TYPE_FOOTER:
                // 设置颜色
                return newHolder(View.inflate(parent.getContext(),
                                              R.layout.layout_day,
                                              null),
                                 TYPE_FOOTER);
        }
        return null;
    }
    
    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        // 判断Item
        if (position < getItemCount() - 1
            || (!isLoad && position == getItemCount() - 1))
            onBindHolder(holder, position);
    }
    
    /**
     * 获取Item数量
     *
     * @return 返回数量包括底部加载
     */
    @Override
    public int getItemCount() {
        if (isLoad)
            return getCount() + 1;
        else
            return getCount();
    }
    
    /**
     * 真实的数据数量
     *
     * @return 数量个数
     */
    protected int getCount() {
        return calUtil.getCount();
    }
    
    /**
     * 绑定Holder数据
     *
     * @param holder
     *            容器
     * @param position
     *            角标
     */
    protected void onBindHolder(RecyclerHolder holder, int position) {
        switch (calUtil.getType(position)) {
            case TYPE_HEADER:
                holder.holder(R.id.cal_year, TextView.class)
                      .setText(String.format(Locale.CHINA,
                                             "%s月",
                                             calUtil.getValue(position)));
                break;
            case TYPE_FOOTER:
                break;
            default:
                holder.holder(R.id.cal_day, TextView.class)
                      .setText(calUtil.getValue(position));
                break;
        }
    }
    
    /**
     * 返回Item属性
     *
     * @param position
     *            角标
     * @return 属性
     */
    @Override
    public int getItemViewType(int position) {
        if (isLoad && position + 1 == getItemCount())
            return TYPE_FOOTER;
        else
            return calUtil.getType(position);
    }
    
    /**
     * 返回当前加载状态
     *
     * @return true 加载 false 不加载
     */
    public boolean isLoad() {
        return isLoad;
    }
    
    /**
     * 停止加载更多
     */
    public void loadFinish() {
        this.isLoad = false;
        notifyItemRemoved(getItemCount() + 1);
    }
    
    /**
     * 设置是否加载更多
     *
     * @param isLoad
     *            是否加载更多
     */
    public void setLoad(boolean isLoad) {
        this.isLoad = isLoad;
    }
    
    /**
     * 获取ViewHolder
     *
     * @param itemView
     *            布局View
     * @param itemViewType
     *            类型
     * @return Holder
     */
    public RecyclerHolder newHolder(View itemView, int itemViewType) {
        RecyclerHolder holder = new RecyclerHolder(itemView);
        switch (itemViewType) {
            case TYPE_HEADER:
                holder.holder(R.id.cal_year);
                break;
            case TYPE_ITEM:
                holder.holder(R.id.cal_day);
                break;
            case TYPE_FOOTER:
                break;
        }
        return holder;
    }
}
