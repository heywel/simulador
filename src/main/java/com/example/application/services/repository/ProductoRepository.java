package com.example.application.services.repository;

import com.example.application.services.model.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends CrudRepository<Producto, Integer> {
    @Override
    List<Producto> findAll();

    @Override
    Optional<Producto> findById(Integer integer);

}
