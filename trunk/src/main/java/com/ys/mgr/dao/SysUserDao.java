package com.ys.mgr.dao;


import com.ys.mgr.form.request.SysUserForm;
import com.ys.mgr.po.SysUser;
import net.miidi.fsj.util.sjp.dao.BaseDao;

import java.util.List;

/**
 * Created by fsj on 2017/5/20.
 */
public interface SysUserDao extends BaseDao<SysUser, Integer> {
    List<Integer> selectRoleIds(Integer userId) throws Exception;

    Integer updatePwd(SysUser loginUser) throws Exception;

    SysUser selectByUserName(SysUser sysUser) throws Exception;

    List<SysUser> selectForList(SysUserForm sysUserForm) throws Exception;
    long selectForCount(SysUserForm sysUserForm) throws Exception;
}
