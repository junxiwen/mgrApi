package com.ys.mgr.config;

import com.ys.mgr.form.response.MyResponseForm;
import com.ys.mgr.util.crypto.MyHashUtils;
import com.ys.mgr.util.mapper.MyJsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Date: 2018/01/22 18:11
 */
@Slf4j
public class MyAuthHandlerInterceptor implements HandlerInterceptor {
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    //请求开始之前的处理
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        startTime.set(System.currentTimeMillis());
        if (log.isDebugEnabled()) {
            log.debug("1. {} {} preHandle", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        }
        boolean result = false; // 是否通过验证

        try {
            // 获取请求头的Authorization
            String authorization = httpServletRequest.getHeader("Authorization");
            if (StringUtils.hasLength(authorization)) {
                authorization = authorization.trim();
                int i = authorization.indexOf(" ");
                String authType = authorization.substring(0, i);
                String auth = new String(Base64Utils.decodeFromString(authorization.substring(i + 1)));

                if (log.isDebugEnabled()) {
                    log.debug("{} {} authType: {}; auth: {}; JSessionId: {}", httpServletRequest.getMethod(),
                            httpServletRequest.getRequestURI(), authType, auth, httpServletRequest.getRequestedSessionId());
                }

                // 可以为每个渠道(client_id)分配一个用户名密码，随时控制某个渠道的访问
                // Authorization: Basic qqlg-client-1:qqlg-client-1${accessToken}
                if (auth.contains(MyConst.BASE_AUTH)) {
                    result = true;
                }


                String accessToken = auth.substring(MyConst.BASE_AUTH.length());
                String mdtoken = httpServletRequest.getParameter("mdtoken");
                String timestamp = httpServletRequest.getParameter("timestamp");
                String accessUserId = httpServletRequest.getParameter("accessUserId");
                // TODO 验证 timestamp 时间差

                // 验证mdtoken，SHA256(accessToken+userId+timestamp+MgrConst.SHA256_KEY)
                // 网页和客户端一定要用不同的 MgrConst.SHA256_KEY，因为网页的代码很容易被人抓到
                String mdtoken2 = MyHashUtils.sha256(accessToken + accessUserId + timestamp + MyConst.SHA256_KEY);
                if (log.isDebugEnabled()) {
                    log.debug("accessToken {} accessUserId {} timestamp {} mdtoken2 {}", accessToken, accessUserId, timestamp, mdtoken2);
                }
                if (!mdtoken2.equalsIgnoreCase(mdtoken)) {
                    result = false;
                }
            }
        } catch (Exception e) {
            log.error("{} {} error {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), e);
            result = false;
        }

        if (!result) {
            // 没通过验证的返回错误提示，因为是restful的api所以全部返回json
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().print(MyJsonMapper.NON_NULL_MAPPER.toJson(new MyResponseForm<String>().returnError()));
        }
        return result; // 只有返回true才会继续向下执行，返回false取消当前请求
    }

    //请求开始之后，视图返回之前的处理
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("2. {} {} postHandle", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        }
    }

    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        long ms = System.currentTimeMillis() - startTime.get();
        if (log.isDebugEnabled()) {
            log.debug("3. {} {} 耗时: {} 毫秒 afterCompletion", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), ms);
        }
    }
}
