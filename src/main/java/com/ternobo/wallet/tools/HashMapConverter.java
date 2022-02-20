package com.ternobo.wallet.tools;

import java.io.IOException;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private static final Logger logger = LoggerFactory.getLogger(HashMapConverter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> meta) {

        String metaJson = null;
        try {
            metaJson = objectMapper.writeValueAsString(meta);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return metaJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String metaJson) {

        Map<String, Object> meta = null;
        try {
            meta = objectMapper.readValue(metaJson, Map.class);
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return meta;
    }

}