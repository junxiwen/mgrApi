package com.ys.mgr.dao;

import com.ys.mgr.form.request.SysRoleForm;
import com.ys.mgr.po.SysRole;
import net.miidi.fsj.util.sjp.dao.BaseDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fsj on 2017/5/24.
 */
@Repository
public class SysRoleDaoImpl extends BaseDaoImpl<SysRole, Integer> implements SysRoleDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<SysRole> selectForList(SysRoleForm sysRoleForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(false));
        MapSqlParameterSource params = buildParams(sysRoleForm, sql, true, true);
        return super.selectForListByNamedParameter(sql.toString(),params);
    }

    @Override
    public long selectForCount(SysRoleForm sysRoleForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(true));
        MapSqlParameterSource params = buildParams(sysRoleForm, sql, false, false);
        return super.selectForCountByNamedParameter(sql.toString(),params);
    }

    private MapSqlParameterSource buildParams(SysRoleForm sysRoleForm, StringBuffer sql, boolean needOrder, boolean needLimit) throws Exception{
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (sysRoleForm != null) {
            sql.append(" where 1=1 ");
            if (needOrder) {
                sql.append(sysRoleForm.getOrderSql(SysRole.class));
            }
            if (needLimit) {
                sql.append(sysRoleForm.getLimitSql());
            }
        }
        return params;
    }

    @Override
    public int deleteById(Integer id) throws Exception {
        jdbcTemplate.update("DELETE FROM sys_role_resource WHERE roleId = ?", id);
        jdbcTemplate.update("DELETE FROM sys_user_role WHERE roleId = ?", id);
        return super.deleteById(id);
    }

    @Override
    public Integer getRoleIdByUserId(Integer mgrUserId) {
        String sql = "SELECT roleId FROM `sys_user_role` WHERE userId=?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, mgrUserId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Integer> selectedResourcesIds(Integer id) throws Exception {
        return jdbcTemplate.queryForList("SELECT resourceId FROM sys_role_resource WHERE roleId = ?", Integer.class, id);
    }
}
