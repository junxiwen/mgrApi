package com.ys.mgr.config;

import com.ys.mgr.util.MyDateUtils;
import com.ys.mgr.util.MyStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * WebMvc配置扩展，各种webmvc相关到配置都可以在这里扩展
 * 比如：addFormatters、addInterceptors、configureMessageConverters等等
 *
 *
 * Date: 2017/12/8 09:30
 */
@Slf4j
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    /**
     * 增加拦截器
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用于排除拦截规则
        //registry.addInterceptor(new MyAuthHandlerInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new MySecurityHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/sysUser/login","/sysUser/*", "/sysUser/logout", "/error","/threadPool/*");
        super.addInterceptors(registry);
    }

    /**
     * 增加数据格式化
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);

        // 格式化日期
        registry.addFormatter(new Formatter<Date>() {
            @Override
            public Date parse(String text, Locale locale) throws ParseException {
                log.debug("addFormatter > parse {}; {}", text, locale);
                if (MyStringUtils.isEmpty(text))
                    return null;
                return MyDateUtils.parseDate(text.trim());
            }

            @Override
            public String print(Date date, Locale locale) {
                log.debug("addFormatter > print {}; {}", date, locale);
                return MyDateUtils.formatDateTime(date);
            }
        });
//        // addFormatter 和 addConverter 二选一即可，后面的会覆盖前面的
//        registry.addConverter(new Converter<String, Date>() {
//            @Override
//            public Date convert(String source) {
//                log.debug("addConverter {}", source);
//                if (MyStringUtils.isBlank(source))
//                    return null;
//                return MyDateUtils.parseDate(source.trim());
//            }
//        });
    }
}
