package com.ys.mgr.po;

import lombok.Data;
import net.miidi.fsj.util.sjp.annotation.Id;
import net.miidi.fsj.util.sjp.annotation.Table;

import java.io.Serializable;

/**
 * Created by fsj on 2017/5/23.
 */
@Data
@Table("sys_role")
public class SysRole implements Serializable {
    @Id(autoIncrement = true)
    private Integer id;
    private String descr;
    private String name;
}
