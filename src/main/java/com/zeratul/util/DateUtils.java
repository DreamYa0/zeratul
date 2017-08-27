package com.zeratul.util;


import org.apache.commons.lang3.math.NumberUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public abstract class DateUtils {

    private static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static String dateToStr(Date date, String pattern) {
        return dateToStr(date, pattern, Locale.CHINA);
    }

    /**
     * @param str 时间戳 或者 yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String str) throws ParseException {
        if (NumberUtils.isNumber(str)) {
            return new Date(Integer.valueOf(str) * 1000L);
        } else {
            return strToDate(str, "yyyy-MM-dd");
        }
    }

    public static boolean isSameDay(Date day1, Date day2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        if (ds1.equals(ds2)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSameDay(int unixTime, Date day) {
        return isSameDay(new Date(unixTime * 1000L), day);
    }

    public static boolean isSameDay(int unixTime1, int unixTime2) {
        return isSameDay(new Date(unixTime1 * 1000L), new Date(unixTime2 * 1000L));
    }

    public static String dateToStr(Date date, String pattern, Locale locale) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        DateFormat ymdhmsFormat = new SimpleDateFormat(pattern, locale);

        return ymdhmsFormat.format(date);
    }

    public static Date strToDate(String str, String pattern)
            throws ParseException {
        return strToDate(str, pattern, Locale.CHINA);
    }

    public static Date strToDate(String str, String pattern, Locale locale)
            throws ParseException {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        DateFormat ymdhmsFormat = new SimpleDateFormat(pattern, locale);
        return ymdhmsFormat.parse(str);
    }

    public static Date getToday() {
        Calendar ca = Calendar.getInstance();
        return ca.getTime();
    }

    public static Date mkDate(int year, int month, int date) {
        Calendar ca = Calendar.getInstance();
        ca.set(year, month - 1, date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdf.format(ca.getTime());
        return ca.getTime();
    }

    public Date getGmtDate(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time.longValue());
        int offset = calendar.get(15) / 3600000 + calendar.get(16) / 3600000;
        calendar.add(10, -offset);
        Date date = calendar.getTime();
        return date;
    }

    public static String getSpecifyDate(int interval, String format) {
        return getSpecifyDate(interval, format, Locale.CHINA);
    }

    public static String getSpecifyDate(int interval, String format,
                                        Locale locale) {
        Calendar cal = new GregorianCalendar();
        cal.add(5, interval);
        return dateToStr(cal.getTime(), format, locale);
    }

    public static String getSpecifyMonth(int interval, String format) {
        return getSpecifyMonth(interval, format, Locale.CHINA);
    }

    public static String getSpecifyMonth(int interval, String format,
                                         Locale locale) {
        Calendar cal = new GregorianCalendar();
        cal.add(2, interval);
        return dateToStr(cal.getTime(), format, locale);
    }

    public static String getSpecifyYear(int interval, String format) {
        return getSpecifyYear(interval, format, Locale.CHINA);
    }

    public static String getSpecifyYear(int interval, String format,
                                        Locale locale) {
        Calendar cal = new GregorianCalendar();
        cal.add(1, interval);
        return dateToStr(cal.getTime(), format, locale);
    }

    public static String getSpecifyDate(String date, int interval, String format) {
        return getSpecifyDate(date, interval, format, Locale.CHINA);
    }

    public static String getSpecifyDate(String date, int interval,
                                        String format, Locale locale) {
        Date d = null;
        try {
            d = strToDate(date, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.add(5, interval);
        return dateToStr(cal.getTime(), format, locale);
    }

    public static Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static long nowHour() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now(ZoneId.systemDefault()).getYear(), LocalDateTime.now(ZoneId.systemDefault()).getMonth(),
                LocalDateTime.now(ZoneId.systemDefault()).getDayOfMonth(), LocalDateTime.now(ZoneId.systemDefault()).getHour(), 0);
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public static long preHour() {
        return nowHour() - TimeUnit.HOURS.toSeconds(1);
    }

    public static long zeroHour() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now(ZoneId.systemDefault()).getYear(), LocalDateTime.now(ZoneId.systemDefault()).getMonth(),
                LocalDateTime.now(ZoneId.systemDefault()).getDayOfMonth(), 0, 0);
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public static String nowDay(String formatter) {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        return localDate.format(dateTimeFormatter);
    }

    private static long nowHour(long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
        localDateTime = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), localDateTime.getHour(), 0);
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    private static long preHour(long time) {
        return nowHour(time) - TimeUnit.HOURS.toSeconds(1);
    }

    private static long zeroHour(long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
        localDateTime = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), 0, 0);
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public static String nowHour(String formatter) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now(ZoneId.systemDefault()).getYear(), LocalDateTime.now(ZoneId.systemDefault()).getMonth(),
                LocalDateTime.now(ZoneId.systemDefault()).getDayOfMonth(), LocalDateTime.now(ZoneId.systemDefault()).getHour(), 0);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String preHour(String formatter) {
        int h = LocalDateTime.now(ZoneId.systemDefault()).getHour() - 1;
        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now(ZoneId.systemDefault()).getYear(), LocalDateTime.now(ZoneId.systemDefault()).getMonth(),
                LocalDateTime.now(ZoneId.systemDefault()).getDayOfMonth(), h < 0 ? 0 : h, 0);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String zeroHour(String formatter) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDateTime.now(ZoneId.systemDefault()).getYear(), LocalDateTime.now(ZoneId.systemDefault()).getMonth(),
                LocalDateTime.now(ZoneId.systemDefault()).getDayOfMonth(), 0, 0);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        return localDateTime.format(dateTimeFormatter);
    }

    private static String nowHour(long time, String formatter) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 将日期转为最近小时的时间戳
     *
     * @param dateTime
     * @return
     */
    public static long toUnixTimeHour(String dateTime) {
        return nowHour(toUnixTime(dateTime));
    }

    /**
     * 将日期转为时间戳
     *
     * @param dateTime
     * @return
     */
    public static long toUnixTime(String dateTime) {
        return toUnixTime(dateTime, FORMATTER);
    }

    /**
     * 将日期转为时间戳
     *
     * @param dateTime
     * @param formatter
     * @return
     */
    public static long toUnixTime(String dateTime, String formatter) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(formatter)).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    public static long[] hour() {
        return hour(Instant.now().atZone(ZoneId.systemDefault()).toEpochSecond());
    }

    public static long[] hour(long time) {
        long[] h = new long[3];
        long now = nowHour(time);
        long pre = preHour(time);
        long zero = zeroHour(time);
        pre = pre < zero ? zero : pre;
        h[0] = zero;
        h[1] = pre;
        h[2] = now;
        return h;
    }

    public static String[] hour(String formatter) {
        return hour(Instant.now().atZone(ZoneId.systemDefault()).toEpochSecond(), formatter);
    }

    public static String[] hour(long time, String formatter) {
        formatter = (formatter == null || formatter.isEmpty()) ? FORMATTER : formatter;
        String[] h = new String[3];
        long now = nowHour(time);
        long pre = preHour(time);
        long zero = zeroHour(time);
        pre = pre < zero ? zero : pre;
        h[0] = nowHour(zero, formatter);
        h[1] = nowHour(pre, formatter);
        h[2] = nowHour(now, formatter);
        return h;
    }

    public static String transformToStr(long time, String formatter) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String transformToStr(long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMATTER);
        return localDateTime.format(dateTimeFormatter);
    }
}
