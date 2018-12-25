package com.ys.mgr.web;

import com.ys.mgr.form.request.SpiderLogForm;
import com.ys.mgr.form.response.MyResponseForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.SpiderLog;
import com.ys.mgr.service.SpiderLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/12/25.
 */
@Slf4j
@RestController
@RequestMapping("/spiderLog")
public class SpiderLogController {

    @Autowired
    private SpiderLogService spiderLogService;

    @PostMapping
    public MyResponseForm<PageData<SpiderLog>> list(SpiderLogForm queryForm) throws Exception {
        try {
            return new MyResponseForm<>(spiderLogService.selectPageData(queryForm));
        }catch (Exception e){
            log.error("查询日志失败,",e);
            return new MyResponseForm().returnFail("查询日志失败");
        }
    }
}
