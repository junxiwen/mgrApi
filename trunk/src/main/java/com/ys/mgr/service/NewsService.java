package com.ys.mgr.service;

import com.ys.mgr.form.request.NewsForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.News;
import net.miidi.fsj.util.sjp.service.BaseService;

/**
 * Created by Administrator on 2018/12/14.
 */
public interface NewsService extends BaseService<News,Integer> {
    //查询权限列表
    PageData<News> selectPageData(NewsForm queryForm) throws Exception;
}
