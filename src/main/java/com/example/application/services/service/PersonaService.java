package com.example.application.services.service;

import com.example.application.services.model.Persona;
import com.example.application.services.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository repository;

    public List<Persona> getAll(){
        return repository.findAll();
    }

    public Optional<Persona> getById(@PathVariable("id") Integer id){
        Optional<Persona> getById = repository.findById(id);
        return getById;
    }

    public Persona create(@RequestBody Persona obj){
        repository.save(obj);
        return obj;
    }

    public Optional<Persona> modify(@RequestBody Persona obj){
        repository.save(obj);
        return getById(obj.getCodPersona());
    }

    public void delete(@PathVariable("id") Integer id){
        repository.deleteById(id);
    }

}
