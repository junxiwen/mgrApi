package com.ys.mgr.service;

import com.ys.mgr.dao.UserInfoDao;
import com.ys.mgr.form.request.UserInfoForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.UserInfo;
import net.miidi.fsj.util.sjp.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author:ViC_Lee
 * Date:2018/4/16
 */
@Service
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Integer> implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public PageData<UserInfo> pageData(UserInfoForm userInfoForm) throws Exception {
        PageData<UserInfo> pageData = new PageData<>(userInfoForm);
        pageData.setTotal(userInfoDao.listTotal(userInfoForm));
        if (pageData.getTotal() > 0) {
            pageData.setData(userInfoDao.listData(userInfoForm));
        }
        return pageData;
    }

    @Override
    public void modifyUserNickName(String id, String nickName) throws Exception {
        userInfoDao.modifyUserNickName(id, nickName);
    }
}
