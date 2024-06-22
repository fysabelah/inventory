package com.spring.batch.api.products.frameworks.db;

import com.spring.batch.api.products.entities.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRepository extends MongoRepository<Reservation, String> {

}
