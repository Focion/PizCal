package cn.focion.cal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 列表适配器
 * 
 * @author Pirate
 * @since 2015.03.28.
 * @version 1.0.1
 */
public class CalAdapter extends RecyclerView.Adapter<CalHolder> {
    
    private Context mContext;
    
    private CalModel mCalModel;
    
    public CalAdapter(Context context, CalModel calModel) {
        mContext = context;
        this.mCalModel = calModel;
    }
    
    @Override
    public CalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CalModel.TYPE_YEAR:
                return newHolder(View.inflate(parent.getContext(),
                                              R.layout.layout_year,
                                              null),
                                 CalModel.TYPE_YEAR);
            case CalModel.TYPE_DAY:
                return newHolder(View.inflate(parent.getContext(),
                                              R.layout.layout_day,
                                              null),
                                 CalModel.TYPE_DAY);
        }
        return null;
    }
    
    @Override
    public void onBindViewHolder(CalHolder holder, int position) {
        switch (mCalModel.getType(position)) {
            case CalModel.TYPE_YEAR:
                // 年
                TextView calYear = holder.holder(R.id.cal_year, TextView.class);
                calYear.setBackgroundColor(CalParams.CAL_YEAR_BG);
                calYear.setTextSize(CalParams.CAL_YEAR_TEXTSIZE);
                calYear.setTextColor(CalParams.CAL_YEAR_TEXTCOLOR);
                if (CalParams.CAL_YEAR_BOLD)
                    calYear.setTextAppearance(mContext, R.style.boldStyle);
                calYear.setText(mCalModel.getValue(position));
                // 周
                TableRow calWeek = holder.holder(R.id.cal_week, TableRow.class);
                calWeek.setBackgroundColor(CalParams.CAL_WEEK_BG);
                TextView sunday = holder.holder(R.id.sunday, TextView.class);
                TextView monday = holder.holder(R.id.monday, TextView.class);
                TextView tuesday = holder.holder(R.id.tuesday, TextView.class);
                TextView wednesday = holder.holder(R.id.wednesday,
                                                   TextView.class);
                TextView thursday =
                                  holder.holder(R.id.thursday, TextView.class);
                TextView friday = holder.holder(R.id.friday, TextView.class);
                TextView saturday =
                                  holder.holder(R.id.saturday, TextView.class);
                sunday.setTextSize(CalParams.CAL_WEEK_TEXTSIZE);
                sunday.setTextColor(CalParams.CAL_WEEK_TEXTCOLOR);
                monday.setTextSize(CalParams.CAL_WEEK_TEXTSIZE);
                monday.setTextColor(CalParams.CAL_WEEK_TEXTCOLOR);
                tuesday.setTextSize(CalParams.CAL_WEEK_TEXTSIZE);
                tuesday.setTextColor(CalParams.CAL_WEEK_TEXTCOLOR);
                wednesday.setTextSize(CalParams.CAL_WEEK_TEXTSIZE);
                wednesday.setTextColor(CalParams.CAL_WEEK_TEXTCOLOR);
                thursday.setTextSize(CalParams.CAL_WEEK_TEXTSIZE);
                thursday.setTextColor(CalParams.CAL_WEEK_TEXTCOLOR);
                friday.setTextSize(CalParams.CAL_WEEK_TEXTSIZE);
                friday.setTextColor(CalParams.CAL_WEEK_TEXTCOLOR);
                saturday.setTextSize(CalParams.CAL_WEEK_TEXTSIZE);
                saturday.setTextColor(CalParams.CAL_WEEK_TEXTCOLOR);
                if (CalParams.CAL_WEEK_BOLD) {
                    sunday.setTextAppearance(mContext, R.style.boldStyle);
                    monday.setTextAppearance(mContext, R.style.boldStyle);
                    tuesday.setTextAppearance(mContext, R.style.boldStyle);
                    wednesday.setTextAppearance(mContext, R.style.boldStyle);
                    thursday.setTextAppearance(mContext, R.style.boldStyle);
                    friday.setTextAppearance(mContext, R.style.boldStyle);
                    saturday.setTextAppearance(mContext, R.style.boldStyle);
                }
                break;
            default:
                TextView calDay = holder.holder(R.id.cal_day, TextView.class);
                calDay.setText(mCalModel.getValue(position));
                calDay.setTextSize(CalParams.CAL_DAY_TEXTSIZE);
                calDay.setTextColor(CalParams.CAL_DAY_TEXTCOLOR);
                calDay.setClickable(mCalModel.getEnable(position));
                calDay.setBackgroundResource(CalParams.CAL_DAY_BG);
                break;
        }
    }
    
    @Override
    public int getItemCount() {
        return mCalModel.getCount();
    }
    
    @Override
    public int getItemViewType(int position) {
        return mCalModel.getType(position);
    }
    
    private CalHolder newHolder(View itemView, int itemViewType) {
        final CalHolder holder = new CalHolder(itemView);
        switch (itemViewType) {
            case CalModel.TYPE_YEAR:
                holder.holder(R.id.cal_year);
                break;
            case CalModel.TYPE_DAY:
                holder.holder(R.id.cal_day)
                      .setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              mCalModel.onCalSelect(holder.getAdapterPosition());
                          }
                      });
                break;
        }
        return holder;
    }
}
