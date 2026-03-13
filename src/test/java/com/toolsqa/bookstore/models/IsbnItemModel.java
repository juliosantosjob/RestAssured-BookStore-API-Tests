package com.toolsqa.bookstore.models;

public class IsbnItemModel {

    private String isbn;

    public IsbnItemModel() {}

    public IsbnItemModel(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}