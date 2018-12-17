package com.ys.mgr.util.http.httpclient;

import lombok.Data;

/**
 *
 * Date: 2017/12/11 16:09
 */
@Data
public class MyHttpClientResult {
    //http请求状态码
    private int code;
    //http请求结果
    private String result;
}
