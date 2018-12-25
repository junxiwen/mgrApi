package com.ys.mgr.web;

import com.ys.mgr.Common;
import com.ys.mgr.form.request.SpiderKeyForm;
import com.ys.mgr.form.response.MyResponseForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.News;
import com.ys.mgr.po.SpiderKey;
import com.ys.mgr.service.SpiderKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by Administrator on 2018/12/25.
 */
@Slf4j
@RestController
@RequestMapping("/spiderKey")
public class SpiderKeyController {
    @Autowired
    private SpiderKeyService spiderKeyService;

    @PostMapping
    public MyResponseForm<PageData<SpiderKey>> list(SpiderKeyForm queryForm) throws Exception {
        try {
            return new MyResponseForm<>(spiderKeyService.selectPageData(queryForm));
        }catch (Exception e){
            log.error("查询关键字失败,",e);
            return new MyResponseForm().returnFail("查询关键字失败");
        }
    }

    @PostMapping("/add")
    public MyResponseForm<String> add(SpiderKey spiderKey) throws Exception {
        try {
            spiderKey.setInsertTime(new Date());
            spiderKeyService.insert(spiderKey);
            return new MyResponseForm().returnSuccess("新增成功");
        }catch (Exception e){
            log.error("新增失败,",e);
            return new MyResponseForm().returnFail("新增失败");
        }
    }

    @GetMapping("/del/{id}")
    public MyResponseForm<String> del(@PathVariable("id")Integer id) throws Exception {
        try {
            spiderKeyService.delById(id);
            return new MyResponseForm().returnSuccess("删除成功");
        }catch (Exception e){
            log.error("删除失败,",e);
            return new MyResponseForm().returnFail("删除失败");
        }
    }
}
