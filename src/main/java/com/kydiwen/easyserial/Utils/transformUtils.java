package com.kydiwen.easyserial.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 孙文权 on 2016/11/28.
 * 数据转换类
 */

public class transformUtils {
    //十六进制数据转换成十进制
    public static int hexTo10(String data) {
        return Integer.parseInt(data, 16);
    }

    //将十六进制通道值转换成十进制通道值，转换完成后直接返回24组数据
    public static String getThoroughfareData(String data) {
        return data;
    }

    /**
     * 格式化日期格式方法
     *
     * @param date 给定的时间
     * @return
     */
    public static String FormatTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 格式化时间为纯数据形式
     *
     * @param date
     * @return
     */
    public static String formateTimeLong(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
    }

    public static String parseTime(String time) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format2.format(date);
    }

    /**
     * @param preString 需要进行异或运算的数据
     * @return 返回结束码
     */
    public static String getEndOrder(String preString) {
        String result = "03";
        String string2 = preString.substring(2);
        for (int i = 0; i < string2.length(); i += 2) {
            result = exclusiveUtils.xor(result, string2.substring(i, i + 2));
        }
        return result;
    }

    //获取进行异或转换后发送的命令

    /**
     * @param preString 需要进行异或运算的数据
     * @return
     */
    public static String getOrder(String preString) {
        String result = "03";
        String string2 = preString.substring(2);
        for (int i = 0; i < string2.length(); i += 2) {
            result = exclusiveUtils.xor(result, string2.substring(i, i + 2));
        }
        return preString + result;
    }
}
