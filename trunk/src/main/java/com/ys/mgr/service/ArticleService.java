package com.ys.mgr.service;

import com.ys.mgr.form.request.ArticleForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.Article;
import net.miidi.fsj.util.sjp.service.BaseService;

/**
 * Created by Administrator on 2019/1/4.
 */
public interface ArticleService extends BaseService<Article,Integer> {
    //查询列表
    PageData<Article> selectPageData(ArticleForm queryForm) throws Exception;

    void delById(Integer id) throws Exception;
}
