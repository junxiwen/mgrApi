package com.ys.mgr.dao;

import com.ys.mgr.form.request.NewsForm;
import com.ys.mgr.po.News;
import net.miidi.fsj.util.sjp.dao.BaseDao;

import java.util.List;

/**
 * Created by Administrator on 2018/12/14.
 */
public interface NewsDao extends BaseDao<News,Integer> {
    List<News> selectForList(NewsForm queryForm) throws Exception;
    long selectForCount(NewsForm queryForm) throws Exception;
}
