package com.ys.mgr.service;


import com.ys.mgr.dao.SysResourceDao;
import com.ys.mgr.form.request.SysResourceForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SysResource;
import com.ys.mgr.po.TreeNode;
import net.miidi.fsj.util.sjp.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fsj on 2017/5/20.
 */
@Service
@Transactional
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource, Integer> implements SysResourceService {
    @Autowired
    private SysResourceDao sysResourceDao;


    @Override
    public PageData<SysResource> selectPageData(SysResourceForm sysResourceForm) throws Exception {
        PageData<SysResource> pageData = new PageData<>(sysResourceForm);
        //查询总数
        pageData.setTotal(sysResourceDao.selectForCount(sysResourceForm));
        if (pageData.getTotal() > 0) {
            //查询列表详情
            pageData.setData(sysResourceDao.selectForList(sysResourceForm));
        }
        return pageData;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SysResource> selectUserResources(Integer userId) throws Exception {
        return sysResourceDao.selectSysResourceByUserId(userId);
    }

    @Override
    public int deleteById(Integer id) throws Exception {
        return sysResourceDao.deleteById(id);
    }

    @Override
    public int insertSysRoleResource(Integer roleId, List<Integer> selectedResourceIds) throws Exception {
        return sysResourceDao.insertSysRoleResource(roleId, selectedResourceIds);
    }

    @Override
    public Map<String, Object> selectRoleIdByuserId(Integer userId) throws Exception {
        return sysResourceDao.getRoleIdByuserId(userId);
    }

    @Override
    public List<TreeNode> selectListByRoleId(Integer roleId) throws Exception {
        List<TreeNode> result = new ArrayList<>();
        List<TreeNode> all = sysResourceDao.selectAllTreeNode();
        List<TreeNode> selected = sysResourceDao.selectTreeNodeByRoleId(roleId);
        // 转换、选中
        for (TreeNode treeNode1 : all) {
            boolean hasParent = false;
            treeNode1.setExpand(true);
            for (TreeNode selectedNode : selected) {
                if (treeNode1.getId().equals(selectedNode.getId())) {
                    treeNode1.setChecked(true);
                }
            }
            for (TreeNode treeNode2 : all) {
                if (treeNode1.getParentId() != null && treeNode1.getParentId().equals(treeNode2.getId())) {
                    hasParent = true;
                    if (treeNode2.getChildren() == null) {
                        treeNode2.setChildren(new ArrayList<>());
                    }
                    treeNode2.getChildren().add(treeNode1);
                    if (treeNode1.getChecked()) {
                        treeNode2.setSelected(true);
                    }
                    break;
                }
            }
            if (!hasParent) {
                result.add(treeNode1);
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getRoleIdByuserId(Integer userId) throws Exception {
        return sysResourceDao.getRoleIdByuserId(userId);
    }
}
