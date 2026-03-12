package com.toolsqa.bookstore.tests;

import com.toolsqa.bookstore.base.BaseTest;
import com.toolsqa.bookstore.models.UserModel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.toolsqa.bookstore.utils.Helpers.extractUserId;
import static com.toolsqa.bookstore.utils.Helpers.generateToken;

import static org.apache.http.HttpStatus.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Tag("criar_usuario")
@Tag("regression")
@DisplayName("Testes de criação de usuário")
public class CreateUserTest extends BaseTest {

    @Test
    @Tag("criar_com_sucesso")
    @DisplayName("Criar uma conta com sucesso")
    void shouldCreateUserSuccessfully() {
        UserModel user = new UserModel(
                "user" + faker.number().randomNumber(),
                "Senha@123"
        );

        response = createUser(user);
        response.then()
                .statusCode(SC_CREATED)
                .body(matchesJsonSchemaInClasspath("resources/contracts/createUser/create-user-success.json"));

        String userId = extractUserId(response);
        String token = generateToken(user);
        deleteUser(userId, token).then().statusCode(SC_NO_CONTENT);
    }

    @Test
    @Tag("username_em_branco")
    @DisplayName("Criar uma conta com username em branco")
    void shouldNotCreateUserWithEmptyUsername() {
        UserModel user = new UserModel("", "Senha@123");

        response = createUser(user);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(matchesJsonSchemaInClasspath("resources/contracts/createUser/create-user-empty-username.json"));
    }

    @Test
    @Tag("password_em_branco")
    @DisplayName("Criar uma conta com password em branco")
    void shouldNotCreateUserWithEmptyPassword() {
        UserModel user = new UserModel(
                "user" + faker.number().randomNumber(),
                ""
        );

        response = createUser(user);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(matchesJsonSchemaInClasspath("resources/contracts/createUser/create-user-empty-password.json"));
    }

    @Test
    @Tag("username_e_password_em_branco")
    @DisplayName("Criar uma conta com username e password em branco")
    void shouldNotCreateUserWithEmptyUsernameAndPassword() {
        UserModel user = new UserModel("", "");

        response = createUser(user);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(matchesJsonSchemaInClasspath("resources/contracts/createUser/create-user-empty-username.json"));
    }

    @Test
    @Tag("password_sem_caracteres_especiais")
    @DisplayName("Criar uma conta com password sem caracteres especiais")
    void shouldNotCreateUserWithoutSpecialCharacterInPassword() {
        UserModel user = new UserModel(
                "user" + faker.number().randomNumber(),
                "Senha123"
        );

        response = createUser(user);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body(matchesJsonSchemaInClasspath("resources/contracts/createUser/create-user-password-invalid-format.json"));
    }

    @Test
    @Tag("username_duplicado")
    @DisplayName("Criar uma conta com os mesmos dados de uma conta já existente")
    void shouldNotCreateUserWithDuplicatedUsername() {
        UserModel user = new UserModel(
                "user" + faker.number().randomNumber(),
                "Senha@123"
        );

        createUser(user);
        response = createUser(user);
        response.then()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(matchesJsonSchemaInClasspath("resources/contracts/createUser/create-user-duplicate.json"));
    }
}