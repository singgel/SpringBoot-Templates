package com.hks.hbase.util;

import java.util.List;
import java.util.Map;

import static com.hks.hbase.util.StringUtil.isEmptyOrWhiteSpace;

public class CheckUtil {
    public static void  checkIsEmpty(String val,String msg){
        if(isEmptyOrWhiteSpace(val)){
            throw new  IllegalArgumentException(msg);
        }
    }
    public static <T> void  checkIsEmpty(List<T> val, String msg){
        if(val == null || val.isEmpty()){
            throw new  IllegalArgumentException(msg);
        }
    }
    public static void  checkIsEmpty(Map val, String msg){
        if(val == null || val.isEmpty()){
            throw new  IllegalArgumentException(msg);
        }
    }
}

