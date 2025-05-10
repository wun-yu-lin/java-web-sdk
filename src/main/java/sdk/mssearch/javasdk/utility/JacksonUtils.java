package sdk.mssearch.javasdk.utility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import sdk.mssearch.javasdk.logger.SdkLoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JacksonUtils {

    private static final Logger log = SdkLoggerFactory.getLogger(JacksonUtils.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 將 Map<String, Object> 轉換成指定的 Java 對象
     *
     * @param map   要轉換的 map
     * @param clazz 目標類型
     * @param <T>   類型參數
     * @return 轉換後的 Java 對象
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * 將對象轉換成 JSON 字符串
     *
     * @param obj Java 對象
     * @return JSON 字符串
     */
    public static String objectToJson(Object obj) throws JsonProcessingException {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("Error converting object to JSON string", e);
            throw e;
        }
    }

    /**
     * 將 JSON 字符串轉換成指定類型的 Java 對象
     *
     * @param json  JSON 字符串
     * @param clazz 目標類型
     * @param <T>   類型參數
     * @return 轉換後的 Java 對象
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.warn("Error converting JSON to object", e);
            throw e;
        }
    }

    /**
     * 使用 Reflection 將 object 的所有欄位轉成 map, 如果有 JsonIgnore 會忽略
     */
    public static Map<String, Object> objectToMap(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        if (object == null) {
            return map;
        }
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(JsonIgnore.class)) {
                // 取得欄位名稱和對應的值
                String fieldName = field.getName();
                Object value = field.get(object);  // 取得欄位的值
                map.put(fieldName, value);
            }
        }
        return map;
    }

    public static JsonNode getJsonRootNode(String json) throws JsonProcessingException {
        return objectMapper.readTree(json);
    }

    public static JsonNode getJsonRootNode(Object object) throws JsonProcessingException {
        String json = objectToJson(object);
        return objectMapper.readTree(json);
    }
}
