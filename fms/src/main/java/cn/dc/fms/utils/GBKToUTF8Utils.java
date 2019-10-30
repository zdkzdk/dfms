package cn.dc.fms.utils;

import java.io.UnsupportedEncodingException;

public class GBKToUTF8Utils {
    public static String gbkToUTF8(String msg) {
        try {
            return new String(msg.getBytes("gbk"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
