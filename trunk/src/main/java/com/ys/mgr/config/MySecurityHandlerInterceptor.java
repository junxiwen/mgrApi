package com.ys.mgr.config;

import com.ys.mgr.form.response.MyResponseForm;
import com.ys.mgr.po.SysResource;
import com.ys.mgr.po.SysUser;
import com.ys.mgr.util.mapper.MyJsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * Date: 2018/01/22 18:12
 */
@Slf4j
public class MySecurityHandlerInterceptor implements HandlerInterceptor {
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    //请求开始之前的处理
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        startTime.set(System.currentTimeMillis());
        if (log.isDebugEnabled()) {
            log.debug("1. {} {} preHandle", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        }
        String requestMethod = httpServletRequest.getMethod();
        String requestURI = httpServletRequest.getRequestURI();

        if (httpServletRequest.getSession().getAttribute(MyConst.SESSION_USER_NAME) == null
                || httpServletRequest.getSession().getAttribute(MyConst.SESSION_RESOURCES_NAME) == null) {
            log.info("{} {} no session", requestMethod, requestURI);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().print(MyJsonMapper.NON_NULL_MAPPER.toJson(new MyResponseForm<String>().returnNoLogin("no login")));
            return false;
        } else {
            SysUser sysUser = (SysUser) httpServletRequest.getSession().getAttribute(MyConst.SESSION_USER_NAME);

            // 判断 accessToken，其实有 session 了就不需要 accessToken 了，放着吧，供参考
            // 客户端拿到 accessToken 之后，先把它存起来（比如存到 SessionStorage 里），请求时在 HEADER 里 Authorization 设置
            // 把 accessToken 拼接到 auth 的 password 后面，Authorization: Basic qqlg-client-1:qqlg-client-1${accessToken}
            String authorization = httpServletRequest.getHeader("Authorization");
            String accessToken = "";
            if (StringUtils.hasLength(authorization)) {
                authorization = authorization.trim();
                authorization = new String(Base64Utils.decodeFromString(authorization.substring(authorization.indexOf(" ") + 1)));
                accessToken = authorization.substring(MyConst.BASE_AUTH.length());
                if (log.isDebugEnabled()) {
                    log.debug("{} {} accessToken: {}; accessUser: {}", requestMethod, requestURI, accessToken, sysUser);
                }
            }
            if (!sysUser.getAccessToken().equals(accessToken)) {
                log.error("{} {} accessToken error accessToken: {}; accessUser: {}", requestMethod, requestURI, accessToken, sysUser);
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().print(MyJsonMapper.NON_NULL_MAPPER.toJson(new MyResponseForm<String>().returnNoLogin("no login")));
                return false;
            }

            // TODO accessToken 一致了那就判断一下 accessUserId 是否一致，accessToken如果不一致 userId 也可能不一致
            String accessUserId = httpServletRequest.getParameter("accessUserId");
            if (!sysUser.getId().toString().equalsIgnoreCase(accessUserId)) {
                log.error("{} {} accessToken error accessToken: {}; accessUser: {}", requestMethod, requestURI, accessToken, sysUser);
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().print(MyJsonMapper.NON_NULL_MAPPER.toJson(new MyResponseForm<String>().returnNoLogin("no login")));
                return false;
            }

            //判断有没有权限 no permission
//            if (!MyConst.ADMIN_ID.equals(sysUser.getId()) && !hasPermission(httpServletRequest)) {
//                log.error("{} {} no permission", requestMethod, requestURI);
//                httpServletResponse.setContentType("application/json;charset=UTF-8");
//                httpServletResponse.getWriter().print(MyJsonMapper.NON_NULL_MAPPER.toJson(new MyResponseForm<String>().returnNoPermission("no permission")));
//                return false;
//            }

        }
        return true; // 只有返回true才会继续向下执行，返回false取消当前请求
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

    private boolean hasPermission(HttpServletRequest httpServletRequest) {
        List<SysResource> accessUserResources = (List<SysResource>) httpServletRequest.getSession().getAttribute(MyConst.SESSION_RESOURCES_NAME);
        if (accessUserResources == null || accessUserResources.isEmpty()) {
            return false;
        }
        String requestMethod = httpServletRequest.getMethod();
        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.startsWith("//")) {
            requestURI = requestURI.substring(1);
        }
        for (SysResource sysResource : accessUserResources) {
            if (requestMethod.equalsIgnoreCase(sysResource.getMethod()) && sysResource.getUrl() != null) {
                if (sysResource.getUrl().equalsIgnoreCase(requestURI)) {
                    return true;
                }
                if (sysResource.getUrl().contains(")") && Pattern.matches(sysResource.getUrl(), requestURI)) {
                    return true;
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.error("{} {} no permission", requestMethod, requestURI);
        }
        return false;
    }
}
