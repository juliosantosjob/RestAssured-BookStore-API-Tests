package com.toolsqa.bookstore.tests;

import com.fasterxml.jackson.databind.JsonNode;

import com.toolsqa.bookstore.base.BaseTest;
import com.toolsqa.bookstore.models.BookCollectionModel;
import com.toolsqa.bookstore.models.IsbnItemModel;
import com.toolsqa.bookstore.models.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.toolsqa.bookstore.utils.ConfigProp.getProp;
import static com.toolsqa.bookstore.utils.Helpers.generateToken;
import static com.toolsqa.bookstore.utils.Helpers.readJson;
import static org.apache.http.HttpStatus.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;

@Tag("regression")
@Tag("gerenciar_livros")
@DisplayName("Testes de gerenciamento de livros")
public class BookManagementTest extends BaseTest {
    JsonNode data = readJson("src/test/resources/data/listBooks.json");
    JsonNode booksNode = data.get("books");

    int size = booksNode.size();
    String isbn = booksNode.get(size - 1).get("isbn").asText();

    @Test
    @Tag("listar_livros_disponiveis")
    @DisplayName("Acessar a lista de livros disponíveis")
    void shouldListAvailableBooks() {
        response = getBooks();
        response.then()
                .statusCode(SC_OK)
                .body("books[0].isbn", equalTo("9781449325862"))
                .body("books[0].title", equalTo("Git Pocket Guide"))
                .body("books[0].author", equalTo("Richard E. Silverman"))
                .body(matchesJsonSchemaInClasspath("contracts/bookManagement/list-books-success.json"));
    }

    @Test
    @Tag("adicionar_e_remover_livro")
    @DisplayName("Adicionar e remover um livro à lista de favoritos")
    void shouldAddBookToFavorites() {
        String username = getProp("USERNAME_VALID");
        String password = getProp("PASSWORD_VALID");
        String userId = getProp("USER_ID_VALID");

        UserModel user = new UserModel(username, password);
        IsbnItemModel isbnItem = new IsbnItemModel(isbn);

        List<IsbnItemModel> collection = List.of(isbnItem);
        BookCollectionModel books = new BookCollectionModel(userId, collection);
        String token = generateToken(user);
        deleteBooks(userId, token);

        response = addBookToCollection(token, books);
        response.then()
                .statusCode(SC_CREATED)
                .body(matchesJsonSchemaInClasspath("contracts/bookManagement/add-book-success.json"));

        deleteBooks(userId, token)
                .then()
                .statusCode(SC_NO_CONTENT);
    }

    @Test
    @Tag("livro_inexistente")
    @DisplayName("Adicionar um livro inexistente")
    void shouldNotAddNonexistentBook() {
        String username = getProp("USERNAME_VALID");
        String password = getProp("PASSWORD_VALID");
        String userId = getProp("USER_ID_VALID");

        UserModel user = new UserModel(username, password);
        IsbnItemModel isbnItem = new IsbnItemModel("0000000000000");

        List<IsbnItemModel> collection = List.of(isbnItem);
        BookCollectionModel books = new BookCollectionModel(userId, collection);

        String token = generateToken(user);
        response = addBookToCollection(token, books);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("code", equalTo("1205"))
                .body("message", equalTo("ISBN supplied is not available in Books Collection!"))
                .body(matchesJsonSchemaInClasspath("contracts/bookManagement/add-book-invalid-isbn.json"));
    }

    @Test
    @Tag("deletar_livro_usuario_invalido")
    @DisplayName("Deletar um livro inexistente")
    void shouldNotDeleteBooksFromInvalidUser() {
        String username = getProp("USERNAME_VALID");
        String password = getProp("PASSWORD_VALID");

        UserModel user = new UserModel(username, password);
        String token = generateToken(user);

        response = deleteBooks("0000000000000000", token);
        response.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("code", equalTo("1207"))
                .body("message", equalTo("User Id not correct!"))
                .body(matchesJsonSchemaInClasspath("contracts/bookManagement/delete-book-invalid-user.json"));
    }

}