package com.hks.hbase.util;

public class StringUtil {
    public static boolean isEmpty(String str){
        if(str == null || str.length() == 0){
            return true;
        }
        return false;
    }

    public static boolean isEmptyOrWhiteSpace(String str){
        if(str == null || str.length() == 0 || str.trim().length() == 0){
            return true;
        }
        return false;
    }
}