package com.ys.mgr.config;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 常量
 * <p>
 *
 * Date: 2017/12/8 09:18
 */
public class MyConst {
    public static final String CHARSET_NAME = "UTF-8";

    /**
     * http请求的基本验证
     */
    public final static String BASE_AUTH = "qqlg-client-1:qqlg-client-1";

    /**
     * http请求的SHA256_KEY
     */
    public final static String SHA256_KEY = "xyz!*#123";

    /**
     * 超级管理员id
     */
    public final static Integer ADMIN_ID = 1;

    /**
     * session中用户的名称
     */
    public final static String SESSION_USER_NAME = "qqlgMgrSessionUser";
    /**
     * session中权限的名称
     */
    public final static String SESSION_RESOURCES_NAME = "qqlgMgrSessionResources";

    public static final String CACHE_NAME = "ztq_cache";
    public static final String KEY_PRE = CACHE_NAME + "_key_";

    public static final String getKeyPre(Class clazz) {
        return KEY_PRE + clazz.getName() + "_";
    }

    /**
     * 加密的参数名称
     */
    public static final String[] HASH_PARAM_NAMES = new String[]{"htmlHashParam001", "androidHashParam001", "iosHashParam001"};
    /**
     * 加密的参数key，和名称一一对应
     */
    public static final String[] HASH_PARAM_KEYS = new String[]{"HFI9E*&7YH_-Ab3H", "AFI87YHE*&_-ab2A", "IFI5*&_7YhE-ab5I"};

    /**
     * 根据参数名称得到加密的key
     *
     * @param name 参数名称
     * @return 加密的key
     */
    public static final String getParamKeyByName(String name) {
        try {
            return HASH_PARAM_KEYS[ArrayUtils.indexOf(HASH_PARAM_NAMES, name)];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final static String DICT_REDIS_KEY = "ZTH_DICT_REDIS_KEY";
    public final static String HIGHT_TOKEN_REDIS_KEY = "ZTH_HIGHT_TOKEN_REDIS_KEY";

    public static final String GOODS_TYPE = "good_type";

    //高德地图转坐标系的key
    public static final String LBS_AMAP = "fd6440dd5b5666548c211d1558ad96db";

    //高德地图转GPS坐标系
    public static final String LBS_AMAP_TYPE_GPS = "gps";

    //高德地图转百度坐标系
    public static final String LBS_AMAP_TYPE_BAIDU = "baidu";

    //百度的Server服务key
    public static final String BAIDU_SERVER_KEY = "Knkpfzhb1dQfj9f7Ywayc3YkwND0vwOo";

    //百度地图的API源坐标类型(3 = google地图、soso地图、aliyun地图、mapabc地图和amap地图所用坐标，国测局（gcj02）坐标)
    public static final String BAIDU_AMAP_FROM = "3";

    //百度地图的API源坐标类型(5 = bd09ll(百度经纬度坐标);)
    public static final String BAIDU_AMAP_TO = "3";

    public static final Integer MONTHS_AGO = 1;

    public static final Integer TIMER_ERROR_TYPE_CLICKNUM = 1;
    public static final Integer TIMER_ERROR_TYPE_HOTSEARCH = 2;
    public static final Integer TIMER_ERROR_TYPE_ADVERTISEMENTSCORE = 3;
}
