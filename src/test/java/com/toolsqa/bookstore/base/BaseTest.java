package com.toolsqa.bookstore.base;

import com.github.javafaker.Faker;
import com.toolsqa.bookstore.models.BookCollectionRequest;
import com.toolsqa.bookstore.models.UserModel;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.JsonConfig;
import io.restassured.config.LogConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.toolsqa.bookstore.utils.ConfigProp.getProp;
import static com.toolsqa.bookstore.utils.Reports.attachmentsAllure;

public class BaseTest implements Constants, Routes {
    protected static final Logger logger = LogManager.getLogger();
    protected static String baseUrl = getProp("BASE_URL");
    protected static Faker faker = new Faker();
    protected Response response;

    @BeforeEach
    public void setupRestAssured() {
        ResponseSpecBuilder responseSpec = new ResponseSpecBuilder();

        responseSpec.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
        RestAssured.baseURI = baseUrl;
        RestAssured.responseSpecification = responseSpec.build();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", CONTENT_TYPE).build();

        RestAssured.config = RestAssured.config()
                .jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE))
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }

    @AfterEach
    public void endSetup() {
        attachmentsAllure(response);
    }

    public static Response createUser(UserModel user) {
        try {
            return RestAssured
                    .given()
                    .body(user)
                    .when()
                    .post(CREATE_USER);
        } catch (Exception e) {
            logger.error("Erro ao criar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage());
        }
    }

    public static Response loginUser(UserModel credentials) {
        try {
            return RestAssured
                    .given()
                    .body(credentials)
                    .when()
                    .post(LOGIN);
        } catch (Exception e) {
            logger.error("Erro ao gerar token: " + e.getMessage());
            throw new RuntimeException("Erro ao gerar token: " + e.getMessage());
        }
    }

    public static Response getUserById(String userId, String token) {
        try {
            return RestAssured
                    .given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .get(GET_USER + userId);
        } catch (Exception e) {
            logger.error("Erro ao obter usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao obter usuário: " + e.getMessage());
        }
    }

    public static Response getBooks() {
        try {
            return RestAssured
                    .given()
                    .when()
                    .get(BOOKS);
        } catch (Exception e) {
            logger.error("Erro ao obter lista de livros: " + e.getMessage());
            throw new RuntimeException("Erro ao obter lista de livros: " + e.getMessage());
        }
    }

    public static Response addBookToCollection(String token, BookCollectionRequest body) {
        try {
            return RestAssured
                    .given()
                    .header("Authorization", "Bearer " + token)
                    .body(body)
                    .when()
                    .post(BOOKS);
        } catch (Exception e) {
            logger.error("Erro ao adicionar livro: " + e.getMessage());
            throw new RuntimeException("Erro ao adicionar livro: " + e.getMessage());
        }
    }

    public static Response deleteBooks(String userId, String token) {
        try {
            return RestAssured
                    .given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .delete(BOOKS + "?UserId=" + userId);
        } catch (Exception e) {
            logger.error("Erro ao deletar livros: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar livros: " + e.getMessage());
        }
    }

    public static Response deleteUser(String userId, String token) {
        try {
            return RestAssured
                    .given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .delete(DELETE_USER + userId);
        } catch (Exception e) {
            logger.error("Erro ao deletar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}