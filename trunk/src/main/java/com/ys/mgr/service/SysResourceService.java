package com.ys.mgr.service;


import com.ys.mgr.form.request.SysResourceForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SysResource;
import com.ys.mgr.po.TreeNode;
import net.miidi.fsj.util.sjp.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by fsj on 2017/5/20.
 */
public interface SysResourceService extends BaseService<SysResource, Integer> {


    //查询权限列表
    PageData<SysResource> selectPageData(SysResourceForm sysResourceForm) throws Exception;

    List<SysResource> selectUserResources(Integer userId) throws Exception;

    int deleteById(Integer id) throws Exception;

    int insertSysRoleResource(Integer roleId, List<Integer> selectedResourceIds) throws Exception;

    Map<String,Object> selectRoleIdByuserId(Integer userId) throws Exception;

    List<TreeNode> selectListByRoleId(Integer roleId) throws Exception;

    Map<String,Object> getRoleIdByuserId(Integer userId) throws Exception;
}
