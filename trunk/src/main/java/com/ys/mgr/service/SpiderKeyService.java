package com.ys.mgr.service;

import com.ys.mgr.form.request.NewsForm;
import com.ys.mgr.form.request.SpiderKeyForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.News;
import com.ys.mgr.po.SpiderKey;
import net.miidi.fsj.util.sjp.service.BaseService;

import java.util.List;

/**
 * Created by Administrator on 2018/12/25.
 */
public interface SpiderKeyService extends BaseService<SpiderKey,Integer>{

    //查询关键字列表
    PageData<SpiderKey> selectPageData(SpiderKeyForm queryForm) throws Exception;

    List<String> selectAllKey();

    void delById(Integer id) throws Exception;
}
