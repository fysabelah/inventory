package com.spring.batch.api.products.frameworks.db;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.Genre;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.enums.ProductSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final MongoTemplate template;

    public ProductRepositoryCustomImpl(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public List<Product> findBySkus(List<String> skus) {
        MatchOperation matchOperation = Aggregation.match(
                new Criteria().orOperator(
                        Criteria.where("book.availability.sku").in(skus),
                        Criteria.where("electronic.availability").elemMatch(Criteria.where("sku").in(skus)),
                        Criteria.where("shoes.availability").elemMatch(Criteria.where("sku").in(skus)),
                        Criteria.where("clothes.availability").elemMatch(Criteria.where("sku").in(skus))
                )
        );

        TypedAggregation<Product> aggregation = Aggregation.newAggregation(Product.class, matchOperation);

        return template.aggregate(aggregation, Product.class).getMappedResults();
    }

    @Override
    public Page<Product> findAllElectronics(String name, String brand, String model, ElectronicType electronicType, boolean status, Pageable page) {
        Query query = new Query();

        setStatusAndCategory(query, status, ProductCategory.ELECTRONICS);

        if (model != null && !model.isBlank()) {
            query.addCriteria(Criteria.where("electronic.model").is(model));
        }

        if (brand != null && !brand.isBlank()) {
            query.addCriteria(Criteria.where("electronic.brand").is(brand));
        }

        if (name != null && !name.isBlank()) {
            query.addCriteria(Criteria.where("electronic.name").is(name));
        }

        if (electronicType != null) {
            query.addCriteria(Criteria.where("electronic.type").is(electronicType));
        }

        long count = template.count(query, Product.class);

        query.with(page);

        List<Product> list = template.find(query, Product.class);

        return new PageImpl<>(list, page, count);
    }

    private static void setStatusAndCategory(Query query, boolean status, ProductCategory electronics) {
        query.addCriteria(Criteria.where("active").is(status));
        query.addCriteria(Criteria.where("category").is(electronics));
    }

    @Override
    public Page<Product> findAllClothes(String name, String brand, String model, ProductSize size, boolean status, Pageable page) {
        Query query = new Query();

        setStatusAndCategory(query, status, ProductCategory.CLOTHES);

        if (name != null && !name.isBlank()) {
            query.addCriteria(Criteria.where("clothes.name").is(name));
        }

        if (brand != null && !brand.isBlank()) {
            query.addCriteria(Criteria.where("clothes.brand").is(brand));
        }

        if (model != null && !model.isBlank()) {
            query.addCriteria(Criteria.where("clothes.model").is(model));
        }

        if (size != null) {
            query.addCriteria(Criteria.where("clothes.availability")
                    .elemMatch(Criteria.where("size").is(size)));
        }

        long count = template.count(query, Product.class);

        query.with(page);

        List<Product> list = template.find(query, Product.class);

        return new PageImpl<>(list, page, count);
    }

    @Override
    public Page<Product> findAllShoes(String name, String brand, String size, boolean status, Pageable page) {
        Query query = new Query();

        setStatusAndCategory(query, status, ProductCategory.SHOES);

        if (name != null && !name.isBlank()) {
            query.addCriteria(Criteria.where("shoes.name").is(name));
        }

        if (brand != null && !brand.isBlank()) {
            query.addCriteria(Criteria.where("shoes.brand").is(brand));
        }

        if (size != null) {
            query.addCriteria(Criteria.where("shoes.availability")
                    .elemMatch(Criteria.where("size").is(size)));
        }

        long count = template.count(query, Product.class);

        query.with(page);

        List<Product> list = template.find(query, Product.class);

        return new PageImpl<>(list, page, count);
    }

    @Override
    public Page<Product> findAllBooks(String title, Genre genre, boolean status, Pageable page) {
        Query query = new Query();

        setStatusAndCategory(query, status, ProductCategory.BOOKS);

        if (title != null && !title.isBlank()) {
            query.addCriteria(Criteria.where("book.title").is(title));
        }

        if (genre != null) {
            query.addCriteria(Criteria.where("book.genre").is(genre));
        }

        long count = template.count(query, Product.class);

        query.with(page);

        List<Product> list = template.find(query, Product.class);

        return new PageImpl<>(list, page, count);
    }
}
