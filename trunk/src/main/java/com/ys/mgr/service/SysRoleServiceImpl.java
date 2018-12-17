package com.ys.mgr.service;


import com.ys.mgr.dao.SysResourceDao;
import com.ys.mgr.dao.SysRoleDao;
import com.ys.mgr.form.request.SysRoleForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SysRole;
import net.miidi.fsj.util.sjp.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fsj on 2017/5/20.
 */
@Service
@Transactional
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, Integer> implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysResourceDao sysResourceDao;

    @Override
    public PageData<SysRole> selectPageData(SysRoleForm sysRoleForm) throws Exception {
        PageData<SysRole> pageData = new PageData<>(sysRoleForm);
        //查询总数
        pageData.setTotal(sysRoleDao.selectForCount(sysRoleForm));
        if (pageData.getTotal() > 0) {
            //查询列表详情
            pageData.setData(sysRoleDao.selectForList(sysRoleForm));
        }
        return pageData;
    }

    @Override
    public int deleteById(Integer id) throws Exception {
        return sysRoleDao.deleteById(id);
    }

    @Override
    public Integer getRoleIdByUserId(Integer mgrUserId) {
        return sysRoleDao.getRoleIdByUserId(mgrUserId);
    }

    @Override
    public List<Integer> selectedResourcesIds(Integer id) throws Exception {
        return sysRoleDao.selectedResourcesIds(id);
    }

    @Override
    public int insertSysRoleResource(Integer roleId, List<Integer> selectedResourceIds) throws Exception {
        return sysResourceDao.insertSysRoleResource(roleId, selectedResourceIds);
    }

    @Override
    public void updateRole(Integer userId, List<Integer> roleIds) throws Exception {
        sysResourceDao.batchUpdateRole(userId,roleIds);
    }
}
