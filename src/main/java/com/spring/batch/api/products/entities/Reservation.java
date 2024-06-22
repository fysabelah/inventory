package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.utils.enums.ReservationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Document(value = "reservations")
public class Reservation {

    @Id
    private String id;

    private String sku;

    private Integer quantity;

    private ReservationStatus status;

    private LocalDateTime date;
}
