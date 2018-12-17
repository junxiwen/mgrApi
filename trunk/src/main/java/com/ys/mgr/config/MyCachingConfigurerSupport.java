package com.ys.mgr.config;

import com.ys.mgr.util.crypto.MyHashUtils;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;

import java.lang.reflect.Method;

/**
 * 自定义缓存管理器
 *
 *
 * Date: 2017/12/8 09:21
 */
//@Configuration
@EnableCaching // 开启缓存功能
public class MyCachingConfigurerSupport extends CachingConfigurerSupport {
    /**
     * 自己配置RedisTemplate，序列化key和value，防止乱码
     *
     * @param redisConnectionFactory redis链接工厂，会自动传入
     *
     * @return 序列化key和value的RedisTemplate
     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        // Jackson2JsonRedisSerializer 速度稍慢，占用内存小
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        // 处理日期格式
//        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        // JdkSerializationRedisSerializer 速度快，占用内存大
        /*redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }*/

    /**
     * 自己配置CacheManager
     *
     * @param redisTemplate 序列化key和value的RedisTemplate，会自动传入
     *
     * @return CacheManager
     */
   /* @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(3600); // 有效期默认1小时
        return redisCacheManager;
    }*/

    /**
     * 在使用@Cacheable时，如果不指定key，则使用找个默认的key生成器生成的key
     *
     * @return 根据类名+方法名+参数自动生成的key
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(".").append(method.getName());

                StringBuilder paramsSb = new StringBuilder();
                for (Object param : params) {
                    if (param != null) {
                        paramsSb.append(param.toString());
                    }
                }

                if (paramsSb.length() > 0) {
                    sb.append("_").append(paramsSb);
                }
                return MyConst.KEY_PRE + target.getClass().getName() + "." + method.getName() + "_" + MyHashUtils.md5(sb.toString());
//                return MyConst.KEY_PRE + target.getClass().getName() + "." + method.getName() + "_" + sb.toString();
            }
        };
    }
}
