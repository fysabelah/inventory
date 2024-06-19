package com.spring.batch.api.products.frameworks.db;

import com.spring.batch.api.products.entities.Product;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{ $or: [ { 'book.availability.sku': ?0 }, { 'electronic.availability.sku': ?0 }, { 'shoes.availability.sku': ?0 }, { 'clothes.availability.sku': ?0 } ] }")
    Optional<Product> findBySku(String sku);

    default List<Product> findBySkus(List<String> skus, MongoTemplate mongoTemplate) {
        MatchOperation matchOperation = Aggregation.match(
                new Criteria().orOperator(
                        Criteria.where("book.availability.sku").in(skus),
                        Criteria.where("electronic.availability").elemMatch(Criteria.where("sku").in(skus)),
                        Criteria.where("shoes.availability").elemMatch(Criteria.where("sku").in(skus)),
                        Criteria.where("clothes.availability").elemMatch(Criteria.where("sku").in(skus))
                )
        );

        TypedAggregation<Product> aggregation = Aggregation.newAggregation(Product.class, matchOperation);

        return mongoTemplate.aggregate(aggregation, Product.class).getMappedResults();
    }
}
