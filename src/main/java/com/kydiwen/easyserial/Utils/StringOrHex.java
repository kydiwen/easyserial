package com.kydiwen.easyserial.Utils;

import java.math.BigInteger;

/**
 * Created by 孙文权 on 2016/12/9.
 * 十六进制string和bytearray之间的转换
 */

public class StringOrHex {
    //将bytearray转换成hexstring
    public static String ToHexString(byte[] arg, int length) {
        String result = new String();
        if (arg != null) {
            for (int i = 0; i < length; i++) {
                result = result
                        + (Integer.toHexString(
                        arg[i] < 0 ? arg[i] + 256 : arg[i]).length() == 1 ? "0"
                        + Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i])
                        : Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i]));
            }
            //转成大写
            return result.toUpperCase();
        }
        return "";
    }

    //将hexstring转换成bytearray
    public static byte[] ToByteArray(String data) {
        return (new BigInteger(data, 16)).toByteArray();
    }

    //将int转换成hexstring
    public static String intTohexstring(int data) {
        return Integer.toHexString((data & 0x000000FF) | 0xFFFFFF00).substring(6).toUpperCase();
    }

    //字符串转16进制ascii
    public static String stringTohextAscii(String data) {
        return ToHexString(data.getBytes(), data.getBytes().length);
    }
}
