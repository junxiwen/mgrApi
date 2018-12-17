package com.ys.mgr.service;


import com.ys.mgr.form.request.UserInfoForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.UserInfo;
import net.miidi.fsj.util.sjp.service.BaseService;

/**
 * Author:ViC_Lee
 * Date:2018/4/16
 */
public interface UserInfoService extends BaseService<UserInfo, Integer> {
    PageData<UserInfo> pageData(UserInfoForm userInfoForm) throws Exception;

    public void modifyUserNickName(String id, String nickName) throws Exception;
}
