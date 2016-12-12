package cn.focion.cal;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 列表适配器
 * 
 * @author Pirate
 * @since 2015.03.28.
 * @version 1.0.1
 */
public class CalAdapter extends RecyclerView.Adapter<CalHolder> {
    
    /**
     * 上下文
     */
    protected Context mContext;
    
    /**
     * 是否加载更多
     */
    protected boolean isLoad = false;
    
    private CalModel mCalModel;
    
    /**
     * 父类构造
     *
     * @param context
     *            上下文
     */
    public CalAdapter(Context context, CalModel calModel) {
        mContext = context;
        this.mCalModel = calModel;
    }
    
    @Override
    public CalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CalModel.TYPE_HEADER:
                return newHolder(View.inflate(parent.getContext(),
                                              R.layout.layout_year,
                                              null),
                                 CalModel.TYPE_HEADER);
            case CalModel.TYPE_ITEM:
                return newHolder(View.inflate(parent.getContext(),
                                              R.layout.layout_day,
                                              null),
                                 CalModel.TYPE_ITEM);
        }
        return null;
    }
    
    @Override
    public void onBindViewHolder(CalHolder holder, int position) {
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
        return mCalModel.getCount();
    }
    
    /**
     * 绑定Holder数据
     *
     * @param holder
     *            容器
     * @param position
     *            角标
     */
    protected void onBindHolder(CalHolder holder, int position) {
        switch (mCalModel.getType(position)) {
            case CalModel.TYPE_HEADER:
                holder.holder(R.id.cal_year, TextView.class)
                      .setText(mCalModel.getValue(position));
                break;
            default:
                TextView calDay = holder.holder(R.id.cal_day, TextView.class);
                calDay.setText(mCalModel.getValue(position));
                if (mCalModel.getEnable(position)) {
                    calDay.setClickable(true);
                    calDay.setBackgroundResource(R.drawable.sel_bg_to_press);
                }
                else {
                    calDay.setClickable(false);
                    calDay.setBackgroundResource(R.color.colorPrimary);
                }
                if (mCalModel.getCheck(position)) {
                    calDay.setTextColor(Color.parseColor("#FFFFFF"));
                    calDay.setBackgroundResource(R.color.colorAccent);
                }
                else {
                    calDay.setTextColor(Color.parseColor("#FF4081"));
                    calDay.setBackgroundResource(R.color.white_clr);
                }
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
            return -1;
        else
            return mCalModel.getType(position);
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
    private CalHolder newHolder(View itemView, int itemViewType) {
        final CalHolder holder = new CalHolder(itemView);
        switch (itemViewType) {
            case CalModel.TYPE_HEADER:
                holder.holder(R.id.cal_year);
                break;
            case CalModel.TYPE_ITEM:
                holder.holder(R.id.cal_day)
                      .setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              Toast.makeText(mContext,
                                             mCalModel.getValue(holder.getAdapterPosition()),
                                             Toast.LENGTH_SHORT)
                                   .show();
                              int position = holder.getAdapterPosition();
                              mCalModel.setCheck(position);
                              notifyDataSetChanged();
                          }
                      });
                break;
        }
        return holder;
    }
}
