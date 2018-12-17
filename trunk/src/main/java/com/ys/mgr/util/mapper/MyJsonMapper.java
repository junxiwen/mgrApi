package com.ys.mgr.util.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.TimeZone;

/**
 * Json转换工具
 *
 *
 * Date: 2017/12/8 11:11
 */
@Slf4j
public class MyJsonMapper {
    /**
     * Include.NON_NULL 风格的Mapper，Empty(如List.isEmpty)的集合会输出
     */
    public static final MyJsonMapper NON_NULL_MAPPER = new MyJsonMapper(JsonInclude.Include.NON_NULL);
    /**
     * Include.ALWAYS 风格的Mapper，NULL 和 EMPTY也全部输出
     */
    public static final MyJsonMapper ALWAYS_MAPPER = new MyJsonMapper(JsonInclude.Include.ALWAYS);
    /**
     * Include.NON_EMPTY 风格的Mapper，NULL 和 Empty(如List.isEmpty)的集合都不输出
     */
    public static final MyJsonMapper NON_EMPTY_MAPPER = new MyJsonMapper(JsonInclude.Include.NON_EMPTY);

    private ObjectMapper mapper;

    private MyJsonMapper(JsonInclude.Include include) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 空值处理为空串
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jg, SerializerProvider provider) throws IOException {
                jg.writeString("");
            }
        });
        // 设置时区
        mapper.setTimeZone(TimeZone.getDefault());// getTimeZone("GMT+8:00")
    }

    /**
     * 自定义输出风格
     *
     * @param include Include.NON_NULL、Include.NON_EMPTY 等等
     *
     * @return
     */
    public static MyJsonMapper getInstance(JsonInclude.Include include) {
        return new MyJsonMapper(include);
    }

    /**
     * 对象转json字符串
     * @param object JavaBean
     * @return json字符串
     */
    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 转换JSON字符串为简单的对象，支持简单集合，如：List<String>
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
     * <p>
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     *
     * @param jsonString json字符串
     * @param clazz 转换成的JavaBean的类型
     * @param <T> 转换成的JavaBean的类型
     * @return 转换成的JavaBean
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>或Map如Map<String, Bean>, 先使用buildCollectionType()或buildMapType()构造类型, 然后再调用本函数.
     *
     * @param jsonString json字符串
     * @param javaType buildCollectionType()或buildMapType()生成的JavaType
     * @param <T> 转换成的集合类型
     * @return 转换成的集合对象
     */
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            log.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }


    /**
     * 构造Collection类型.
     *
     * @param collectionClass 集合类型
     * @param elementClass 集合元素的类型
     * @return JavaType
     */
    public JavaType buildCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Map类型.
     *
     * @param mapClass map类型
     * @param keyClass key类型
     * @param valueClass value类型
     * @return JavaType
     */
    public JavaType buildMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆覆盖部分的属性.
     */
    public void update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            log.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        } catch (IOException e) {
            log.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
    }

    /**
     * 允许单引号 允许不带引号的字段名称
     */
    private MyJsonMapper enableSimple() {
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return this;
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

}
