package org.wzy.sqltemplate.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

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

    /**
     * 给一个指定日期，返回这个日期对应的星期的星期一是哪天
     *
     * @param date 给定的日期
     * @return 指定日期所在星期的星期一
     */
    public String getWeekMonday(String date) {
        DateTime d = DateUtil.parse(date, "yyyyMMdd");
        Calendar calendar = DateUtil.beginOfWeek(d.toCalendar(), true);
        return DateUtil.format(calendar.getTime(), "yyyyMMdd");
    }

    /**
     * 给一个指定日期，返回这个日期对应的星期的星期五是哪天
     *
     * @param date 给定的日期
     * @return 指定日期所在星期的星期五
     */
    public String getWeekFriday(String date) {
        DateTime d = DateUtil.parse(date, "yyyyMMdd");
        Calendar calendar = DateUtil.beginOfWeek(d.toCalendar(), true);
        calendar.add(Calendar.DATE, 4);
        return DateUtil.format(calendar.getTime(), "yyyyMMdd");
    }

    /**
     * 给一个指定日期，返回这个日期对应的上一个星期的星期一是哪天
     *
     * @param date 给定的日期
     * @return 指定日期的上一个星期的星期一
     */
    public String getLastWeekMonday(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date d = simpleDateFormat.parse(date);
            DateTime dateTime = DateUtil.offsetWeek(d, -1);
            Calendar calendar = DateUtil.beginOfWeek(dateTime.toCalendar(), true);
            Date monday = calendar.getTime();
            return simpleDateFormat.format(monday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 给一个指定日期，返回这个日期对应的上一个星期的星期五是哪天
     *
     * @param date 给定的日期
     * @return 指定日期的上一个星期的星期五
     */
    public String getLastWeekFriday(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date d = simpleDateFormat.parse(date);
            DateTime dateTime = DateUtil.offsetWeek(d, -1);
            Calendar calendar = DateUtil.beginOfWeek(dateTime.toCalendar(), true);
            calendar.add(Calendar.DATE, 4);
            Date friday = calendar.getTime();
            return simpleDateFormat.format(friday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
