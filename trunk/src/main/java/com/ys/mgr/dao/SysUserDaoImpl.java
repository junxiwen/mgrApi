package com.ys.mgr.dao;

import com.ys.mgr.form.request.SysUserForm;
import com.ys.mgr.po.SysUser;
import net.miidi.fsj.util.sjp.dao.BaseDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fsj on 2017/5/20.
 */
@Repository
public class SysUserDaoImpl extends BaseDaoImpl<SysUser, Integer> implements SysUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SysUser> selectForList(SysUserForm sysUserForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(false));
        MapSqlParameterSource params = buildParams(sysUserForm, sql, true, true);
        return super.selectForListByNamedParameter(sql.toString(),params);
    }

    @Override
    public long selectForCount(SysUserForm sysUserForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(true));
        MapSqlParameterSource params = buildParams(sysUserForm, sql, false, false);
        return super.selectForCountByNamedParameter(sql.toString(),params);
    }

    private MapSqlParameterSource buildParams(SysUserForm sysUserForm, StringBuffer sql, boolean needOrder, boolean needLimit) throws Exception{
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (sysUserForm != null) {
            sql.append(" where 1=1 ");
            if (needOrder) {
                sql.append(sysUserForm.getOrderSql(SysUser.class));
            }
            if (needLimit) {
                sql.append(sysUserForm.getLimitSql());
            }
        }
        return params;
    }

    /**
     * 重写selectAllForList，不查询admin（id=1）
     * @return
     * @throws Exception
     */
    @Override
    public List<SysUser> selectAllForList() throws Exception {
        return super.selectForList("select * from sys_user where id > 1 and userName <> ? ", "admin");
    }

    @Override
    public int insert(SysUser entity) throws Exception {
        int rows = super.insert(entity);
        if (rows == 0 || entity.getId() == null) {
            throw new Exception("新增用户失败");
        }
        insertUserRole(entity);
        return rows;
    }

    @Override
    public int update(SysUser entity) throws Exception {
        int rows = super.update(entity);
        jdbcTemplate.update("DELETE FROM sys_user_role where userId = ?", entity.getId());
        insertUserRole(entity);
        return rows == 0 ? 1 : rows;
    }

    private void insertUserRole(SysUser entity) {
        if (entity.getRoleIds() != null) {
            List<Object[]> params = new ArrayList<>(entity.getRoleIds().size());
            for (Integer roleId : entity.getRoleIds()) {
                params.add(new Integer[]{entity.getId(), roleId});
            }
            jdbcTemplate.batchUpdate("insert into sys_user_role(userId, roleId) VALUES (?, ?)", params);
        }
    }

    @Override
    public List<Integer> selectRoleIds(Integer userId) throws Exception {
        if (userId == null) {
            return null;
        }
        return jdbcTemplate.queryForList("SELECT roleId FROM sys_user_role WHERE userId = ?", Integer.class, userId);
    }

    @Override
    public Integer updatePwd(SysUser loginUser) throws Exception{
        if(loginUser==null){
            return null;
        }
        return jdbcTemplate.update("UPDATE sys_user SET password = ?,salt = ? WHERE id = ?",loginUser.getPassword(),loginUser.getSalt(),loginUser.getId());
    }

    @Override
    public SysUser selectByUserName(SysUser sysUser) throws Exception{
        String sql = "select * from sys_user where userName = ? and status = ?";
        List<Object> params = new ArrayList<>();
        params.add(sysUser.getUserName());
        params.add(sysUser.getStatus());

        List<SysUser> sysUsers = jdbcTemplate.query(sql,params.toArray(),new BeanPropertyRowMapper<>(SysUser.class));
        SysUser user = null;
        if(sysUsers.size() > 0){
            user = sysUsers.get(0);
        }
        return user;
    }
}
