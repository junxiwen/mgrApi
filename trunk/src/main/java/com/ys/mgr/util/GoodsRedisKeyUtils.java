package com.ys.mgr.util;


import com.ys.mgr.config.MyApplicationContextUtil;
import com.ys.mgr.config.MyConst;
//import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by ke cheng song on 2018/3/1.
 */
public class GoodsRedisKeyUtils {

    /*public static String getGoodRedisKey() {
        try {
            StringRedisTemplate stringRedisTemplate = MyApplicationContextUtil.getBean(StringRedisTemplate.class);
            String key = stringRedisTemplate.opsForValue().get(MyConst.GOODS_TYPE);
            if (MyStringUtils.isBlank(key)) {
                key = "0";
            } else {
                if (key.equals("1")) {
                    key = "0";
                } else {
                    key = "1";
                }
            }
            return key;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCollectionName() {
        String key = getGoodRedisKey();
        String collectionName = "";
        if (MyStringUtils.isNotBlank(key) && key.equals("1")) {
            collectionName = "goods";
        } else {
            collectionName = "goodsOne";
        }
        return collectionName;
    }*/
}
