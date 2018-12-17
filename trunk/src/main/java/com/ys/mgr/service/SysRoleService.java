package com.ys.mgr.service;


import com.ys.mgr.form.request.SysRoleForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SysRole;
import net.miidi.fsj.util.sjp.service.BaseService;

import java.util.List;

/**
 * Created by fsj on 2017/5/20.
 */
public interface SysRoleService extends BaseService<SysRole, Integer> {

    //查询角色列表
    PageData<SysRole> selectPageData(SysRoleForm sysRoleForm) throws Exception;

    int deleteById(Integer id) throws Exception;

    Integer getRoleIdByUserId(Integer mgrUserId);

    List<Integer> selectedResourcesIds(Integer id) throws Exception;

    int insertSysRoleResource(Integer roleId, List<Integer> selectedResourceIds) throws Exception;

    void updateRole(Integer userId, List<Integer> roleIds) throws Exception;
}
