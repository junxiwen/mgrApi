package com.ys.mgr.form.request;

import lombok.Data;

/**
 * Created by Administrator on 2018/12/14.
 */
@Data
public class NewsForm extends MyRequestForm {
    private String content;
    private Integer status;
}
