package com.ys.mgr.util.http;


import com.ys.mgr.config.MyPropertyUtil;
import com.ys.mgr.util.MyMapUtils;
import com.ys.mgr.util.MyStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * Date: 2017/12/22 10:35
 */
@Slf4j
public class MyHttpUtils {
    /**
     * 获取当前请求对象
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前项目url路径，结尾带"/"
     *
     * @return
     */
    public static String getCurrentUrlPath() {
        return getCurrentUrlPathNoSplash() + "/";
    }

    /**
     * 获取当前项目url路径，结尾不带"/"
     *
     * @return
     */
    public static String getCurrentUrlPathNoSplash() {
        try {
            HttpServletRequest request = getRequest();
            return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 获取当前项目域名，结尾不带"/"
     *
     * @return
     */
    public static String getCurrentDomain() {
        try {
            HttpServletRequest request = getRequest();
            return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取客户端真实ip地址
     *
     * @param request
     *
     * @return
     */
    public static String getRealClientIP(HttpServletRequest request) {
        return getRealIP(request);
    }

    /**
     * 获取客户端真实ip地址
     *
     * @return
     */
    public static String getRealClientIP() {
        return getRealIP(getRequest());
    }

    private static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (filterIp(ip) != null) {
            return ip;
        }

        ip = request.getHeader("X-Real-IP");
        if (filterIp(ip) != null) {
            return ip;
        }

        ip = request.getHeader("Proxy-Client-IP");
        if (filterIp(ip) != null) {
            return ip;
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (filterIp(ip) != null) {
            return ip;
        }

        return request.getRemoteAddr();
    }

    private static String filterIp(String ip) {
        try {
            if (ip == null) {
                return null;
            }
            int index = ip.indexOf(",");
            if (index != -1) {
                String[] allForward = ip.split(",");
                for (int i = 0; i < allForward.length; i++) {
                    String oneIp = allForward[i].trim();
                    if (MyStringUtils.isNotBlank(oneIp) && !"unKnown".equalsIgnoreCase(oneIp) && !oneIp.startsWith("127.")
                            && !oneIp.startsWith("10.") && !oneIp.startsWith("100.") && !oneIp.startsWith("192.168")) {
                        return oneIp;
                    }
                }
            } else {
                String oneIp = ip.trim();
                if (MyStringUtils.isNotBlank(oneIp) && !"unKnown".equalsIgnoreCase(oneIp) && !oneIp.startsWith("127.")
                        && !oneIp.startsWith("10.") && !oneIp.startsWith("100.") && !oneIp.startsWith("192.168")) {
                    return oneIp;
                }
            }
        } catch (Exception e) {
            log.error("filterIp error", e);
        }
        return null;
    }

    /**
     * 解析参数为map
     *
     * @param paramStr
     *
     * @return
     */
    public static Map<String, String> paramStrToMap(String paramStr) {
        return MyMapUtils.toMap(paramStr);
    }

    /**
     * 获取UserAgent
     *
     * @return
     */
    public static String getUserAgentStr() {
        return getRequest().getHeader("User-Agent");
    }

    /**
     * 获取UserAgent
     *
     * @return
     */
    /*public static UserAgent getUserAgent() {
        return UserAgent.parseUserAgentString(getUserAgentStr());
    }

    *//**
     * 获得客户端浏览器信息，品牌、类型、版本等等
     * @return
     *//*
    public static Browser getBrowser() {
        return getUserAgent().getBrowser();
    }

    *//**
     * 获得客户端系统信息，品牌、类型、版本等等
     * @return
     *//*
    public static OperatingSystem getOperatingSystem() {
        return getUserAgent().getOperatingSystem();
    }
*/
    /**
     * 得到图片等资源的完整URL
     * @param res
     * @return
     */
    public static String getRealResUrl (String res) {
        if (MyStringUtils.isBlank(res) || res.startsWith("http") || res.startsWith("ftp")) {
            return res;
        }
        return MyPropertyUtil.getMyStaticPropertyUtils().getResUrlPath() + (res.startsWith("/") ? res : "/" + res);
    }
}
