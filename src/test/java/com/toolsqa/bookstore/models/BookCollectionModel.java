package com.toolsqa.bookstore.models;

import java.util.List;

public class BookCollectionModel {

    private String userId;
    private List<IsbnItemModel> collectionOfIsbns;

    public BookCollectionModel(String userIdValid, String isbn) {}

    public BookCollectionModel(String userId, List<IsbnItemModel> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<IsbnItemModel> getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public void setCollectionOfIsbns(List<IsbnItemModel> collectionOfIsbns) {
        this.collectionOfIsbns = collectionOfIsbns;
    }
}