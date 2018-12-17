package com.ys.mgr.web;

import com.ys.mgr.Common;
import com.ys.mgr.form.request.NewsForm;
import com.ys.mgr.form.response.MyResponseForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.News;
import com.ys.mgr.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by Administrator on 2018/12/14.
 */
@Slf4j
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping
    public MyResponseForm<PageData<News>> list(NewsForm queryForm) throws Exception {
        try {
            return new MyResponseForm<>(newsService.selectPageData(queryForm));
        }catch (Exception e){
            log.error("查询新闻列表失败,",e);
            return new MyResponseForm().returnFail("查询新闻列表失败");
        }
    }

    @PostMapping("/add")
    public MyResponseForm<String> add(News news) throws Exception {
        try {
            news.setStatus(Common.NEWS_STATUS_OK_0);
            news.setInsertTime(new Date());
            news.setUpdateTime(new Date());
            newsService.insert(news);
            return new MyResponseForm().returnSuccess("新增成功");
        }catch (Exception e){
            log.error("新增失败,",e);
            return new MyResponseForm().returnFail("新增失败");
        }
    }

    @GetMapping("/update/{id}")
    public MyResponseForm<String> del(@PathVariable("id")Integer id) throws Exception {
        try {
            News news = newsService.selectById(id);
            if(Common.NEWS_STATUS_STOP_1.equals(news.getStatus())){
                news.setStatus(Common.NEWS_STATUS_OK_0);
            }else{
                news.setStatus(Common.NEWS_STATUS_STOP_1);
            }
            news.setUpdateTime(new Date());
            newsService.update(news);
            return new MyResponseForm().returnSuccess("更新成功");
        }catch (Exception e){
            log.error("更新失败,",e);
            return new MyResponseForm().returnFail("更新失败");
        }
    }
}
