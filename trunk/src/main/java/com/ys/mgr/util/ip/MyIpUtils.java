package com.ys.mgr.util.ip;

import com.ys.mgr.util.ip.src.AreaCodeFinder;
import com.ys.mgr.util.ip.src.IPSeeker;

/**
 * ip工具对外公开的接口
 *
 *
 * 2016年2月22日
 */
public class MyIpUtils {

    /**
     * 根据ip得到对应的地址信息
     *
     * @param ip
     *
     * @return
     */
    public static String getAddress(String ip) {
        try {
            return IPSeeker.getAddress(ip);
        } catch (Exception e) {
            e.printStackTrace();
            return "无法解析ip: " + ip;
        }
    }

    /**
     * 根据ip得到对应的城市代码
     *
     * @param ip
     *
     * @return
     */
    public static Integer getCityCode(String ip) {
        return Integer.parseInt(AreaCodeFinder.findAreaCode(ip).getCityCode());
    }

    /**
     * 根据ip得到对应的省份代码
     *
     * @param ip
     *
     * @return
     */
    public static Integer getProvinceCode(String ip) {
        return Integer.parseInt(AreaCodeFinder.findAreaCode(ip).getProvinceCode());
    }

    /**
     * 根据ip得到对应的运营商代码
     *
     * @param ip
     *
     * @return
     */
    public static String getTelNetCode(String ip) {
        return AreaCodeFinder.findAreaCode(ip).getTelNetCode();
    }
}
