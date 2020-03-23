package org.wzy.sqltemplate.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 便于模板中日期的一些操作
 *
 * @author xingchuan.qxc
 * @since 2020/03/23
 */
public class CommonUtil {

    /**
     * 通过一个日期的入参，得出N天后的日期
     *
     * @param date 格式yyyyMMdd
     * @param days 加多少天
     * @return 计算之后的日期，格式yyyyMMdd
     */
    public String addDays(String date, int days) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date d = simpleDateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            calendar.add(Calendar.DATE, days);
            return simpleDateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
