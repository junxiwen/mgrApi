package com.ys.mgr.dao;

import com.ys.mgr.form.request.ArticleForm;
import com.ys.mgr.po.Article;
import net.miidi.fsj.util.sjp.dao.BaseDao;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */
public interface ArticleDao extends BaseDao<Article,Integer> {
    List<Article> selectForList(ArticleForm queryForm) throws Exception;
    long selectForCount(ArticleForm queryForm) throws Exception;
}
