package cn.chenxing.domain.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @Description 日期与时间戳的转换
 * @Author maogen.ymg
 * @Date 2020/4/12 12:05
 */
@Slf4j
public class DateUtil {
    private DateUtil() {}

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param formatStr 如：yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String timeStamp2Date(long seconds,String formatStr) {

        if(formatStr == null || formatStr.isEmpty()){
            formatStr = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(new Date(seconds));
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param dateStr 字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return TimeStamp
     */
    public static long date2TimeStamp(String dateStr,String format){
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            // 去掉毫秒
            return sdf.parse(dateStr).getTime()/1000;
        } catch (Exception e) {
            log.error("时间戳转换失败，请检查时间格式是否正确({}): {}", format, e.getMessage(), e);
            AlertUtil.showErrorAlert("时间戳转换失败，请检查时间格式是否正确(" + format + ")");
            return 0;
        }
    }

    public static long localDateTime2TimeStamp(LocalDateTime localDateTime) {

        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 取得当前时间戳（精确到秒）
     * @return timeStamp
     */
    public static String getCurrentTimeStamp(){
        long time = System.currentTimeMillis();
        return String.valueOf(time/1000);
    }
}
