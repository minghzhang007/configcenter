package com.lewis.configcenter.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lewis.configcenter.common.core.EmptyToNullStringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class JsonUtils {

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // JSON节点不包含属性值为NULL
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule deserializeModule = new SimpleModule("DeserializeModule", new Version(1, 0, 0, null));
        deserializeModule.addDeserializer(String.class, new EmptyToNullStringDeserializer());
        objectMapper.registerModule(deserializeModule);

        SimpleModule serializeModule = new SimpleModule();
        serializeModule.addSerializer(Long.class, ToStringSerializer.instance);
        serializeModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(serializeModule);
    }

    public static <T> T toBean(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonParseException e) {
            logger.error("toBean(String, Class<T>)", e); //$NON-NLS-1$
        } catch (JsonMappingException e) {
            logger.error("toBean(String, Class<T>)", e); //$NON-NLS-1$
        } catch (IOException e) {
            logger.error("toBean(String, Class<T>)", e); //$NON-NLS-1$
        }
        return null;
    }

    public static String toString(Object obj) {
        if (obj != null) {
            try {
                return objectMapper.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                logger.error("toString(String, Class<T>)", e);
            }
        }
        return null;
    }

    private JsonUtils() {
    }

}
