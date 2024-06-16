package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.utils.enums.Genre;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class BookBusiness extends ProductBusiness {

    public BookBusiness(Clock clock) {
        super(clock);
    }

    public void updateToInsert(String sku, Product product) {
        /*product.getBook().getAvailability().setSku(sku);
        product.getBook().getAvailability().setUpdatedAt(LocalDateTime.now(clock));*/
    }

    public void verifyAndUpdateBook(String title, Integer pages, Genre genre, String publisher, Product product) throws BusinessException {
        /*boolean hasToUpdate = false;

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
        }*/
    }

    /*public String createSku(Product product) {
        return super.getSku(List.of(product.getBook().getIsbn()));
    }*/

    @Override
    public void updateQuantity(Integer quantity, Integer protection, LocalDateTime updatedAt, Product product) throws BusinessException {
        /*super.checkIfShouldUpdateQuantity(product.getBook().getAvailability().getUpdatedAt(),
                updatedAt, quantity, protection);

        product.getBook().getAvailability().setUpdatedAt(updatedAt);
        product.getBook().getAvailability().setQuantity(quantity);
        product.getBook().getAvailability().setProtection(protection);*/
    }

    @Override
    public void updateSpecificInformation(Product toUpdate, Product newInformation) throws BusinessException {

    }
}
