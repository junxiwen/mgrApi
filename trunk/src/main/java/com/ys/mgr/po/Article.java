package com.ys.mgr.po;

import lombok.Data;
import net.miidi.fsj.util.sjp.annotation.Id;
import net.miidi.fsj.util.sjp.annotation.IgnoredField;
import net.miidi.fsj.util.sjp.annotation.Table;

import java.util.Date;

/**
 * Created by Administrator on 2019/1/4.
 */
@Data
@Table("article")
public class Article {
    @Id
    private Integer id;
    private String url;
    private String title;
    private String content;//个人备注
    private Integer type;//类型 1、技术文章；2、逸闻趣事
    private Date insertTime;

    @IgnoredField
    public String getType2(){
        if(new Integer(1).equals(type)){
            return "技术文章";
        }else{
            return "逸闻趣事";
        }
    }
}