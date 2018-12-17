package com.ys.mgr.dao;

import com.ys.mgr.form.request.NewsForm;
import com.ys.mgr.form.request.SysResourceForm;
import com.ys.mgr.po.News;
import com.ys.mgr.po.SysResource;
import com.ys.mgr.util.MyStringUtils;
import net.miidi.fsj.util.sjp.dao.BaseDaoImpl;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/12/14.
 */
@Repository
public class NewsDaoImpl extends BaseDaoImpl<News,Integer> implements NewsDao {
    @Override
    public List<News> selectForList(NewsForm queryForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(false));
        MapSqlParameterSource params = buildParams(queryForm, sql, true, true);
        return super.selectForListByNamedParameter(sql.toString(),params);
    }

    @Override
    public long selectForCount(NewsForm queryForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(true));
        MapSqlParameterSource params = buildParams(queryForm, sql, false, false);
        return super.selectForCountByNamedParameter(sql.toString(),params);
    }

    private MapSqlParameterSource buildParams(NewsForm queryForm, StringBuffer sql, boolean needOrder, boolean needLimit) throws Exception{
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (queryForm != null) {
            sql.append(" where 1=1 ");
            if(MyStringUtils.isNotBlank(queryForm.getContent())){
                sql.append(" AND content like :content");
                params.addValue("content", "%"+queryForm.getContent()+"%");
            }
            if (needOrder) {
                sql.append(queryForm.getOrderSql(News.class));
            }
            if (needLimit) {
                sql.append(queryForm.getLimitSql());
            }
        }
        return params;
    }
}
