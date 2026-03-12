package com.toolsqa.bookstore.tests;

import com.toolsqa.bookstore.base.BaseTest;
import com.toolsqa.bookstore.models.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.google.common.base.Predicates.equalTo;
import static com.toolsqa.bookstore.utils.Helpers.extractUserId;
import static com.toolsqa.bookstore.utils.Helpers.generateToken;
import static org.apache.http.HttpStatus.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.nullValue;

@Tag("deletar_conta")
@Tag("regression")
public class AccountDeletionTest extends BaseTest {

    @Test
    @Tag("criar_e_deletar_conta")
    @DisplayName("Criar uma conta e deletar a mesma")
    void shouldCreateAndDeleteUser() {

        UserModel user = new UserModel(
                "user" + faker.number().randomNumber(),
                "Test@123"
        );

        // cria usuário
        response = createUser(user);

        response.then()
                .statusCode(SC_CREATED);

        String userId = extractUserId(response);

        String token = generateToken(user);

        // deleta usuário
        deleteUser(userId, token)
                .then()
                .statusCode(SC_NO_CONTENT);
    }

    @Test
    @Tag("deletar_com_id_invalido")
    @DisplayName("Deletar uma conta com ID inválido")
    void shouldNotDeleteUserWithInvalidId() {
        UserModel user = new UserModel(
                "user" + faker.number().randomNumber(),
                "Test@123"
        );

        String token = generateToken(user);

        deleteUser("000000000", token)
                .then()
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("resources/contracts/deleteUser/delete-user-invalid-id.json"));
    }


    @Test
    @Tag("deletar_e_falhar_login")
    @DisplayName("Deletar uma conta e tentar realizar login com a mesma")
    void shouldNotLoginAfterDeletingUser() {

        UserModel user = new UserModel(
                "user" + faker.number().randomNumber(),
                "Test@123"
        );

        response = createUser(user);

        String userId = extractUserId(response);
        String token = generateToken(user);

        deleteUser(userId, token)
                .then()
                .statusCode(SC_NO_CONTENT);

        response = loginUser(user);
        response.then()
                .statusCode(SC_OK);
    }
}