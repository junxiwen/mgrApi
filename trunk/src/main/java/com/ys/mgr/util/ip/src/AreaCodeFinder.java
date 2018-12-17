package com.ys.mgr.util.ip.src;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

//中国有重名的区， 没有市的名字是一样的。
//只有 吉林省 和 吉林市 的省市名字是一样的。
//区重名的很多
//370103	市中区
//130105	新华区
//150102	新城区
//130602	新市区
//310107	普陀区
//110105	朝阳区
//130103	桥东区
//130104	桥西区
//330205	江北区
//120102	河东区
//210902	海州区
//211204	清河区
//440111	白云区
//140203	矿区
//220403	西安区
//330106	西湖区
//140311	郊区
//210302	铁东区
//210106	铁西区
//130102	长安区
//150204	青山区
//320106	鼓楼区

public class AreaCodeFinder {
    private static final Logger log = LoggerFactory.getLogger(AreaCodeFinder.class);
    private final static Properties allAreas = new Properties();

    static {
        // 无需加锁， 假设系统初始化一定成功！！
        try {

            allAreas.load(new InputStreamReader(AreaCodeFinder.class.getResourceAsStream("/areaCode.properties"), "utf8"));
//			for (Enumeration e = allAreas.propertyNames(); e.hasMoreElements();) {
//				String key = (String) e.nextElement();
//				System.out.println(key + "****:" + allAreas.getProperty(key));
//			}
            log.debug("find total {} areacode.", allAreas.size());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("地区代码加载失败！");
        }
    }

//	河北省保定市 星宇网吧
//	上海市徐汇区 电信
//	上海市 移动
//	湖北省武汉市 电信
//	局域网 对方和您在同一内部网
//	内蒙古包头市 联通
//	西藏拉萨市 教育网骨干节点
//	浙江省温州市 联通
//	广西南宁市 电信
//	台湾省台北市 中华电信
//	德国
//	香港 特别行政区
//	巴西
//	伊朗
//	奥地利
//	印度尼西亚 NTT网络
//	辽宁省大连市 大连纽康信息系统有限公司(雍景台荣阁28B)
//	宁夏石嘴山市 大武口区欣璐网吧(隆湖开发区六站医院斜对面)
//	新疆喀什市 移动
//	重庆市 电信
//	北京市 联通ADSL
//  四川省成都市 电子科技大学清水河校区 

    //直辖市、港澳台、自治区
    private static String city4[] = {"北京市", "上海市", "天津市", "重庆市", "新疆", "宁夏", "内蒙古", "广西", "西藏", "香港", "台湾", "澳门"};

    //在这里找出来的不是运营商，而是手机连接网络的运营商
    //手机移动服务的运营商要通过active的时候记录得到。
    //或者通过imsi卡号得到。
    private static String netChar[] = {"电信", "联通", "移动"};
    private static String allNetCode[] = {"160103", "160102", "160101"};

//	<input type="checkbox" name="deviceNetwork" value="160101">
//	移动
//</li>
//<li>
//	<input type="checkbox" name="deviceNetwork" value="160102">
//	联通
//</li>
//<li>
//	<input type="checkbox" name="deviceNetwork" value="160103">
//	电信
//</li>
//<li>
//	<input type="checkbox" name="deviceNetwork" value="160104">
//	其他网络


    public static AreaCoder findAreaCode(String ip) {
        AreaCoder areaCoder = new AreaCoder();
        if (ip == null) {
            return areaCoder;
        }

        try {
            IPLocation ipLocation = IPSeeker.getIPLocatioin(ip);
            String province = null;
            String country = ipLocation.country;
            String city = null;

            if (country == null) {
                log.error("在qqwry数据库里面，找不到 ip: {} 的地址信息", ip);
                return areaCoder;
            }

            if (ipLocation.area != null) {
                for (int i = 0; i < netChar.length; i++) {
                    if (ipLocation.area.indexOf(netChar[i]) != -1) {
                        areaCoder.setTelNetCode(allNetCode[i]);
                        break;
                    }
                }
            }

            int pos = country.indexOf("省");
            if (pos == -1) {
                // 没有找到省，说明就是这个4个直辖市和5个自治区之一了
                // 新疆， 宁夏， 内蒙古，广西， 西藏
                // 北京市， 上海市，天津市，重庆市，
                int i = 0;
                for (i = 0; i < city4.length; i++) {
                    pos = country.indexOf(city4[i]);
                    // pos == 0 ，要求一定是最开始的几个字匹配的
                    if (pos == 0) {
                        // 是4个直辖市 之一了
                        if (i < 4) {
                            // areaCode表里面没有“市”这个字
                            province = country.substring(0,
                                    city4[i].length() - 1);
                            city = country.substring(city4[i].length());
                        } else {
                            // 是这个 5个自治区之一了
                            province = city4[i];
                            city = country.substring(city4[i].length());
                        }
                        break;
                    }
                }
                if (i >= city4.length) {
                    log.info("无法解析为 省份，城市的分词处理: {}, {}", country, ip);
                }

            } else {
                // pos == 0 ，要求一定是最开始的几个字匹配的
                // 找打是23个省份之一
                // areaCode表里面没有“省”这个字
                city = country.substring(pos + 1); // skip “省”这个字
                province = country.substring(0, pos);
            }
//			log.debug("{} --> {}", country, province + ",  " + city);

            // 如果找到了省份或者直辖市
            if (province != null) {
                province = province.trim();
                //转换成utf8编码
//				province = new S

                areaCoder.setProvinceCode(allAreas.getProperty(province, AreaCoder.PROVINCECODE_UNKNOWN));

                if (areaCoder.getProvinceCode().equals(
                        AreaCoder.PROVINCECODE_UNKNOWN)) {
                    log.error("在区域表里面找不到对应的区域 『{}』 代码：", province);
                }

                if (city != null) {
                    areaCoder.setCityCode(allAreas.getProperty(city, AreaCoder.CITYCODE_UNKNOWN));
                }
//				log.debug("success find area code: {}, {}", areaCoder.getProvinceCode() + areaCoder.getCityCode(), city);
            }

        } catch (Exception e) {
            log.error("IPSeeker.getIPLocatioin failed for ip: {}", ip);
            e.printStackTrace();
        }

        return areaCoder;
    }

}
