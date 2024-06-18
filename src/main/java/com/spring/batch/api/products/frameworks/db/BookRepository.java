package com.spring.batch.api.products.frameworks.db;


import com.spring.batch.api.products.entities.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByAvailabilitySku(String sku);
}
