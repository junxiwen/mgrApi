package com.ys.mgr.form.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/8.
 */
@Data
public class SysUserForm extends MyRequestForm{
    private Integer id;
    private String userName;
    private String password;
    private String mobilephone;
    private String realname;
    public static final int ON = 0;
    public static final int OFF = 1;
    private Integer status;
    private Date insertTime;
    private String descr;
    private String salt;

    private List<Integer> roleIds = new ArrayList<>(0);
    private String accessToken;
}
