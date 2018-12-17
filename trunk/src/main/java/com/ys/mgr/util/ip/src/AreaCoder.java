package com.ys.mgr.util.ip.src;

public class AreaCoder {
    // 未知的地区代码
    // 在搜索广告的时候肯定不会搜索成功。
    // 省份代码
    public static final String PROVINCECODE_UNKNOWN = "999999";
    // 市级代码，或者直辖市的区代码
    public static final String CITYCODE_UNKNOWN = "999999";

    // 电信运营商代码
    // 160101 移动
    // 160102 联通
    // 160103 电信
    // 160104 其他网络

    public static final String TELNETCODE_UNKNOWN = "160104";

    private String provinceCode;
    private String cityCode;
    private String telNetCode;

    public AreaCoder() {
        provinceCode = PROVINCECODE_UNKNOWN;
        cityCode = CITYCODE_UNKNOWN;
        telNetCode = TELNETCODE_UNKNOWN;
    }

    public String getTelNetCode() {
        return telNetCode;
    }

    public void setTelNetCode(String telNetCode) {
        this.telNetCode = telNetCode.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode.trim();
    }

}
