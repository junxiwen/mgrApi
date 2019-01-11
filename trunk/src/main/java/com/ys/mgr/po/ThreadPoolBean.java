package com.ys.mgr.po;

import lombok.Data;
import net.miidi.fsj.util.sjp.annotation.Id;
import net.miidi.fsj.util.sjp.annotation.Table;

import java.util.Date;

/**
 * Created by Administrator on 2019/1/11.
 */
@Data
@Table("thread_tbl")
public class ThreadPoolBean {
    @Id
    private Integer id;
    private String name;
    private Date insertTime;

    public ThreadPoolBean(String name, Date insertTime) {
        this.name = name;
        this.insertTime = insertTime;
    }

    public ThreadPoolBean() {
    }
}
