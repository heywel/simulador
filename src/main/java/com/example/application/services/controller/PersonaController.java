package com.example.application.services.controller;

import com.example.application.services.model.Persona;
import com.example.application.services.service.PersonaService;
import com.example.application.services.utilApp.ConstantApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ConstantApp.PATH_SERVICES +"/persona/")
public class PersonaController {

    @Autowired
    private PersonaService service;

    @GetMapping(value = "/")
    public List<Persona> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") Integer id){
        Optional<Persona> getById = service.getById(id);
        if (getById.isPresent()){
            return new ResponseEntity<>(getById.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/")
    public Persona create(@RequestBody Persona obj){
        service.create(obj);
        return obj;
    }

    @PutMapping(value = "/")
    public ResponseEntity<Persona> modify(@RequestBody Persona obj){
        if (obj != null){
            service.modify(obj);
        }
        return getById(obj.getCodPersona());
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id){
        service.delete(id);
    }

}
