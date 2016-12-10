package cn.focion.cal;

import android.util.SparseArray;
import android.util.SparseIntArray;

/**
 * Created by PS on 16/12/10.
 */

public class CalUtil {
    
    private SparseIntArray positionType;
    
    private SparseArray<String> positionValue;
    
    private int count;
    
    public CalUtil() {
        positionType = new SparseIntArray();
        positionValue = new SparseArray<>();
    }
    
    public void initializeYear(int year) {
        positionType.clear();
        positionValue.clear();
        count = isLeap(year) ? 366 : 365 + 12;
        int position = 0;
        for (int i = 1; i <= 12; i++) {
            positionType.put(position, 1);
            positionValue.put(position, String.valueOf(i));
            position++;
            for (int j = 1; j <= days(year, i); j++) {
                positionType.put(position, 0);
                positionValue.put(position, String.valueOf(j));
                position++;
            }
        }
    }
    
    private boolean isLeap(int year) {
        return (year % 100 == 0 && year % 400 == 0)
               || (year % 100 != 0 && year % 4 == 0);
    }
    
    private int days(int year, int month) {
        if (month == 2)
            return isLeap(year) ? 29 : 28;
        else if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        else
            return 31;
    }
    
    public int getCount() {
        return count;
    }
    
    public int getType(int position) {
        return positionType.get(position);
    }
    
    public String getValue(int position) {
        return positionValue.get(position);
    }
}
