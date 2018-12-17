package com.ys.mgr.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *过滤器
 *
 *
 * Date: 2017/12/8 11:52
 */
@Slf4j
@WebFilter("/*")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if(log.isDebugEnabled()) {
            log.debug("filter init"); // 项目启动的时候init
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 使用自定义的HttpServletRequestWrapper扩展ServletRequest，后面拿到的request都是MyHttpServletRequestWrapper
        MyHttpServletRequestWrapper httpServletRequest = new MyHttpServletRequestWrapper((HttpServletRequest) request);
        if(log.isDebugEnabled()) {
            log.debug("filter doFilter {}", httpServletRequest.getRequestURI());
        }
        chain.doFilter(httpServletRequest, response);
    }

    @Override
    public void destroy() {
        if(log.isDebugEnabled()) {
            log.debug("filter destroy"); //项目关闭的时候destroy
        }
    }
}
