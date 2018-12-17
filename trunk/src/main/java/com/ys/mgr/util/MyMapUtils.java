package com.ys.mgr.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Date: 2017/12/22 13:58
 */
public class MyMapUtils {
    public static boolean isEmpty(final Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(final Map<?,?> map) {
        return !isEmpty(map);
    }

    public static Map<String, String> toMap(String kvs) {
        if (MyStringUtils.isBlank(kvs))
            return null;

        Map<String, String> map = new HashMap<>();
        String[] params = kvs.split("&");
        if (params != null && params.length > 0) {
            for (String param : params) {
                if (MyStringUtils.isNotBlank(param)) {
                    String[] kv = param.split("=");
                    if (kv != null && kv.length == 2) {
                        map.put(kv[0], kv[1]);
                    }
                }
            }
        }
        return map;
    }
}
