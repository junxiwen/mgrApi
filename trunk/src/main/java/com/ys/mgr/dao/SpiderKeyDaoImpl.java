package com.ys.mgr.dao;

import com.ys.mgr.form.request.SpiderKeyForm;
import com.ys.mgr.po.SpiderKey;
import net.miidi.fsj.util.sjp.dao.BaseDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/12/25.
 */
@Repository
public class SpiderKeyDaoImpl extends BaseDaoImpl<SpiderKey,Integer> implements SpiderKeyDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SpiderKey> selectForList(SpiderKeyForm queryForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(false));
        MapSqlParameterSource params = new MapSqlParameterSource();
        return super.selectForListByNamedParameter(sql.toString(),params);
    }

    @Override
    public long selectForCount(SpiderKeyForm queryForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(true));
        MapSqlParameterSource params = new MapSqlParameterSource();
        return super.selectForCountByNamedParameter(sql.toString(),params);
    }

    @Override
    public List<String> selectAllKey() {
        return jdbcTemplate.queryForList("SELECT spiderKey FROM spider_key", String.class);
    }
}
