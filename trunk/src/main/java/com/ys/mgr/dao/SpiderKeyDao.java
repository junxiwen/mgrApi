package com.ys.mgr.dao;

import com.ys.mgr.form.request.SpiderKeyForm;
import com.ys.mgr.po.SpiderKey;
import net.miidi.fsj.util.sjp.dao.BaseDao;

import java.util.List;

/**
 * Created by Administrator on 2018/12/25.
 */
public interface SpiderKeyDao extends BaseDao<SpiderKey,Integer> {

    List<SpiderKey> selectForList(SpiderKeyForm queryForm) throws Exception;
    long selectForCount(SpiderKeyForm queryForm) throws Exception;

    List<String> selectAllKey();
}
