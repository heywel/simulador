package com.example.application.services.repository;

import com.example.application.services.model.Persona;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonaRepository extends CrudRepository<Persona, Integer> {
    @Override
    List<Persona> findAll();
}
