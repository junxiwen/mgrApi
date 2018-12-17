package com.ys.mgr.service;

import com.ys.mgr.dao.SysResourceDao;
import com.ys.mgr.dao.SysUserDao;
import com.ys.mgr.form.request.SysUserForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SysUser;
import net.miidi.fsj.util.sjp.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by fsj on 2017/5/20.
 */
@Service
@Transactional
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Integer> implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysResourceDao sysResourceDao;


    @Override
    public PageData<SysUser> selectPageData(SysUserForm sysUserForm) throws Exception {
        PageData<SysUser> pageData = new PageData<>(sysUserForm);
        //查询总数
        pageData.setTotal(sysUserDao.selectForCount(sysUserForm));
        if (pageData.getTotal() > 0) {
            //查询列表详情
            pageData.setData(sysUserDao.selectForList(sysUserForm));
        }
        return pageData;
    }

    @Transactional(readOnly = true)
    @Override
    public SysUser selectByUserName(SysUser sysUser) throws Exception {
        sysUser.setStatus(0);
        return sysUserDao.selectByUserName(sysUser);
    }

    @Override
    public Integer updatePwd(SysUser loginUser) throws Exception{
        return sysUserDao.updatePwd(loginUser);
    }

    @Override
    public int insert(SysUser entity) throws Exception {
        entity.setId(null);
        entity.setInsertTime(new Date());
        if(entity.getStatus() == null) {
            entity.setStatus(0);
        }
        int rows = sysUserDao.insert(entity);
//        int i = 1/0; // 测试事务
        return rows;
    }

    // 默认的update是调用的superDao，要调用自己的dao做特殊处理需要重写
    @Override
    public int update(SysUser entity) throws Exception {
        int rows = sysUserDao.update(entity);
//        int i = 1/0; // 测试事务
        return rows;
    }

    @Transactional(readOnly = true)
    @Override
    public SysUser selectById(SysUser entity) throws Exception {
        return selectById(entity.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public SysUser selectById(Integer id) throws Exception {
        SysUser sysUser = super.selectById(id);
        sysUser.setRoleIds(sysUserDao.selectRoleIds(id));
        return sysUser;
    }

    /**
     * 重写selectAllForList，不查询admin
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    @Override
    public List<SysUser> selectAllForList() throws Exception {
        return sysUserDao.selectAllForList();
    }

    @Override
    @Transactional
    public void updateSysUser(SysUser sysUser) throws Exception {
        //1、修改用户信息
        sysUserDao.update(sysUser);
        //2、修改角色
        sysResourceDao.batchUpdateRole(sysUser.getId(),sysUser.getRoleIds());
    }
}
