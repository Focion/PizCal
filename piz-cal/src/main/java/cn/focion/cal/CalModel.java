package cn.focion.cal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

/**
 * 日历模型
 * 
 * @author Pirate
 * @version 1.0.0
 * @since 2016.12.10
 */
public class CalModel {
    
    // 头
    public static final int TYPE_YEAR = 1;
    
    // item
    public static final int TYPE_DAY = 0;
    
    // 开始年份
    private int startYear;
    
    // 结束年份
    private int endYear;
    
    // 开始月份
    private int startMonth;
    
    // 结束月份
    private int endMonth;
    
    // 剔除不可选择的日期
    private SparseArray<SparseArray<ArrayList<Integer>>> splitDays;
    
    // 角标类型映射
    private SparseIntArray positionType;
    
    // 角标值映射
    private SparseArray<String> positionValue;
    
    private SparseArray<String> positionResult;
    
    // 可选择的映射
    private SparseBooleanArray positionEnable;
    
    // 总数
    private int count = 0;
    
    private CalView.OnCalSelectListener mListener;
    
    public CalModel() {
        positionType = new SparseIntArray();
        positionEnable = new SparseBooleanArray();
        positionValue = new SparseArray<>();
        positionResult = new SparseArray<>();
        splitDays = new SparseArray<>();
    }
    
    public CalModel setYear(int startYear, int endYear) {
        return setYear(startYear, endYear, 1, 12);
    }
    
    public CalModel setYear(int startYear,
                            int endYear,
                            int startMonth,
                            int endMonth) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        return this;
    }
    
    public CalModel setSplitMonth(int year, int... months) {
        for (int month : months) {
            int size = dayOfMonth(year, month);
            int[] days = new int[size];
            for (int day = 0; day < size; day++)
                days[day] = day + 1;
            setSplitDay(year, month, days);
        }
        return this;
    }
    
    /**
     * 过滤某年某月的某些天
     * 
     * @param year
     *            年
     * @param month
     *            月
     * @param days
     *            日
     */
    public CalModel setSplitDay(int year, int month, int... days) {
        if (startYear <= 0 && endYear <= 0)
            throw new RuntimeException("年数据格式错误");
        if (startMonth <= 0 && endMonth <= 0)
            throw new RuntimeException("月数据格式错误");
        // 防止输入的年份大于显示的年份
        if (year >= startYear && year <= endYear) {
            if (splitDays.size() == 0)
                splitDays.put(year, new SparseArray<ArrayList<Integer>>());
            SparseArray<ArrayList<Integer>> months = splitDays.get(year);
            // 防止输入的月份大于显示的年份
            if ((month >= startMonth && month <= 12)
                || (month >= 1 && month <= endMonth)) {
                if (months.size() == 0)
                    months.put(month, new ArrayList<Integer>());
                ArrayList<Integer> dayArr = months.get(month);
                int dayLimit = dayOfMonth(year, month);
                for (int day : days) {
                    if (day <= dayLimit)
                        dayArr.add(day);
                }
            }
        }
        return this;
    }
    
    public void build() {
        // 清除数据
        positionType.clear();
        positionValue.clear();
        positionEnable.clear();
        int position = 0;
        // 循环年份
        for (int year = startYear; year <= endYear; year++) {
            int fooStart = 1;
            int fooEnd = 12;
            if (year == startYear) {
                // 第一年
                fooStart = startMonth;
                fooEnd = 12;
            }
            else if (year == endYear) {
                // 最后一年
                fooStart = 1;
                fooEnd = endMonth;
            }
            for (int month = fooStart; month <= fooEnd; month++) {
                int dayOfWeek = dayOfWeek(year, month) - 1;
                int dayOfMonth = dayOfMonth(year, month);
                count++;
                count += dayOfMonth;
                count += dayOfWeek;
                // 组装数据
                positionType.put(position, CalModel.TYPE_YEAR);
                positionValue.put(position,
                                  String.format(Locale.CHINA,
                                                "%d 年 %d 月",
                                                year,
                                                month));
                position++;
                for (int day = 1 - dayOfWeek; day <= dayOfMonth; day++) {
                    positionType.put(position, TYPE_DAY);
                    if (day < 1) {
                        positionValue.put(position, "");
                        positionEnable.put(position, false);
                    }
                    else {
                        positionValue.put(position, String.valueOf(day));
                        positionEnable.put(position, true);
                        SparseArray<ArrayList<Integer>> monthSplits =
                                                                    splitDays.get(year);
                        if (monthSplits != null) {
                            ArrayList<Integer> splits = monthSplits.get(month);
                            positionEnable.put(position,
                                               splits == null
                                                         || !splits.contains(day));
                        }
                        positionResult.put(position,
                                           String.format(Locale.CHINA,
                                                         "%d-%d-%d",
                                                         year,
                                                         month,
                                                         day));
                    }
                    position++;
                }
            }
        }
    }
    
    /**
     * 一号是周几
     */
    private int dayOfWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 是否是闰年
     */
    private boolean isLeap(int year) {
        return (year % 100 == 0 && year % 400 == 0)
               || (year % 100 != 0 && year % 4 == 0);
    }
    
    /**
     * 月的天数
     */
    private int dayOfMonth(int year, int month) {
        if (month == 2)
            return isLeap(year) ? 29 : 28;
        else if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        else
            return 31;
    }
    
    void onCalSelect(int position) {
        mListener.onCalSelect(positionResult.get(position));
    }
    
    void setOnCalSelectListener(CalView.OnCalSelectListener calSelectListener) {
        mListener = calSelectListener;
    }
    
    /**
     * 获取显示数量
     */
    int getCount() {
        return count;
    }
    
    /**
     * 获取类型
     */
    int getType(int position) {
        return positionType.get(position);
    }
    
    /**
     * 获取值
     */
    String getValue(int position) {
        return positionValue.get(position);
    }
    
    /**
     * 获取不能选中的值
     */
    boolean getEnable(int position) {
        return positionEnable.get(position);
    }
}
