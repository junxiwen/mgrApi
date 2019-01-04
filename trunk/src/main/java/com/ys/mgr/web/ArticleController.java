package com.ys.mgr.web;

import com.ys.mgr.form.request.ArticleForm;
import com.ys.mgr.form.response.MyResponseForm;
import com.ys.mgr.form.response.PageData;
import com.ys.mgr.po.Article;
import com.ys.mgr.service.ArticleService;
import com.ys.mgr.util.MyDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/1/4.
 */
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public MyResponseForm<PageData<Article>> list(ArticleForm queryForm) throws Exception {
        try {
            return new MyResponseForm<>(articleService.selectPageData(queryForm));
        }catch (Exception e){
            log.error("查询新闻列表失败,",e);
            return new MyResponseForm().returnFail("查询新闻列表失败");
        }
    }

    @PostMapping("/addOrUpdate")
    public MyResponseForm<String> add(Article article) throws Exception {
        try {
            Integer id = article.getId();
            if(id == null ){
                article.setInsertTime(MyDateUtils.getCurrentTime());
                articleService.insert(article);
            }else{
                articleService.update(article);
            }
            return new MyResponseForm().returnSuccess("编辑成功");
        }catch (Exception e){
            log.error("编辑失败,",e);
            return new MyResponseForm().returnFail("编辑失败");
        }
    }

    @GetMapping("/del/{id}")
    public MyResponseForm<String> del(@PathVariable("id")Integer id) throws Exception {
        try {
            articleService.delById(id);
            return new MyResponseForm().returnSuccess("删除文章成功");
        }catch (Exception e){
            log.error("删除失败,",e);
            return new MyResponseForm().returnFail("删除失败");
        }
    }
}
