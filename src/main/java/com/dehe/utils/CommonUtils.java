package com.dehe.utils;

import java.security.MessageDigest;
import java.util.Map;

/**
 * 工具类
 */
public class CommonUtils {


    /**
     * MD5加密工具类
     * @param data
     * @return
     */
    public static String MD5(String data)  {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }

            return sb.toString().toUpperCase();
        } catch (Exception exception) {
        }
        return null;

    }
    public   static  String JM(String inStr) {
        char [] a = inStr.toCharArray();
        for  ( int  i =  0 ; i < a.length; i++) {
            a[i] = (char ) (a[i] ^  't' );
        }
        String k = new  String(a);
        return  k;
    }


    public static void main(String[] args) {
        System.out.println(JM("C8837B23FF8AAA8A2DDE915473CE0991"));
    }

}
