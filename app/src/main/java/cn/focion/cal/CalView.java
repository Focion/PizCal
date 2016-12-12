package cn.focion.cal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 日历控件
 * 
 * @author Pirate
 * @version 1.0.0
 * @since 2016.12.12
 */
public class CalView extends FrameLayout {
    
    private CalModel mCalModel;
    
    private CalAdapter mAdapter;
    
    public CalView(Context context) {
        super(context);
    }
    
    public CalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }
    
    public CalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }
    
    /**
     * 初始化
     */
    private void initialize(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs,
                                                        R.styleable.CalView);
        CalParams.CAL_YEAR_BG = arr.getColor(R.styleable.CalView_cal_yearBg,
                                             Color.parseColor("#FFFFFF"));
        CalParams.CAL_YEAR_TEXTSIZE =
                                    arr.getDimension(R.styleable.CalView_cal_yearTextSize,
                                                     18f);
        CalParams.CAL_YEAR_TEXTCOLOR =
                                     arr.getColor(R.styleable.CalView_cal_yearTextColor,
                                                  Color.parseColor("#000000"));
        CalParams.CAL_YEAR_BOLD =
                                arr.getBoolean(R.styleable.CalView_cal_yearBold,
                                               false);
        CalParams.CAL_WEEK_BG = arr.getColor(R.styleable.CalView_cal_weekBg,
                                             Color.parseColor("#FFFFFF"));
        CalParams.CAL_WEEK_TEXTSIZE =
                                    arr.getDimension(R.styleable.CalView_cal_weekTextSize,
                                                     16f);
        CalParams.CAL_WEEK_TEXTCOLOR =
                                     arr.getColor(R.styleable.CalView_cal_weekTextColor,
                                                  Color.parseColor("#000000"));
        CalParams.CAL_WEEK_BOLD =
                                arr.getBoolean(R.styleable.CalView_cal_weekBold,
                                               false);
        CalParams.CAL_DAY_BG = arr.getResourceId(R.styleable.CalView_cal_dayBg,
                                                 R.drawable.sel_bg_to_press);
        CalParams.CAL_DAY_TEXTSIZE =
                                   arr.getDimension(R.styleable.CalView_cal_dayTextSize,
                                                    16f);
        CalParams.CAL_DAY_TEXTCOLOR =
                                    arr.getColor(R.styleable.CalView_cal_dayTextColor,
                                                 Color.parseColor("#000000"));
        arr.recycle();
        setBackgroundColor(CalParams.CAL_YEAR_BG);
        mCalModel = new CalModel();
        mAdapter = new CalAdapter(context, mCalModel);
        RecyclerView recycler = new RecyclerView(context);
        recycler.setClipToPadding(false);
        recycler.setVerticalFadingEdgeEnabled(false);
        recycler.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        GridLayoutManager manager = new GridLayoutManager(context, 7);
        manager.setSpanSizeLookup(new CalSpanSize(mAdapter));
        recycler.setLayoutManager(manager);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(mAdapter);
        addView(recycler);
    }
    
    public CalModel getCalModel() {
        return mCalModel;
    }
    
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }
    
    public void setOnCalSelectListener(OnCalSelectListener selectListener) {
        mCalModel.setOnCalSelectListener(selectListener);
    }
    
    public interface OnCalSelectListener {
        void onCalSelect(String date);
    }
}
