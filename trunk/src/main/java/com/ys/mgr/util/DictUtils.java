package com.ys.mgr.util;


import lombok.extern.slf4j.Slf4j;

/**
 *
 * Date: 2018/02/23 21:17
 */
@Slf4j
public class DictUtils {
   /* public static final String KEY_NORMALDIVIDERATE = "normalDivideRate";
    public static final String KEY_HIGHTDIVIDERATE = "hightDivideRate";
    public static final String KEY_DIAMONDDIVIDERATE = "diamondDivideRate";
    public static final String KEY_NOPARNTERDIVIDERATE = "noParnterDivideRate";
    public static final String KEY_NORMALDIVIDERATE2 = "normalDivideRate2";
    public static final String KEY_HIGHTDIVIDERATE2 = "hightDivideRate2";
    public static final String KEY_DIAMONDDIVIDERATE2 = "diamondDivideRate2";

    public static String getDictValue(String dictKey) throws Exception {
        StringRedisTemplate stringRedisTemplate = MyApplicationContextUtil.getBean(StringRedisTemplate.class);
        Map<Object, Object> dictMap = stringRedisTemplate.boundHashOps(MyConst.DICT_REDIS_KEY).entries();
        Object dictValue = dictMap.get(dictKey);
        if (dictKey == null)
            return null;
        return dictValue.toString();
    }*/

//    public static void refreshDict() throws Exception {
//        SysDataDictionaryService sysDataDictionaryService = MyApplicationContextUtil.getBean(SysDataDictionaryService.class);
//        List<SysDataDictionary> sysDataDictionaryList = sysDataDictionaryService.selectAllForList();
//        if (sysDataDictionaryList != null) {
//            log.info("初始化数据字典: {}", sysDataDictionaryList);
//            Map<String, String> dictMap = new HashMap<>(sysDataDictionaryList.size());
//            for (SysDataDictionary sysDataDictionary : sysDataDictionaryList) {
//                dictMap.put(sysDataDictionary.getKeyStr(), sysDataDictionary.getValueStr());
//            }
//            StringRedisTemplate stringRedisTemplate = MyApplicationContextUtil.getBean(StringRedisTemplate.class);
//            stringRedisTemplate.boundHashOps(MyConst.DICT_REDIS_KEY).putAll(dictMap); // 存入redis
//        }
//    }
//
//    public static HightTokenForm getHightToken() throws Exception {
//        StringRedisTemplate stringRedisTemplate = MyApplicationContextUtil.getBean(StringRedisTemplate.class);
//        Map<Object, Object> dictMap = stringRedisTemplate.boundHashOps(MyConst.HIGHT_TOKEN_REDIS_KEY).entries();
//        if (dictMap == null)
//            return null;
//        HightTokenForm hightTokenForm = new HightTokenForm();
//        hightTokenForm.setQq(dictMap.get("qq").toString());
//        hightTokenForm.setToken(dictMap.get("token").toString());
//        return hightTokenForm;
//    }
//
//    public static void refreshHightToken() throws Exception {
//        TbkXiaoCaoHightService tbkXiaoCaoHightService = MyApplicationContextUtil.getBean(TbkXiaoCaoHightService.class);
//        TbkXiaoCaoHight tbkXiaoCaoHight = tbkXiaoCaoHightService.selectById(1);
//        if (tbkXiaoCaoHight != null) {
//            log.info("初始化高拥token: {}", tbkXiaoCaoHight);
//            Map<String, String> dictMap = new HashMap<>(2);
//            dictMap.put("qq", tbkXiaoCaoHight.getQq());
//            dictMap.put("token", tbkXiaoCaoHight.getToken());
//            StringRedisTemplate stringRedisTemplate = MyApplicationContextUtil.getBean(StringRedisTemplate.class);
//            stringRedisTemplate.boundHashOps(MyConst.HIGHT_TOKEN_REDIS_KEY).putAll(dictMap); // 存入redis
//        }
//    }
}
