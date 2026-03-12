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
import static org.hamcrest.CoreMatchers.equalTo;

@Tag("regression")
@Tag("deletar_conta")
@DisplayName("Testes de deleção de conta")
public class AccountDeletionTest extends BaseTest {

    @Test
    @Tag("criar_e_deletar_conta")
    @DisplayName("Criar uma conta e deletar a mesma")
    void shouldCreateAndDeleteUser() {
        UserModel user = new UserModel("user" + faker.number().randomNumber(), "Test@123");
        response = createUser(user);

        String userId = extractUserId(response);
        String token = generateToken(user);

        response = deleteUser(userId, token);
        response.then()
                .body(equalTo(""))
                .statusCode(SC_NO_CONTENT);
    }

    @Test
    @Tag("deletar_com_id_invalido")
    @DisplayName("Deletar uma conta com ID inválido")
    void shouldNotDeleteUserWithInvalidId() {
        UserModel user = new UserModel("user" + faker.number().randomNumber(), "Test@123");
        String token = generateToken(user);

        deleteUser("000000000", token)
                .then()
                .statusCode(SC_OK)
                .body("code", equalTo("1207"))
                .body("message", equalTo("User Id not correct!"))
                .body(matchesJsonSchemaInClasspath("contracts/deleteUser/delete-user-invalid-id.json"));
    }


    @Test
    @Tag("deletar_e_falhar_login")
    @DisplayName("Deletar uma conta e tentar realizar login com a mesma")
    void shouldNotLoginAfterDeletingUser() {
        UserModel user = new UserModel("user" + faker.number().randomNumber(), "Test@123");
        response = createUser(user);

        String userId = extractUserId(response);
        String token = generateToken(user);
        deleteUser(userId, token);

        response = loginUser(user);
        response.then()
                .body("token", equalTo(null))
                .body("expires", equalTo(null))
                .body("status", equalTo("Failed"))
                .body("result", equalTo("User authorization failed."));
    }
}