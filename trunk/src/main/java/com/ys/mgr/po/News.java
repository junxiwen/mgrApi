package com.ys.mgr.po;

import lombok.Data;
import net.miidi.fsj.util.sjp.annotation.Id;
import net.miidi.fsj.util.sjp.annotation.IgnoredField;
import net.miidi.fsj.util.sjp.annotation.Table;

import java.util.Date;

/**
 * Created by Administrator on 2018/12/14.
 */
@Data
@Table("news")
public class News {
    @Id
    private Integer id;
    private String title;
    private String url;
    private String content;
    private Date insertTime;
    private Integer status;//是否已经失效 0正常 1失效
    private Date updateTime;

    @IgnoredField
    public String getStatus2(){
        return new Integer(0).equals(status)?"正常":"失效";
    }
}
