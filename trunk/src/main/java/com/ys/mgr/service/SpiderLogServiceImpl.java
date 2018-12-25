package com.ys.mgr.service;

import com.ys.mgr.dao.SpiderLogDao;
import com.ys.mgr.form.request.SpiderLogForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SpiderLog;
import net.miidi.fsj.util.sjp.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/12/25.
 */
@Service
public class SpiderLogServiceImpl extends BaseServiceImpl<SpiderLog,Integer> implements SpiderLogService {

    @Autowired
    private SpiderLogDao spiderLogDao;

    @Override
    public PageData<SpiderLog> selectPageData(SpiderLogForm queryForm) throws Exception {
        PageData<SpiderLog> pageData = new PageData<>(queryForm);
        //查询总数
        pageData.setTotal(spiderLogDao.selectForCount(queryForm));
        if (pageData.getTotal() > 0) {
            //查询列表详情
            pageData.setData(spiderLogDao.selectForList(queryForm));
        }
        return pageData;
    }
}
