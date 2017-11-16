package com.lewis.configcenter.common.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class EmptyToNullStringDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
        String text = p.getText();
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        return text.trim();
    }
}
