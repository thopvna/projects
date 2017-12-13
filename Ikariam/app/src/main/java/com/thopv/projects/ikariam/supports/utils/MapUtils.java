package com.thopv.projects.ikariam.supports.utils;

import java.util.Map;

/**
 * Created by thopv on 11/1/2017.
 */

public class MapUtils {
    public static int getSum(Map<?, Integer> input){
        int result = 0;

        for(Object key : input.keySet()){
            result += input.get(key);
        }

        return result;
    }

}
