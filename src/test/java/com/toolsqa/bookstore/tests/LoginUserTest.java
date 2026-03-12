package com.toolsqa.bookstore.tests;

import com.toolsqa.bookstore.base.BaseTest;
import com.toolsqa.bookstore.models.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.toolsqa.bookstore.utils.ConfigProp.getProp;
import static org.apache.http.HttpStatus.SC_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@Tag("regression")
@Tag("fazer_login")
@DisplayName("Testes de login de usuário")
public class LoginUserTest extends BaseTest {

    @Test
    @Tag("login_com_sucesso")
    @DisplayName("Login com sucesso")
    void shouldLoginSuccessfully() {
        UserModel user = new UserModel(
                getProp("USERNAME_VALID"),
                getProp("PASSWORD_VALID")
        );

        response = loginUser(user);
        response.then()
                .statusCode(SC_OK)
                .body("token", notNullValue())
                .body("expires", notNullValue())
                .body("status", equalTo("Success"))
                .body("result", equalTo("User authorized successfully."))
                .body(matchesJsonSchemaInClasspath("contracts/login/login-success.json"));
    }

    @Test
    @Tag("username_invalido")
    @DisplayName("Login com username inválido")
    void shouldNotLoginWithInvalidUsername() {
        UserModel user = new UserModel(
                "invalidUser",
                getProp("PASSWORD_VALID")
        );

        response = loginUser(user);
        response.then()
                .body("status", equalTo("Failed"))
                .body("result", equalTo("User authorization failed."))
                .body(matchesJsonSchemaInClasspath("contracts/login/login-failed.json"));
    }

    @Test
    @Tag("password_invalido")
    @DisplayName("Login com password inválido")
    void shouldNotLoginWithInvalidPassword() {
        UserModel user = new UserModel(
                getProp("USERNAME_VALID"),
                "invalidPassword"
        );

        response = loginUser(user);
        response.then()
                .body("status", equalTo("Failed"))
                .body("result", equalTo("User authorization failed."))
                .body(matchesJsonSchemaInClasspath("contracts/login/login-failed.json"));
    }

    @Test
    @Tag("username_e_password_invalidos")
    @DisplayName("Login com username e password inválidos")
    void shouldNotLoginWithInvalidUsernameAndPassword() {
        UserModel user = new UserModel(
                "invalidUser",
                "invalidPassword"
        );

        response = loginUser(user);
        response.then()
                .body("status", equalTo("Failed"))
                .body("result", equalTo("User authorization failed."))
                .body(matchesJsonSchemaInClasspath("contracts/login/login-failed.json"));
    }

    @Test
    @Tag("username_em_branco")
    @DisplayName("Login com username em branco")
    void shouldNotLoginWithEmptyUsername() {
        UserModel user = new UserModel(
                "",
                getProp("PASSWORD_VALID")
        );

        response = loginUser(user);
        response.then()
                .body("code", equalTo("1200"))
                .body("message", equalTo("UserName and Password required."))
                .body(matchesJsonSchemaInClasspath("contracts/login/login-empty-field.json"));
    }

    @Test
    @Tag("password_em_branco")
    @DisplayName("Login com password em branco")
    void shouldNotLoginWithEmptyPassword() {
        UserModel user = new UserModel(
                getProp("USERNAME_VALID"),
                ""
        );

        response = loginUser(user);
        response.then()
                .body("code", equalTo("1200"))
                .body("message", equalTo("UserName and Password required."))
                .body(matchesJsonSchemaInClasspath("contracts/login/login-empty-field.json"));
    }

}
