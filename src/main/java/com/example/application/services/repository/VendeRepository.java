package com.example.application.services.repository;

import com.example.application.services.model.Vende;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface VendeRepository extends CrudRepository<Vende, Integer> {
    @Override
    List<Vende> findAll();

    //List<Vende> getByIdProductoAndFechaVentaBetweenFechaVenta(Integer p,LocalDate inicio, LocalDate fin);

    List<Vende> findAllByIdProductoAndFechaVentaBetween(Integer id, LocalDate i, LocalDate f);
}
