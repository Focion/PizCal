package cn.focion.cal;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @author Pirate
 * @version 1.0.0
 * @since 2016.11.08
 */

@SuppressWarnings("ALL")
public class CalHolder extends RecyclerView.ViewHolder {
    
    /**
     * 构造参数
     *
     * @param itemView
     *            布局View
     */
    public CalHolder(View item) {
        super(item);
    }
    
    /**
     * 静态Holder容器
     *
     * @param id
     *            资源Id
     * @param <T>
     *            泛型
     * @return 静态View
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T holder(int id) {
        return holder(id, null);
    }
    
    /**
     * 静态Holder容器
     *
     * @param id
     *            资源Id
     * @param <T>
     *            泛型
     * @return 静态View
     */
    public <T extends View> T holder(int id, Class<T> clazz) {
        // 创建容器
        SparseArray<View> holder = (SparseArray<View>) itemView.getTag();
        // 如果容器为空
        if (holder == null) {
            holder = new SparseArray<>();
            itemView.setTag(holder);
        }
        
        // 容器不为空
        View child = holder.get(id);
        if (child == null) {
            // 获取布局控件
            child = itemView.findViewById(id);
            // 放入容器
            holder.put(id, child);
        }
        // 返回控件TIME_RUN
        return (T) child;
    }
}
