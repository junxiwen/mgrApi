package com.ys.mgr.schedule;

import com.ys.mgr.Common;
import com.ys.mgr.po.News;
import com.ys.mgr.po.SpiderLog;
import com.ys.mgr.service.NewsService;
import com.ys.mgr.service.SpiderKeyService;
import com.ys.mgr.service.SpiderLogService;
import com.ys.mgr.util.MyCollectionUtils;
import com.ys.mgr.util.MyDateUtils;
import com.ys.mgr.util.MyStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/12/21.
 */
@Slf4j
@Component
public class Spider {

    @Autowired
    private NewsService newsService;

    @Autowired
    private SpiderKeyService spiderKeyService;
    @Autowired
    private SpiderLogService spiderLogService;

    //@Scheduled(cron = "0 0 23 * * ?")
    @Scheduled(cron = "0 */2 * * * ?")
    //@Scheduled(cron = "*/5 * * * * ?")
    public void spiderNews(){
        Date currentTime = MyDateUtils.getCurrentTime();
        String currentTimeStr = MyDateUtils.formatDate(currentTime, "yyyy-MM-dd HH:mm:ss");
        log.info("开始爬取数据:{}",currentTimeStr);
        try {
            List<String> spiderKeyList = initSpiderKeyList();
            //创建client实例
            HttpClient client= HttpClients.createDefault();
            //创建httpget实例
            HttpGet httpGet=new HttpGet("https://news.zhibo8.cc/nba/more.htm");
            httpGet.setConfig(RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build());
            //执行 get请求
            HttpResponse response=client.execute(httpGet);
            //返回获取实体
            HttpEntity entity=response.getEntity();
            //获取网页内容，指定编码
            String web= EntityUtils.toString(entity,"UTF-8");
            //输出网页
            Document doc= Jsoup.parse(web);
            Elements elements = doc.getElementsByTag("a");
            for(int i = 30 ; i <= elements.size() ; i ++){
                try {
                    Element element = elements.get(i-1);
                    String title = element.text(); // 返回元素的文本
                    if(element.parentNode().parentNode().childNodes().size() != 3){
                        continue;
                    }
                    if(MyStringUtils.isNotBlank(title) &&checkTitleIsContainsKey(title,spiderKeyList)){
                        List<Node> nodes = element.parentNode().parentNode().childNodes();
                        String url = nodes.get(0).childNodes().get(0).attributes().toString().split("=")[1].replace("\"", "");
                        String content = nodes.get(1).childNodes().get(0).toString();
                        Node node = nodes.get(2).childNodes().get(0);
                        Date newsTime = MyDateUtils.parseDate(node.toString(), "yyyy-MM-dd HH:mm:ss");//消息发布时间
                        Date currentDay = MyDateUtils.getCurrentDay();
                        if(newsTime.before(currentDay)){//今天之前的消息，不记录
                            continue;
                        }else{
                            try {
                                News news = new News();
                                news.setTitle(title);
                                news.setUrl("http:"+url);
                                news.setContent(content);
                                news.setInsertTime(newsTime);
                                news.setUpdateTime(new Date());
                                news.setStatus(Common.NEWS_STATUS_OK_0);
                                newsService.insert(news);
                            }catch (Exception e){
                            }
                        }
                    }
                }catch (Exception e){
                    log.error("爬取数据失败,",e);
                }
            }
            /*try {
                //log.info("此次数据记录:{},成功数:{},重复数:{}",MyDateUtils.formatDate(currentTime,"yyyy-MM-dd HH:mm:ss"),successNum,repeatNum);
                SpiderLog spiderLog = new SpiderLog();
                spiderLog.setContent("成功数:"+successNum+",重复数:"+repeatNum);
                spiderLog.setInsertTime(currentTime);
                spiderLogService.insert(spiderLog);
                log.info("记录爬虫日志:{}",currentTimeStr);
            }catch (Exception e){

            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean checkTitleIsContainsKey(String title,List<String> keyList){
        for (String key : keyList) {
           if(title.toUpperCase().contains(key.toUpperCase())){
                return true;
           }
        }
        return false;
    }

    //初始化关键字列表
    private List<String> initSpiderKeyList(){
        List<String> spiderKeyList = spiderKeyService.selectAllKey();
        if(MyCollectionUtils.isEmpty(spiderKeyList)){
            spiderKeyList.add("詹");
            spiderKeyList.add("勒布朗");
            spiderKeyList.add("LBJ");
            spiderKeyList.add("Lebron");
            spiderKeyList.add("科比");
        }
        return spiderKeyList;
    }

    public static void main(String[] args) throws Exception {
        String title = "科比n James";
        List<String> keyList = new ArrayList();
        keyList.add("Lebron");
        keyList.add("科比");
        System.out.println(MyDateUtils.getCurrentTime());
        for (String key : keyList) {
            if(title.toUpperCase().contains(key.toUpperCase())){
                System.out.println(true);
                return;
            }
        }
        System.out.println(false);

    }
}
