package com.ys.mgr.config;

/**
 *
 * Date: 2017/12/28 16:53
 */
public class MyPropertyUtil {
    private static MyDynamicPropertyUtil myDynamicPropertyUtil;
    private static MyStaticPropertyUtil myStaticPropertyUtil;

    public static MyDynamicPropertyUtil getMyDynamicPropertyUtils() {
        if (myDynamicPropertyUtil == null) {
            myDynamicPropertyUtil = MyApplicationContextUtil.getBean("myDynamicPropertyUtil",
                    MyDynamicPropertyUtil.class);
        }
        return myDynamicPropertyUtil;
    }

    public static MyStaticPropertyUtil getMyStaticPropertyUtils() {
        if (myStaticPropertyUtil == null) {
            myStaticPropertyUtil = MyApplicationContextUtil.getBean("myStaticPropertyUtil",
                    MyStaticPropertyUtil.class);
        }
        return myStaticPropertyUtil;
    }
}
