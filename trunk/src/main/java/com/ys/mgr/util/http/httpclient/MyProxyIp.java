package com.ys.mgr.util.http.httpclient;

import lombok.Data;

/**
 *
 * Date: 2018/01/26 15:36
 */
@Data
public class MyProxyIp {
    private String ip;
    private Integer port;
    private Long timestamp = 0l;
}
