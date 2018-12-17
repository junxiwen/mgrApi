package com.ys.mgr.dao;

import com.ys.mgr.form.request.UserInfoForm;
import com.ys.mgr.po.UserInfo;
import net.miidi.fsj.util.sjp.dao.BaseDao;

import java.util.List;

/**
 * Author:ViC_Lee
 * Date:2018/4/16
 */
public interface UserInfoDao extends BaseDao<UserInfo, Integer> {
    //列表
    public List<UserInfo> listData(UserInfoForm userInfoForm) throws Exception;

    //列表总数、BaseDao查询总条数返回一个long类型
    public long listTotal(UserInfoForm userInfoForm) throws Exception;

    //修改用户昵称
    public void modifyUserNickName(String id, String nickName) throws Exception;
}
