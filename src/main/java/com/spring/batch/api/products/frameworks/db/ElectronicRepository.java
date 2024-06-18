package com.spring.batch.api.products.frameworks.db;

import com.spring.batch.api.products.entities.Electronic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElectronicRepository extends MongoRepository<Electronic, String> {

    Optional<Electronic> findByAvailabilitySkuEquals(String sku);

    List<Electronic> findByAvailabilitySkuIn(List<String> skus);
}
