package com.fei.asmdepend;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * See
 * https://github.com/JeasonWong/CostTime/blob/master/costtime/src/main/java/me/wangyuwei/costtime/TimeCache.java
 */
public class TimeCache {

    public static Map<String, Long> sStartTime = new HashMap<>();
    public static Map<String, Long> sEndTime = new HashMap<>();
    public static int thresholdInMs;

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
        Long start = sStartTime.get(methodName);
        if (start == null) {
            return;
        }
        Long end = sEndTime.get(methodName);
        if (end == null) {
            return;
        }
        final long time = end - start;
        final long timeMs = time / 1000000;
        if (timeMs < TimeCache.thresholdInMs) {
            return;
        }
        System.out.println("TimeCache-->getCostTime--" + methodName + "-- " + timeMs + "毫秒");
        Log.i("MethodRunTimes", methodName + "-- " + timeMs + "毫秒");
    }

    public static void setThresholdInMs(int thresholdInMs) {
        TimeCache.thresholdInMs = thresholdInMs;
    }
}