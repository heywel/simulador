package com.example.application.services.repository;

import com.example.application.services.model.TopVenta;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TopVentaRepository extends CrudRepository<TopVenta, Integer> {
    @Override
    List<TopVenta> findAll();

}
