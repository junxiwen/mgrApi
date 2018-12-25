package com.ys.mgr.service;

import com.ys.mgr.dao.SpiderKeyDao;
import com.ys.mgr.form.request.SpiderKeyForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SpiderKey;
import net.miidi.fsj.util.sjp.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/12/25.
 */
@Service
public class SpiderKeyServiceImpl extends BaseServiceImpl<SpiderKey,Integer> implements SpiderKeyService{
    @Autowired
    private SpiderKeyDao spiderKeyDao;

    @Override
    public PageData<SpiderKey> selectPageData(SpiderKeyForm queryForm) throws Exception {
        PageData<SpiderKey> pageData = new PageData<>(queryForm);
        //查询总数
        pageData.setTotal(spiderKeyDao.selectForCount(queryForm));
        if (pageData.getTotal() > 0) {
            //查询列表详情
            pageData.setData(spiderKeyDao.selectForList(queryForm));
        }
        return pageData;
    }

    @Override
    public List<String> selectAllKey() {
        return spiderKeyDao.selectAllKey();
    }

    @Override
    public void delById(Integer id) throws Exception{
        spiderKeyDao.deleteById(id);
    }
}
