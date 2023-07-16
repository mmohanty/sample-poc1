package com.ezbank;

import java.util.HashMap;
import java.util.Map;

public class Util {

    static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(0, "JANUARY");
        map.put(1, "FEBRUARY");
        map.put(2, "MARCH");
        map.put(3, "APRIL");
        map.put(4, "MAY");
        map.put(5, "JUNE");
        map.put(6, "JULY");
        map.put(7, "AUGUST");
        map.put(8, "SEPTEMBER");
        map.put(9, "OCTOBER");
        map.put(10, "NOVEMBER");
        map.put(11, "DECEMBER");
    }
    public static String getMonth(int index){
        return map.get(index);
    }
}
