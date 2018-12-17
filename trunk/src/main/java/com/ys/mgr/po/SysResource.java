package com.ys.mgr.po;

import lombok.Data;
import net.miidi.fsj.util.sjp.annotation.Id;
import net.miidi.fsj.util.sjp.annotation.IgnoredField;
import net.miidi.fsj.util.sjp.annotation.Table;

import java.io.Serializable;

/**
 * Created by fsj on 2017/5/22.
 */
@Data
@Table("sys_resource")
public class SysResource implements Serializable {
    @Id(autoIncrement = true)
    private Integer id;
    private String method;
    private String url;
    private String name;
    private String simpleName;//简写
    private Integer type = 1;
    private String orderNum;
    private Integer parentId;
    private String icon;

    @IgnoredField
    public static final Integer menu = 0;
    @IgnoredField
    public static final Integer button = 1;
}