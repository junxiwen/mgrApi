package com.ys.mgr.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import net.miidi.fsj.util.sjp.annotation.Column;
import net.miidi.fsj.util.sjp.annotation.Id;
import net.miidi.fsj.util.sjp.annotation.IgnoredField;
import net.miidi.fsj.util.sjp.annotation.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fsj on 2017/5/19.
 */
@Data
@Table("sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 8302393766287956651L;
    @Id(autoIncrement = true)
    private Integer id;
    private String userName;
    @JsonIgnore
    private String password;
    private String mobilephone;
    private String realname;

    private Integer status;
    @Column(value = "insertTime", updateAble = false)
    private Date insertTime;
    private String descr;
    @JsonIgnore
    @Column(value = "salt", updateAble = false)
    private String salt;

    @IgnoredField
    private List<Integer> roleIds = new ArrayList<>(0);
    @IgnoredField
    private String accessToken;
    @IgnoredField
    public static final int ON = 0;
    @IgnoredField
    public static final int OFF = 1;
}
