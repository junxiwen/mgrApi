package com.ys.mgr.po;

import lombok.Data;
import net.miidi.fsj.util.sjp.annotation.Id;
import net.miidi.fsj.util.sjp.annotation.Table;

import java.util.Date;

/**
 * Created by Administrator on 2018/12/25.
 */
@Data
@Table("spider_log")
public class SpiderLog {
    @Id
    private Integer id;
    private String content;
    private Date insertTime;
}