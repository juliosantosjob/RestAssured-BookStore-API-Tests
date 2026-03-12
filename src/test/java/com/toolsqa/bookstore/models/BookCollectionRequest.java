package com.toolsqa.bookstore.models;

import java.util.List;

public class BookCollectionRequest {

    private String userId;
    private List<IsbnItem> collectionOfIsbns;

    public BookCollectionRequest(String userIdValid, String isbn) {
    }

    public BookCollectionRequest(String userId, List<IsbnItem> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<IsbnItem> getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public void setCollectionOfIsbns(List<IsbnItem> collectionOfIsbns) {
        this.collectionOfIsbns = collectionOfIsbns;
    }
}