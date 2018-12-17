package com.ys.mgr.util.http.httpclient;


import com.ys.mgr.config.MyPropertyUtil;
import com.ys.mgr.util.MyStringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * Date: 2018/01/26 15:36
 */
@Slf4j
public class MyProxyIpUtils {
    private static MyProxyIp myProxyIp = null;

    public static MyProxyIp getProxyIp() {
        if (myProxyIp == null || myProxyIp.getTimestamp() == null || myProxyIp.getIp() == null || myProxyIp.getPort() == null
                || System.currentTimeMillis() - myProxyIp.getTimestamp() > MyPropertyUtil.getMyStaticPropertyUtils().getProxyTimeout()) {
            try {
                String result = MyHttpClientUtils.httpGetRequest(MyPropertyUtil.getMyStaticPropertyUtils().getProxyUrl(),
                        2, 5).getResult();
                //由于请求IP的时间过短、可能会出现"too many requests"
                if (MyStringUtils.isBlank(result) || result.contains("too many requests")) {
                    return myProxyIp;
                }
                String ip = result.split(",")[0];
                String i[] = ip.split(":");
                myProxyIp = new MyProxyIp();
                myProxyIp.setIp(i[0]);
                myProxyIp.setPort(Integer.valueOf(i[1]));
                myProxyIp.setTimestamp(System.currentTimeMillis());
            } catch (Exception e) {
                log.error("getProxyIp error ", e);
            }
        }
        return myProxyIp;
    }
}
