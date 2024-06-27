package com.spring.batch.api.products.interfaceadapters.gateways;

import com.spring.batch.api.products.entities.Reservation;
import com.spring.batch.api.products.frameworks.db.ReservationRepository;
import com.spring.batch.api.products.utils.MessageUtil;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
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

    public List<Reservation> findById(List<String> ids) throws BusinessException {
        List<Reservation> reservations = repository.findAllById(ids);

        if (reservations.isEmpty()) {
            throw new BusinessException("RESERVATIONS_NOT_FOUND");
        }

        return reservations;
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

    public List<Reservation> update(List<Reservation> reservations) {
        return repository.saveAll(reservations);
    }
}
