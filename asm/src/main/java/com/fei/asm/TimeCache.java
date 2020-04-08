package com.fei.asm;

import java.util.HashMap;
import java.util.Map;

/**
 * See
 * https://github.com/JeasonWong/CostTime/blob/master/costtime/src/main/java/me/wangyuwei/costtime/TimeCache.java
 */
public class TimeCache {

    public static Map<String, Long> sStartTime = new HashMap<>();
    public static Map<String, Long> sEndTime = new HashMap<>();

    public static void setStartTime(String methodName) {
        final long startTime = System.nanoTime();
        System.out.println("TimeCache-->setStartTime--" + methodName + "--" + startTime);
        sStartTime.put(methodName, startTime);
    }

    public static void setEndTime(String methodName) {
        final long endTime = System.nanoTime();
        System.out.println("TimeCache-->setStartTime--" + methodName + "--" + endTime);
        sEndTime.put(methodName, endTime);
    }

    public static void computeMethodTime(String methodName) {
        long start = sStartTime.get(methodName);
        long end = sEndTime.get(methodName);
        final long time = end - start;
        System.out.println("TimeCache-->getCostTime--" + methodName + "--" + time);
    }

}