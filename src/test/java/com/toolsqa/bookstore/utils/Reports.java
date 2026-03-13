package com.toolsqa.bookstore.utils;


import io.qameta.allure.Allure;
import io.restassured.response.Response;

public class Reports {

    public static void attachmentsAllure(Response response) {
        try {
            if (response != null) {
                String resp =
                        "Status Code: " + response.getStatusCode() + "\n" +
                        "Headers: " + response.getHeaders() + "\n" +
                        "Body: " + response.getBody().asString();
                Allure.addAttachment("API Response", "text/plain", resp);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao anexar resposta da API ao Allure: " + e.getMessage());
        }
    }
}
