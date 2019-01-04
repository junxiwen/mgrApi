package com.ys.mgr.service;

import com.ys.mgr.dao.ArticleDao;
import com.ys.mgr.form.request.ArticleForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.Article;
import net.miidi.fsj.util.sjp.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/1/4.
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article,Integer> implements ArticleService {

    @Autowired
    private ArticleDao ArticleDao;

    @Override
    public PageData<Article> selectPageData(ArticleForm queryForm) throws Exception {
        PageData<Article> pageData = new PageData<>(queryForm);
        //查询总数
        pageData.setTotal(ArticleDao.selectForCount(queryForm));
        if (pageData.getTotal() > 0) {
            //查询列表详情
            pageData.setData(ArticleDao.selectForList(queryForm));
        }
        return pageData;
    }

    @Override
    public void delById(Integer id) throws Exception {
        ArticleDao.deleteById(id);
    }
}
