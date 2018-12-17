package com.ys.mgr.service;


import com.ys.mgr.form.request.SysUserForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SysUser;
import net.miidi.fsj.util.sjp.service.BaseService;

/**
 * Created by fsj on 2017/5/20.
 */
public interface SysUserService extends BaseService<SysUser, Integer> {


    //查询系统用户列表
    PageData<SysUser> selectPageData(SysUserForm sysUserForm) throws Exception;

    /**
     * 根据用户名查询用户信息
     * @param sysUser 包含用户名信息的对象
     * @return 用户详细信息
     * @throws Exception
     */
    SysUser selectByUserName(SysUser sysUser) throws Exception;

    Integer updatePwd(SysUser loginUser) throws Exception;

    void updateSysUser(SysUser sysUser) throws Exception;
}
