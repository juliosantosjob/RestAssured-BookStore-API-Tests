package com.toolsqa.bookstore.base;

import com.github.javafaker.Faker;
import com.toolsqa.bookstore.models.BookCollectionModel;
import com.toolsqa.bookstore.models.UserModel;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import static com.toolsqa.bookstore.utils.ConfigProp.getProp;
import static com.toolsqa.bookstore.utils.Reports.attachmentsAllure;

public class BaseTest implements Constants, Routes {
    protected static final Logger logger = LogManager.getLogger();
    protected static String baseUrl = getProp("BASE_URL");
    protected static Faker faker = new Faker();
    protected Response response;

    @BeforeEach
    public void setupRestAssured(TestInfo testInfo) {
        logger.info("Iniciando setup RestAssured");
        ResponseSpecBuilder responseSpec = new ResponseSpecBuilder();

        responseSpec.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
        RestAssured.baseURI = baseUrl;
        RestAssured.responseSpecification = responseSpec.build();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", CONTENT_TYPE).build();

        RestAssured.config = RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        beforeOfEachTest(testInfo);
    }

    @AfterEach
    public void endSetup(TestInfo testInfo) {
        attachmentsAllure(response);
        afterOfEachTest(testInfo);
    }

    public void beforeOfEachTest(TestInfo testInfo) {
        logger.info("Cenário: {}", testInfo.getDisplayName());
    }

    public void afterOfEachTest(TestInfo testInfo) {
        logger.info("Status da chamada: {}", response.getStatusCode() + "\n");
    }

    public static Response createUser(UserModel user) {
        logger.info("Criando usuário {}", user.getUserName());

        try {
            return RestAssured
                    .given()
                    .body(user)
                    .when()
                    .post(CREATE_USER);
        } catch (Exception e) {
            logger.error("Erro ao criar usuário: {}", e.getMessage());
            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage());
        }
    }

    public static Response loginUser(UserModel credentials) {
        logger.info("Iniciando login para o usuário: {}", credentials.getUserName());

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

    public static Response getBooks() {
        logger.info("Obtendo lista de livros disponíveis");

        try {
            return RestAssured
                    .given()
                    .when()
                    .get(BOOKS);
        } catch (Exception e) {
            logger.error("Erro ao obter lista de livros: {}", e.getMessage());
            throw new RuntimeException("Erro ao obter lista de livros: " + e.getMessage());
        }
    }

    public static Response addBookToCollection(String token, BookCollectionModel body) {
        logger.info("Adicionando livro à coleção do usuário com token: {}", token);

        try {
            return RestAssured
                    .given()
                    .header("Authorization", "Bearer " + token)
                    .body(body)
                    .when()
                    .post(BOOKS);
        } catch (Exception e) {
            logger.error("Erro ao adicionar livro: {}", e.getMessage());
            throw new RuntimeException("Erro ao adicionar livro: " + e.getMessage());
        }
    }

    public static Response deleteBooks(String userId, String token) {
        logger.info("Deletando livros da coleção do usuário com ID: {}", userId);

        try {
            return RestAssured
                    .given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .delete(BOOKS + "?UserId=" + userId);
        } catch (Exception e) {
            logger.error("Erro ao deletar livros: {}", e.getMessage());
            throw new RuntimeException("Erro ao deletar livros: " + e.getMessage());
        }
    }

    public static Response deleteUser(String userId, String token) {
        logger.info("Deletando usuário com ID: {}", userId);

        try {
            return RestAssured
                    .given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .delete(DELETE_USER + userId);
        } catch (Exception e) {
            logger.error("Erro ao deletar usuário: {}", e.getMessage());
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

}