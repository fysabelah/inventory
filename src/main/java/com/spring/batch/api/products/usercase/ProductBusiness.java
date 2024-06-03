package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.*;
import com.spring.batch.api.products.utils.enums.Genre;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class ProductBusiness {

    private final Clock clock;

    public ProductBusiness(Clock clock) {
        this.clock = clock;
    }

    public void updateToInsert(String sku, Product product) {
        product.setSku(sku);
        product.getAvailability().setUpdatedAt(LocalDateTime.now(clock));
    }

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

        return createElectronicSku(product.getElectronic());
    }

    private String createElectronicSku(Electronic electronic) {
        return normalize(electronic.getModel())
                .concat("-")
                .concat(normalize(electronic.getBrand()));
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
        return normalize(book.getIsbn());
    }

    public void verifyAndUpdateBook(String title, Integer pages, Genre genre, Product product, String publisher) throws BusinessException {
        boolean hasToUpdate = false;

        if (title != null && !title.isBlank() && title.compareTo(product.getBook().getTitle()) != 0) {
            product.getBook().setTitle(title);
            hasToUpdate = true;
        }

        if (pages >= 0 && !Objects.equals(product.getBook().getPages(), pages)) {
            hasToUpdate = true;
            product.getBook().setPages(pages);
        }

        if (genre != null && !product.getBook().getGenre().equals(genre)) {
            hasToUpdate = true;
            product.getBook().setGenre(genre);
        }

        if (publisher != null && !publisher.isBlank() && !product.getBook().getPublisher().equals(publisher)) {
            hasToUpdate = true;
            product.getBook().setPublisher(publisher);
        }

        if (!hasToUpdate) {
            throw new BusinessException("NOTHING_TO_UPDATE");
        }
    }
}
