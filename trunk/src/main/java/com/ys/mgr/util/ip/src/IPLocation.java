package com.ys.mgr.util.ip.src;

/**
 * <pre>
 * 用来封装ip相关信息，目前只有两个字段，ip所在的国家和地区
 * </pre>
 */
class IPLocation {
    public String country;
    public String area;

    public IPLocation() {
        country = area = "";
    }

    //  country        area
//	上海市徐汇区           电信
//	上海市                          移动
//	湖北省武汉市           电信
//	河北省保定市           星宇网吧
//	局域网                          对方和您在同一内部网	
    public IPLocation getCopy() {
        IPLocation ret = new IPLocation();
        //湖北省武汉市
        ret.country = country;
        //电信，移动
        ret.area = area;
        return ret;
    }
}
