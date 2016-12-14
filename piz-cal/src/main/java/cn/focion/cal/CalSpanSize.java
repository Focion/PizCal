package cn.focion.cal;

import android.support.v7.widget.GridLayoutManager;

/**
 * Grid加载更多空间大小
 * 
 * @author Pirate
 * @since 2015.11.02.
 * @version 1.0.0
 */
public class CalSpanSize extends GridLayoutManager.SpanSizeLookup {
    
    private CalAdapter mAdapter;
    
    public CalSpanSize(CalAdapter calAdapter) {
        mAdapter = calAdapter;
    }
    
    @Override
    public int getSpanSize(int position) {
        // 获取当前ViewType
        switch (mAdapter.getItemViewType(position)) {
            case -1: // 底部加载更多
                return 7;
            case 0: // 常规Item
                return 1;
            case 1:
                return 7;
            default:
                return 1;
        }
    }
}
