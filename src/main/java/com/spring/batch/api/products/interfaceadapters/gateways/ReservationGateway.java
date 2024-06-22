package com.spring.batch.api.products.interfaceadapters.gateways;

import com.spring.batch.api.products.entities.Reservation;
import com.spring.batch.api.products.frameworks.db.ReservationRepository;
import com.spring.batch.api.products.utils.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationGateway {

    private final ReservationRepository repository;

    public ReservationGateway(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<Reservation> insert(List<Reservation> reservations) {
        return repository.insert(reservations);
    }

    public Reservation findById(String id) {
        return findByIdOptional(id)
                .orElseThrow(() -> new NoSuchElementException(MessageUtil.getMessage("NOT_FOUND")));
    }

    public Optional<Reservation> findByIdOptional(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("MISSING_PARAMETERS"));
        }

        return repository.findById(id);
    }

    public Reservation update(Reservation reservation) {
        return repository.save(reservation);
    }
}
