package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.*;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductBusiness {

    public String createSku(Product product) {
        if (ProductCategory.BOOKS.equals(product.getCategory())) {
            return createBookSku(product.getBook());
        }

        if (ProductCategory.CLOTHES.equals(product.getCategory())) {
            return createClothesSku(product.getClothes());
        }

        if (ProductCategory.SHOES.equals(product.getCategory())) {
            return createShoesSku(product.getShoe());
        }

        return createEletronicSku(product.getEletronic());
    }

    private String createEletronicSku(Eletronic eletronic) {
        return normalize(eletronic.getModel())
                .concat("-")
                .concat(normalize(eletronic.getBrand()))
                .concat("-")
                .concat(normalize(eletronic.getBrand()));
    }

    private String normalize(String value) {
        return value.replaceAll("\\s", "-");
    }

    private String createShoesSku(Shoe shoe) {
        return normalize(shoe.getName())
                .concat("-")
                .concat(normalize(shoe.getBrand()))
                .concat("-")
                .concat(normalize(shoe.getColor()))
                .concat("-")
                .concat(normalize(shoe.getSize()));
    }

    private String createClothesSku(Clothes clothes) {
        return normalize(clothes.getName())
                .concat("-")
                .concat(normalize(clothes.getModel()))
                .concat("-")
                .concat(normalize(clothes.getBrand()))
                .concat("-")
                .concat(normalize(clothes.getSize().name()));
    }

    private String createBookSku(Book book) {
        return normalize(book.getTitle())
                .concat("-")
                .concat(normalize(book.getPublisher()));
    }
}
