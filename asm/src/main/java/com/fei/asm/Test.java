package com.fei.asm;

/**
 * Created by PengFeifei on 2020/4/9.
 * 利用插件生成asm代码时,需要先对目标文件TimeCache先生成,然后再对Test文件生成
 * 1 首先对TimeCach使用插件
 * 2 然后对Test文件使用插件
 */
public class Test {

    private final String methodName;

    public Test(String methodName) {
        this.methodName = methodName;
    }

//    public void aa() {
//        TimeCache.setStartTime(methodName);
//    }
//
//    public void bb() {
//        TimeCache.setEndTime(methodName);
//        TimeCache.computeMethodTime(methodName);
//    }

    public void cc() {
        System.out.println("&&&&&&******^^^^^^%%%%%$$$$$$#######");
    }


}
