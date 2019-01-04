package com.ys.mgr.dao;

import com.ys.mgr.form.request.ArticleForm;
import com.ys.mgr.po.Article;
import net.miidi.fsj.util.sjp.dao.BaseDaoImpl;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */
@Repository
public class ArticleDaoImpl extends BaseDaoImpl<Article,Integer> implements ArticleDao {

    @Override
    public List<Article> selectForList(ArticleForm queryForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(false));
        MapSqlParameterSource params = buildParams(queryForm, sql, true, true);
        return super.selectForListByNamedParameter(sql.toString(),params);
    }

    @Override
    public long selectForCount(ArticleForm queryForm) throws Exception {
        StringBuffer sql = new StringBuffer(super.buildSelectSql(true));
        MapSqlParameterSource params = buildParams(queryForm, sql, false, false);
        return super.selectForCountByNamedParameter(sql.toString(),params);
    }

    private MapSqlParameterSource buildParams(ArticleForm queryForm, StringBuffer sql, boolean needOrder, boolean needLimit) throws Exception{
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (queryForm != null) {
            sql.append(" where 1=1 ");
            if (needOrder) {
                sql.append(queryForm.getOrderSql(Article.class));
            }
            if (needLimit) {
                sql.append(queryForm.getLimitSql());
            }
        }
        return params;
    }
}
