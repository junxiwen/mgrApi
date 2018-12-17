package com.ys.mgr.dao;


import com.ys.mgr.form.request.SysResourceForm;
import com.ys.mgr.po.SysResource;
import com.ys.mgr.po.TreeNode;
import net.miidi.fsj.util.sjp.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * Created by fsj on 2017/5/24.
 */
public interface SysResourceDao extends BaseDao<SysResource, Integer> {
    List<SysResource> selectSysResourceByUserId(Integer userId) throws Exception;

    int insertSysRoleResource(Integer roleId, List<Integer> selectedResourceIds) throws Exception;

    Map<String,Object> getRoleIdByuserId(Integer userId) throws Exception;

    List<TreeNode> selectAllTreeNode() throws Exception;

    List<TreeNode> selectTreeNodeByRoleId(Integer roleId) throws Exception;

    void batchUpdateRole(Integer userId, List<Integer> rolesId) throws Exception;


    List<SysResource> selectForList(SysResourceForm sysResourceForm) throws Exception;
    long selectForCount(SysResourceForm sysResourceForm) throws Exception;
}
