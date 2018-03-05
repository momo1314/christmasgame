package org.redrock.christmas.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringUtil {

    /**
     * 检查字符串是否为空
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.equals("");
    }

    /**
     * 检查是否存在空字符串
     * @param strs
     * @return
     */
    public static boolean hasBlank(String ... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        } else {
            for (String str : strs) {
                if (isBlank(str)) return true;
            }
            return false;
        }
    }

    public static String urlEncode(String str) {
        String data = null;
        try {
            data = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String urlDecode(String str) {
        String data = null;
        try {
            data = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }
}
