package com.ys.mgr.service;

import com.ys.mgr.dao.NewsDao;
import com.ys.mgr.form.request.NewsForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.News;
import net.miidi.fsj.util.sjp.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/12/14.
 */
@Service
public class NewsServiceImpl extends BaseServiceImpl<News,Integer> implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public PageData<News> selectPageData(NewsForm queryForm) throws Exception {
        PageData<News> pageData = new PageData<>(queryForm);
        //查询总数
        pageData.setTotal(newsDao.selectForCount(queryForm));
        if (pageData.getTotal() > 0) {
            //查询列表详情
            pageData.setData(newsDao.selectForList(queryForm));
        }
        return pageData;
    }
}
