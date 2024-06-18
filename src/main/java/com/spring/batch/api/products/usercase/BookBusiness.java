package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.Book;
import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.utils.enums.Genre;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class BookBusiness extends ProductBusiness {

    public BookBusiness(Clock clock) {
        super(clock);
    }

    public void updateToInsert(Book book) {
        book.getAvailability().setUpdatedAt(LocalDateTime.now(clock));
        book.getAvailability().setSku(createSku(book));
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

    private String createSku(Book book) {
        return super.createSku(book.getIsbn(), ProductCategory.BOOKS);
    }

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
