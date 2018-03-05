package org.redrock.christmas.util;

import java.util.Calendar;

public class DateUtil {

    /**
     * 获得系统时间
     * @return 根据数据库date格式格式化的t_date
     */
    public static String getdate() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int year = now.get(now.get(Calendar.ERA));
        String t_date = year+"-"+month+"-"+day;
        return t_date;
    }
}
