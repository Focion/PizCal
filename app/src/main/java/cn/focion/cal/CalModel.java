package cn.focion.cal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
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
    public static final int TYPE_HEADER = 1;
    
    // item
    public static final int TYPE_ITEM = 0;
    
    // 单选
    public static final int TYPE_SINGLE = 10;
    
    // 复选
    public static final int TYPE_MULTIPLE = 11;
    
    // 角标类型映射
    private SparseIntArray positionType;
    
    // 角标值映射
    private SparseArray<String> positionValue;
    
    // 单选复选的映射
    private SparseBooleanArray positionCheck;
    
    // 可选择的映射
    private SparseBooleanArray positionEnable;
    
    // 剔除不可选择的日期
    private SparseArray<List<Integer>> monthSplit;
    
    // 总数
    private int count;
    
    public CalModel() {
        positionType = new SparseIntArray();
        positionValue = new SparseArray<>();
        positionCheck = new SparseBooleanArray();
        positionEnable = new SparseBooleanArray();
        monthSplit = new SparseArray<>();
    }
    
    /**
     * 显示整年
     */
    public CalModel setYear(int year) {
        count = (isLeap(year) ? 366 : 365) + 12;
        yearMonth(year, 1);
        return this;
    }
    
    /**
     * 显示整月
     */
    public void setMonth(int year, int month) {
        count += days(year, month);
        yearMonth(year, month);
    }
    
    /**
     * 设置剔除月和日
     */
    public CalModel split(int month, Integer... day) {
        List<Integer> days = Arrays.asList(day);
        monthSplit.put(month, days);
        return this;
    }
    
    public void build() {
        
    }
    
    /**
     * 处理数据
     */
    private void yearMonth(int year, int month) {
        positionType.clear();
        positionValue.clear();
        int position = 0;
        for (int i = month; i <= 12; i++) {
            positionType.put(position, TYPE_HEADER);
            positionValue.put(position,
                              String.format(Locale.CHINA,
                                            "%d 年 %d 月",
                                            year,
                                            i));
            positionCheck.put(position, false);
            int week = dayOfWeek(year, i) - 1;
            position++;
            for (int j = 1 - week; j <= days(year, i); j++) {
                positionType.put(position, TYPE_ITEM);
                positionCheck.put(position, false);
                if (j < 1) {
                    positionValue.put(position, "");
                    positionEnable.put(position, true);
                }
                else {
                    positionValue.put(position, String.valueOf(j));
                    List<Integer> days = monthSplit.get(i);
                    positionEnable.put(position,
                                       days == null || !days.contains(j));
                }
                position++;
            }
        }
    }
    
    private SparseArray<SparseArray<ArrayList<Integer>>> yearMonthDays;
    
    public void setYearMonth(int startYear,
                             int startMonth,
                             int endYear,
                             int endMonth) {
        
    }
    
    public void setSplit(int year, int month, int... days) {
        
    }
    

    private void yearMonthDay() {
        
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
    private int days(int year, int month) {
        if (month == 2)
            return isLeap(year) ? 29 : 28;
        else if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        else
            return 31;
    }
    
    /**
     * 获取显示数量
     */
    public int getCount() {
        return count;
    }
    
    /**
     * 获取类型
     */
    public int getType(int position) {
        return positionType.get(position);
    }
    
    /**
     * 获取值
     */
    public String getValue(int position) {
        return positionValue.get(position);
    }
    
    /**
     * 选中或者取消选中
     */
    public void setCheck(int position) {
        positionCheck.put(position, !positionCheck.get(position));
    }
    
    /**
     * 获取选中
     */
    public boolean getCheck(int position) {
        return positionCheck.get(position);
    }
    
    /**
     * 获取不能选中的值
     */
    public boolean getEnable(int position) {
        return positionEnable.get(position);
    }
}
