package com.ys.mgr.dao;

import com.ys.mgr.form.request.UserInfoForm;
import com.ys.mgr.po.UserInfo;
import com.ys.mgr.util.MyStringUtils;
import net.miidi.fsj.util.sjp.dao.BaseDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author:ViC_Lee
 * Date:2018/4/16
 */
@Repository
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo, Integer> implements UserInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<UserInfo> listData(UserInfoForm userInfoForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(false));
        MapSqlParameterSource sqlParameterSource = parameterSource(userInfoForm, sql, true, true);
        return super.selectForListByNamedParameter(sql.toString(), sqlParameterSource);
    }

    @Override
    public long listTotal(UserInfoForm userInfoForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(true));
        MapSqlParameterSource sqlParameterSource = parameterSource(userInfoForm, sql, false, false);
        return super.selectForCountByNamedParameter(sql.toString(), sqlParameterSource);
    }

    @Override
    public void modifyUserNickName(String id, String nickName) throws Exception {
        jdbcTemplate.update("UPDATE user_info SET nickName = ? WHERE id = ?", new Object[]{nickName, id});
    }

    public MapSqlParameterSource parameterSource(UserInfoForm userInfoForm, StringBuffer sql, boolean needOrder, boolean needLimit) throws Exception {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        sql.append(" WHERE 1=1");
        if (MyStringUtils.isNoneEmpty(userInfoForm.getId())) {
            sql.append(" AND id = :id");
            mapSqlParameterSource.addValue("id", userInfoForm.getId());
        }
        if (MyStringUtils.isNoneEmpty(userInfoForm.getPhoneNum())) {
            sql.append(" AND phoneNum = :phoneNum");
            mapSqlParameterSource.addValue("phoneNum", userInfoForm.getPhoneNum());
        }
        if (userInfoForm.getPlatform() != null) {
            sql.append(" AND platform = :platform");
            mapSqlParameterSource.addValue("platform", userInfoForm.getPlatform());
        }
        if (userInfoForm.getType() != null) {
            sql.append(" AND type = :type");
            mapSqlParameterSource.addValue("type", userInfoForm.getType());
        }
        if (userInfoForm.getCanIssuance() != null) {
            sql.append(" AND canIssuance = :canIssuance");
            mapSqlParameterSource.addValue("canIssuance", userInfoForm.getCanIssuance());
        }
        if (userInfoForm.getUserProperty() != null) {
            sql.append(" AND userProperty = :userProperty");
            mapSqlParameterSource.addValue("userProperty", userInfoForm.getUserProperty());
        }
        if (userInfoForm.getReqDateStart() != null) {
            sql.append(" AND createTime >= :reqDateStart");
            mapSqlParameterSource.addValue("reqDateStart", userInfoForm.getReqDateStart());
        }
        if (userInfoForm.getReqDateEnd() != null) {
            sql.append(" AND createTime < :reqDateEnd");
            mapSqlParameterSource.addValue("reqDateEnd", userInfoForm.getReqDateEnd());
        }
        if (needOrder) {
            sql.append(userInfoForm.getOrderSql(UserInfo.class));
        }
        if (needLimit) {
            sql.append(userInfoForm.getLimitSql());
        }
        return mapSqlParameterSource;
    }
}
