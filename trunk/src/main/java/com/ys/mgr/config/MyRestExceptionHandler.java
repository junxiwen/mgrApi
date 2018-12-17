package com.ys.mgr.config;

import com.ys.mgr.form.response.MyResponseForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * RestAPI 统一异常处理，如果要处理页面请求的异常可以用@ControllerAdvice
 *
 *
 * Date: 2017/12/8 09:29
 */
@Slf4j
@RestControllerAdvice
public class MyRestExceptionHandler {
    /**
     * 不指定异常类型，处理所有类型的异常
     */
    @ExceptionHandler
    public MyResponseForm<String> handleControllerException(HttpServletRequest request, Throwable ex) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        // 纪录原始错误日志
        log.error("requestUri: {}, statusCode: {}, error: {}", request.getRequestURI(), statusCode, ex.toString());
        return new MyResponseForm<String>().returnError("rquest error!");
    }
}
