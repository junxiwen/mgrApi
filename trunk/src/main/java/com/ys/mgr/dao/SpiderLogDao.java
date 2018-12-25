package com.ys.mgr.dao;

import com.ys.mgr.form.request.SpiderLogForm;
import com.ys.mgr.po.SpiderLog;
import net.miidi.fsj.util.sjp.dao.BaseDao;

import java.util.List;

/**
 * Created by Administrator on 2018/12/25.
 */
public interface SpiderLogDao extends BaseDao<SpiderLog,Integer> {
    List<SpiderLog> selectForList(SpiderLogForm queryForm) throws Exception;
    long selectForCount(SpiderLogForm queryForm) throws Exception;
}
