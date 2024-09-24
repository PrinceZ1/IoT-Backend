package com.example.demo.utils;

import java.util.Map;

public class MapUtil {
    public static <T> T getObjcet(Map<String, Object> params, String key, Class<T> tClass){
        Object obj = params.getOrDefault(key, null);
        if(obj != null){
            if(tClass.getTypeName().equals("java.lang.Long")){
                obj = obj != "" ? Long.parseLong(obj.toString()) : null;
            }
            else if(tClass.getTypeName().equals("java.lang.Integer")){
                obj = obj != "" ? Integer.parseInt(obj.toString()) : null;
            }
            else if(tClass.getTypeName().equals("java.lang.String")){
                obj = obj != "" ? obj.toString() : null;
            }
            return tClass.cast(obj);
        }
        return null;
    }
}
