package com.example.application.services.controller;

import com.example.application.services.dto.request.VentaDTORequest;
import com.example.application.services.model.Vende;
import com.example.application.services.service.VendeService;
import com.example.application.services.utilApp.ConstantApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ConstantApp.PATH_SERVICES + "/vende/")
public class VendeController {

    @Autowired
    private VendeService service;

    @GetMapping(value = "/")
    public List<Vende> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Vende> getById(@PathVariable("id") Integer id){
        Optional<Vende> getById = service.getById(id);
        if (getById.isPresent()){
            return new ResponseEntity<>(getById.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/date")
    public ResponseEntity<List<Vende>> findByDate(@RequestBody VentaDTORequest obj){
        Optional<List<Vende>> date = service.findByDate(obj);
        return new ResponseEntity(date, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public Vende create(@RequestBody Vende obj){
        obj.setFechaVenta(LocalDate.now());
        service.create(obj);
        return obj;
    }

    @PutMapping(value = "/")
    public ResponseEntity<Vende> modify(@RequestBody Vende obj){
        if (obj != null){
            obj.setFechaVenta(LocalDate.now());
            service.modify(obj);
        }
        return getById(obj.getCodVenta());
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id){
        service.delete(id);
    }

}
