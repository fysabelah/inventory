package com.spring.batch.api.products;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class TestUtils {

    protected final ObjectMapper objectMapper;

    public TestUtils() {
        this.objectMapper = new ObjectMapper();
    }

    protected File getMock(String path) {
        return new File(path);
    }
}
