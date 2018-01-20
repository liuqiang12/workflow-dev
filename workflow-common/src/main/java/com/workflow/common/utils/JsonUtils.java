package com.workflow.common.utils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author LIUQIANG
 * @description：
 **/
public class JsonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class.getName());
    private ObjectMapper mapper;
    private static JsonUtils obj = new JsonUtils();
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";
    private static final DateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
    private static final String DEFAULT_TIMEZONE = "GMT+8:00";
    private String timeZone = DEFAULT_TIMEZONE;

    private JsonUtils(){
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static JsonUtils getInstance() {
        synchronized (obj) {
            if (obj == null) {
                return new JsonUtils();
            }
        }
        return obj;
    }

    public static void exceptionHandler(Exception e) {
        IllegalStateException ex = new IllegalStateException(e.getMessage(), e);
        LOGGER.error(ex.getMessage(), ex);
        throw ex;
    }

    public JavaType getListType(Class<?> elementClasses) {
        return mapper.getTypeFactory().constructParametricType(List.class, elementClasses);
    }

    /**
     * 将JAVA对象序列化为JSON字符串.此为原始方法
     * <P>本方法可设置时间格式,<code>null</code>值过滤开关,包含/排除开关,包含/排除的属性数组
     * @param value 被序列化的对象
     * @param dateFormat 时间格式,默认格式为<tt>yyyy-MM-dd HH:mm:ss,SSS</tt>,默认时区为GMT+8
     * @param nullskip 是否跳过<code>null</code>值属性,默认为不跳过
     * @param isInclude 包含/排除开关,默认为排除
     * @param mixInClass MixIn接口(子接口,实现类)Class对象
     * @param filteFields 被包含/排除的属性名称数组
     * @return JSON字符串
     */
    public String toJson(Object value, DateFormat dateFormat, boolean nullskip, boolean isInclude, Class<? extends FilterMixIn> mixInClass, String... filteFields) {
        if (nullskip) {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            LOGGER.debug("JSON - 序列化时跳过空值属性");
        }
        if (dateFormat != null) {
            if (timeZone != null) {
                dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
                LOGGER.debug("JSON-设置时区为:{}", timeZone);
            }
            mapper.setDateFormat(dateFormat);
            if (dateFormat instanceof SimpleDateFormat) {
                SimpleDateFormat f = (SimpleDateFormat) dateFormat;
                LOGGER.debug("JSON-设置时间格式为:{}", f.toPattern());
            } else if (dateFormat instanceof ISO8601DateFormat) {
                LOGGER.debug("JSON-设置时间格式为:{}", "yyyy-MM-ddThh:mm:ssZ");
            }
        }
        if (filteFields != null && filteFields.length > 0) {
            String filterId = mixInClass.getAnnotation(JsonFilter.class).value();
            FilterProvider filters = null;
            if (isInclude) {
                filters = new SimpleFilterProvider().addFilter(filterId,
                        SimpleBeanPropertyFilter.filterOutAllExcept(filteFields));
                LOGGER.debug("JSON-设置属性过滤规则:仅包含![{}]", Arrays.toString(filteFields));
            } else {
                filters = new SimpleFilterProvider().addFilter(filterId,
                        SimpleBeanPropertyFilter.serializeAllExcept(filteFields));
                LOGGER.debug("JSON-设置属性过滤规则:仅排除![{}]", Arrays.toString(filteFields));
            }
            mapper.setFilterProvider(filters);
        }
        if (mixInClass != null) {
            mapper.addMixIn(value.getClass(), mixInClass);
        }
        String result = null;
        try {
            result = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            exceptionHandler(e);
        }
        return result;
    }


    public String toJsonExcludeProperty(Object value, Class<? extends FilterMixIn> mixInClass, String... filteFields) {
        return toJson(value, DEFAULT_DATEFORMAT, false, false, mixInClass, filteFields);
    }


    public String toJsonIncludeProperty(Object value, Class<? extends FilterMixIn> mixInClass, String... filteFields) {
        return toJson(value, DEFAULT_DATEFORMAT, false, true, mixInClass, filteFields);
    }


    public String toJson(Object value, Class<? extends FilterMixIn> mixInClass) {
        return toJson(value, DEFAULT_DATEFORMAT, false, false, mixInClass, new String[0]);
    }

    /**
     * 将JAVA对象序列化为JSON字符串,重载方法1
     * @param value 被序列化的对象
     * @param dateFormat 时间格式,默认格式为<tt>yyyy-MM-dd HH:mm:ss,SSS</tt>,默认时区为GMT+8
     * @param nullskip 是否跳过<code>null</code>值属性,默认为不跳过
     * @return JSON字符串
     * @see com.workflow.common.utils.JsonUtils#toJson(Object, DateFormat, boolean, boolean, Class, String...)
     */
    public String toJson(Object value, DateFormat dateFormat, boolean nullskip) {
        return toJson(value, dateFormat, nullskip, false, null, new String[0]);
    }

    /**
     * 将JAVA对象序列化为JSON字符串,重载方法2
     * @param value 被序列化的对象
     * @param dateFormat 时间格式,默认格式为<tt>yyyy-MM-dd HH:mm:ss,SSS</tt>,默认时区为GMT+8
     * @return JSON字符串
     * @see com.workflow.common.utils.JsonUtils#toJson(Object, DateFormat, boolean)
     */
    public String toJson(Object value, DateFormat dateFormat) {
        return toJson(value, dateFormat, false);
    }

    /**
     * 将JAVA对象序列化为JSON字符串,重载方法3
     * @param value 被序列化的对象
     * @return JSON字符串
     * @see com.workflow.common.utils.JsonUtils#toJson(Object, DateFormat)
     */
    public String toJson(Object value) {
        return toJson(value, DEFAULT_DATEFORMAT);
    }

    /**
     * 将JSON字符串反序列化为JAVA对象
     * @param json JSON字符串
     * @param javaType JAVA对象类型
     * @param dateFormat 时间格式,默认格式为<tt>yyyy-MM-dd HH:mm:ss,SSS</tt>,默认时区为GMT+8
     * @return 指定类型的JAVA对象
     */
    public <T> T toBean(String json, Class<T> javaType, DateFormat dateFormat) {
        if (dateFormat != null) {
            if (timeZone != null) {
                dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            }
            mapper.setDateFormat(dateFormat);
        }
        T t = null;
        try {
            t = mapper.readValue(json, javaType);
        } catch (Exception e) {
            exceptionHandler(e);
        }
        return t;
    }

    /**
     * 将JSON字符串反序列化为JAVA对象
     * @param json JSON字符串
     * @param javaType JAVA对象类型
     * @return 指定类型的JAVA对象
     * @see com.workflow.common.utils.JsonUtils#toBean(String, Class, DateFormat)
     */
    public <T> T toBean(String json, Class<T> javaType) {
        return toBean(json, javaType, DEFAULT_DATEFORMAT);
    }

    /**
     * 将JSON字符串反序列化为集合
     * @param json JSON字符串
     * @param eleType 集合的元素类型
     * @return {@link List}集合
     * @param <E> 实现了序列化接口的Java类
     */
    public <E> List<E> toList(String json, Class<E> eleType) {
        List<E> list = null;
        try {
            list = mapper.readValue(json, getListType(eleType));
        } catch (Exception e) {
            exceptionHandler(e);
        }
        return list;
    }

    public List<String> getValue(String json, String key) {
        List<String> values = null;
        try {
            JsonNode root = mapper.readTree(json);
            values = root.findValuesAsText(key);
        } catch (IOException e) {
            exceptionHandler(e);
        }
        return values;
    }

    /**
     * 将JSON字符串反序列化为Map
     * @param json
     * @return
     */
    public Map<String,String> toMap(String json) {
        Map map = null;
        try {
            map = mapper.readValue(json,Map.class);
        } catch (Exception e) {
            exceptionHandler(e);
        }
        return map;
    }
    /**
     * 将JSON字符串反序列化为Map
     * @param json
     * @return
     */
    public Map<String,Object> toMapObject(String json) {
        Map map = null;
        try {
            map = mapper.readValue(json,Map.class);
        } catch (Exception e) {
            exceptionHandler(e);
        }
        return map;
    }
}

