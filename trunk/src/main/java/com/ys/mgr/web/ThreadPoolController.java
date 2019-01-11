package com.ys.mgr.web;

import com.ys.mgr.po.ThreadPoolBean;
import com.ys.mgr.service.ThreadPoolBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/1/11.
 */
@RestController
@RequestMapping("/threadPool")
public class ThreadPoolController {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
    @Autowired
    private ThreadPoolBeanService threadPoolBeanService;

    @GetMapping("/singleInsert")
    public String singleThreadInsert() throws Exception{
        long timeMillis = System.currentTimeMillis();
        ThreadPoolBean threadPoolBean = new ThreadPoolBean();
        for(int i = 0 ; i < 100 ; i ++){
            threadPoolBean.setName("单线程添加数据"+i);
            threadPoolBean.setInsertTime(new Date());
            threadPoolBeanService.insert(threadPoolBean);
        }
        long timeMillis1 = System.currentTimeMillis();
        System.out.println("耗时:"+(timeMillis1-timeMillis));
        return "执行完成";
    }

    @GetMapping("/poolInsert")
    public String threadPoolInsert() throws Exception{
        for(int i = 0 ; i < 500 ; i ++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
        }
        executor.shutdown();
        return "执行完成";
    }

    class MyTask implements Runnable {
        private int taskNum;

        public MyTask(int num) {
            this.taskNum = num;
        }

        @Override
        public void run() {
            try {
                ThreadPoolBean threadPoolBean = new ThreadPoolBean("多线程添加数据"+taskNum,new Date());
                threadPoolBeanService.insert(threadPoolBean);
            }catch (Exception e){

            }
        }
    }
}
