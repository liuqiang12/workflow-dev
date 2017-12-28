package com.workflow.portal.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflow.portal.entity.Result;

import java.io.IOException;

public class JsonUtils {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * 将json转换为Result
     * @param json
     * @param clazz
     * @return
     */
    public static Result jsonToResult(String json, Class<?> clazz) {

        try {
            if (clazz == null) {
                return MAPPER.readValue(json, Result.class);
            }

            JsonNode jsonNode = MAPPER.readTree(json);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }

            return new Result(jsonNode.get("status").intValue(), obj, jsonNode.get("error").asText());
        } catch (IOException e) {
            return null;
        }

    }


}
