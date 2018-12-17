package com.ys.mgr.dao;

import com.ys.mgr.form.request.SysRoleForm;
import com.ys.mgr.po.SysRole;
import net.miidi.fsj.util.sjp.dao.BaseDao;

import java.util.List;

/**
 * Created by fsj on 2017/5/24.
 */
public interface SysRoleDao extends BaseDao<SysRole, Integer> {
    Integer getRoleIdByUserId(Integer mgrUserId);
    List<Integer> selectedResourcesIds(Integer id) throws Exception;


    List<SysRole> selectForList(SysRoleForm sysRoleForm) throws Exception;
    long selectForCount(SysRoleForm sysRoleForm) throws Exception;
}
