package com.openpayd.currency.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class Mapper {

    private Mapper() {
    }

    public static final ObjectMapper jsonMapper = new ObjectMapper();

    static {
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
