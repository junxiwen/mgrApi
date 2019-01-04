package com.ys.mgr.service;

import com.ys.mgr.form.request.NewsForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.News;
import net.miidi.fsj.util.sjp.service.BaseService;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/12/14.
 */
public interface NewsService extends BaseService<News,Integer> {
    //查询新闻列表
    PageData<News> selectPageData(NewsForm queryForm) throws Exception;
}
