package com.ys.mgr.po;

import com.ys.mgr.util.MyStringUtils;
import com.ys.mgr.util.http.MyHttpUtils;
import lombok.Data;
import lombok.ToString;
import net.miidi.fsj.util.sjp.annotation.Id;
import net.miidi.fsj.util.sjp.annotation.Table;

import java.util.Date;

/**
 * Author:ViC_Lee
 * Date:2018/4/16
 * 用户
 */
@Data
@Table("user_info")
@ToString
public class UserInfo {
    @Id
    private String id;
    private String userName;//'登录账户',
    private String password;//'用户密码',
    private String nickName;//'用户昵称',
    private Date createTime;//'注册时间',
    private String headImage;//用户头像
    private Integer type;//'1、真实用户    2、未注册用户',
    private String devId;//'注册设备信息',
    private Integer platform;//'1、ios   2、Android',
    private String phoneNum;//'手机号码',
    private Integer sex;//'1、男  2、女',
    private Date birthday;// '生日',
    private String address;//'常住地址',
    private String synopsis;//'简介',
    private String industry;//'行业',
    private Integer canIssuance;//'0、不能发布 1、可以发布',
    private Integer userProperty;//'1、个人 2、企业',
    private Integer starLevel;//'星级:1、半星 2、一星 以此类推'

    //@IgnoredField


    public String getHeadImage2() {
        if (MyStringUtils.isNoneEmpty(headImage)) {
            return MyHttpUtils.getRealResUrl(headImage);
        }
        return null;
    }
}
