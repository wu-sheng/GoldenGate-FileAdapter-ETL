package com.ai.edc.common.utils;

import java.util.Collection;
import java.util.Map;

public class CollectionUtil {
    public static boolean isEmpty(Collection<?> c){
        if(c == null || c.size() == 0){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean isEmpty(Map<?,?> c){
        if(c == null || c.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean isEmpty(Object[] c){
        if(c == null || c.length == 0){
            return true;
        }else{
            return false;
        }
    }
}
