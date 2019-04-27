package com.sapozhnikov.organizationmanagement.web.controller.rest;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

class TestUtils {
    static String readFile(String relativePathToFile) throws IOException {
        return IOUtils.toString(TestUtils.class
                .getResourceAsStream(relativePathToFile), StandardCharsets.UTF_8);
    }
}
