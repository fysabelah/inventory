package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.Book;

public interface BookBusinessInterface {

    String createSku(String sku);

    void updateToInsert(Book book);
}
