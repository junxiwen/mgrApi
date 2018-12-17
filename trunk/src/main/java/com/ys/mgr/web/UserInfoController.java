package com.ys.mgr.web;

import com.ys.mgr.form.request.UserInfoForm;
import com.ys.mgr.form.response.MyResponseForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.UserInfo;
import com.ys.mgr.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:ViC_Lee
 * Date:2018/4/16
 */
@RestController
@RequestMapping("/userInfo")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户列表
     *
     * @param userInfoForm
     * @return
     * @throws Exception 条件查询用post请求才能接受到页面传进来的form参数
     */
    @RequestMapping(method = RequestMethod.POST)
    public MyResponseForm<PageData<UserInfo>> list(UserInfoForm userInfoForm) throws Exception {
        return new MyResponseForm<>(userInfoService.pageData(userInfoForm)).returnSuccess();
    }

    /**
     * 修改用户昵称
     *
     * @param id
     * @param nickName
     * @throws Exception
     */
    @RequestMapping(value = "/modifyUserNickName", method = RequestMethod.POST)
    public MyResponseForm<String> modifyUserNickName(String id, String nickName) throws Exception {
        userInfoService.modifyUserNickName(id, nickName);
        return new MyResponseForm<>("").returnSuccess();
    }
}
