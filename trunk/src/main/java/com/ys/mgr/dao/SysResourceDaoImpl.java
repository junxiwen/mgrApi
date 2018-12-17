package com.ys.mgr.dao;


import com.ys.mgr.form.request.SysResourceForm;
import com.ys.mgr.po.SysResource;
import com.ys.mgr.po.TreeNode;
import net.miidi.fsj.util.sjp.dao.BaseDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fsj on 2017/5/24.
 */
@Repository
public class SysResourceDaoImpl extends BaseDaoImpl<SysResource, Integer> implements SysResourceDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<SysResource> selectForList(SysResourceForm sysResourceForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(false));
        MapSqlParameterSource params = buildParams(sysResourceForm, sql, true, true);
        return super.selectForListByNamedParameter(sql.toString(),params);
    }

    @Override
    public long selectForCount(SysResourceForm sysResourceForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(true));
        MapSqlParameterSource params = buildParams(sysResourceForm, sql, false, false);
        return super.selectForCountByNamedParameter(sql.toString(),params);
    }

    private MapSqlParameterSource buildParams(SysResourceForm sysResourceForm, StringBuffer sql, boolean needOrder, boolean needLimit) throws Exception{
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (sysResourceForm != null) {
            sql.append(" where 1=1 ");
            if (needOrder) {
                sql.append(sysResourceForm.getOrderSql(SysResource.class));
            }
            if (needLimit) {
                sql.append(sysResourceForm.getLimitSql());
            }
        }
        return params;
    }

    @Override
    public List<SysResource> selectSysResourceByUserId(Integer userId) throws Exception {
        if (userId == null)
            return null;
        String sql = "SELECT res.* FROM sys_resource res JOIN sys_role_resource rr ON res.id=rr.resourceId "
                + " JOIN sys_role r ON rr.roleId = r.id JOIN sys_user_role ur ON r.id = ur.roleId WHERE ur.userId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SysResource.class), userId);
    }

    @Override
    public int deleteById(Integer id) throws Exception {
        jdbcTemplate.update("DELETE FROM sys_role_resource WHERE resourceId = ?", id);
        return super.deleteById(id);
    }

    @Override
    public int insertSysRoleResource(Integer roleId, List<Integer> selectedResourceIds) throws Exception {
        jdbcTemplate.update("DELETE FROM sys_role_resource WHERE roleId = ?", roleId);
        List<Object[]> params = new ArrayList<>(selectedResourceIds.size());
        for (Integer rosourceId : selectedResourceIds) {
            params.add(new Integer[]{rosourceId, roleId});
        }
        jdbcTemplate.batchUpdate("insert into sys_role_resource(resourceId, roleId) VALUES (?, ?)", params);
        return 1;
    }

    @Override
    public Map<String, Object> getRoleIdByuserId(Integer userId) throws Exception {
        String sql = "SELECT " +
                " sur.roleId, " +
                " su.id " +
                " FROM sys_user_role sur LEFT JOIN sys_user su ON sur.userId = su.id " +
                " WHERE sur.userId = ?";
        return jdbcTemplate.queryForMap(sql, userId);
    }

    @Override
    public List<TreeNode> selectAllTreeNode() throws Exception {
        return jdbcTemplate.query("select r.id, r.simpleName title,r.url,r.icon, r.parentId from sys_resource r order by r.orderNum asc",
                new BeanPropertyRowMapper<>(TreeNode.class));
    }

    @Override
    public List<TreeNode> selectTreeNodeByRoleId(Integer roleId) throws Exception {
        return jdbcTemplate.query("select r.id, r.simpleName title,r.url,r.icon,r.parentId from sys_resource r JOIN sys_role_resource rr "
                        + " ON rr.resourceId = r.id WHERE rr.roleId = ?",
                new BeanPropertyRowMapper<>(TreeNode.class), roleId);
    }


    @Override
    public void batchUpdateRole(Integer userId, List<Integer> rolesId) throws Exception {
        jdbcTemplate.update("DELETE FROM sys_user_role WHERE userId = ?", userId);
        List<Object[]> params = new ArrayList<>(rolesId.size());
        for (Integer roleId : rolesId) {
            params.add(new Integer[]{userId, roleId});
        }
        jdbcTemplate.batchUpdate("insert into sys_user_role(userId, roleId) VALUES (?, ?)", params);
    }
}