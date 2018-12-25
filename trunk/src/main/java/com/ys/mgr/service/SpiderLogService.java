package com.ys.mgr.service;

import com.ys.mgr.form.request.SpiderLogForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SpiderLog;
import net.miidi.fsj.util.sjp.service.BaseService;

/**
 * Created by Administrator on 2018/12/25.
 */
public interface SpiderLogService extends BaseService<SpiderLog,Integer>{
    //查询日志列表
    PageData<SpiderLog> selectPageData(SpiderLogForm queryForm) throws Exception;
}
