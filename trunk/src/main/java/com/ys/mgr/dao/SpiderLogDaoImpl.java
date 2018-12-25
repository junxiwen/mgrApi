package com.ys.mgr.dao;

import com.ys.mgr.form.request.SpiderLogForm;
import com.ys.mgr.po.SpiderLog;
import net.miidi.fsj.util.sjp.dao.BaseDaoImpl;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/12/25.
 */
@Repository
public class SpiderLogDaoImpl extends BaseDaoImpl<SpiderLog,Integer> implements SpiderLogDao {
    @Override
    public List<SpiderLog> selectForList(SpiderLogForm queryForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(false));
        MapSqlParameterSource params = buildParams(queryForm, sql, true, true);
        return super.selectForListByNamedParameter(sql.toString(),params);
    }

    @Override
    public long selectForCount(SpiderLogForm queryForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(true));
        MapSqlParameterSource params = buildParams(queryForm, sql, false, false);
        return super.selectForCountByNamedParameter(sql.toString(),params);
    }
    private MapSqlParameterSource buildParams(SpiderLogForm queryForm, StringBuffer sql, boolean needOrder, boolean needLimit) throws Exception{
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (queryForm != null) {
            sql.append(" where 1=1 ");
            if (needOrder) {
                sql.append(queryForm.getOrderSql(SpiderLog.class));
            }
            if (needLimit) {
                sql.append(queryForm.getLimitSql());
            }
        }
        return params;
    }
}
