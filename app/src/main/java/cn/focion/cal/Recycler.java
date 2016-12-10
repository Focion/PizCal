package cn.focion.cal;

import android.view.View;

/**
 * Recycler带距离顶端的高度
 *
 * @author Pirate
 * @since 2015.03.28.
 * @version 1.0.0
 */
public interface Recycler {
    
    /**
     * 加载更多的接口
     */
    interface LoadMoreListener {
        /**
         * 加载更多
         */
        void onLoadMore();
    }
    
    /**
     * 点击Item的时间监听
     */
    interface ItemClickListener {
        /**
         * RecyclerView点击监听
         * 
         * @param v
         *            布局View
         * @param position
         *            索引角标
         */
        void onRecyclerItemClick(View v, int position);
    }
    
    /**
     * 长按点击Item的时间监听
     */
    interface ItemLongClickListener {
        /**
         * 长按RecyclerView点击监听
         *
         * @param v
         *            布局View
         * @param position
         *            索引角标
         * @return 是否消耗
         */
        boolean onRecyclerItemLongClick(View v, int position);
    }
}
