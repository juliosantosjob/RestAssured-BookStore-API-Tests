package com.toolsqa.bookstore.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toolsqa.bookstore.base.BaseTest;
import com.toolsqa.bookstore.models.UserModel;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;

public class Helpers extends BaseTest {

    public static String generateToken(UserModel user) {
        return loginUser(user)
                .then()
                .extract()
                .jsonPath()
                .getString("token");
    }

    public static String extractUserId(Response response) {
        return response
                .jsonPath()
                .getString("userID");
    }

    public static JsonNode readJson(String pathJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(new File(pathJson));
        } catch (IOException e) {
            logger.error("Error reading JSON file: {}", e.getMessage());
            throw new RuntimeException("Error reading JSON file: " + pathJson, e);
        }
    }
}
