package com.ys.mgr.service;

import com.ys.mgr.dao.SpiderKeyDao;
import com.ys.mgr.form.request.SpiderKeyForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SpiderKey;
import lombok.extern.slf4j.Slf4j;
import net.miidi.fsj.util.sjp.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/12/25.
 */
@Slf4j
@Service
@Transactional // 类级别的事务控制
public class SpiderKeyServiceImpl extends BaseServiceImpl<SpiderKey,Integer> implements SpiderKeyService{

    private static final String cacheDemo = "spider";

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
    @Cacheable(cacheNames = cacheDemo, key = "'spider_key'")
    public List<String> selectAllKey() {
        return spiderKeyDao.selectAllKey();
    }

    @Override
    @CacheEvict(cacheNames = cacheDemo, key = "'spider_key'")//清空指定key的缓存
    public void delById(Integer id) throws Exception{
        spiderKeyDao.deleteById(id);
        log.error("删除关键字:{}，清空缓存",id);
    }
}
