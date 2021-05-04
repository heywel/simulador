package com.example.application.services.controller;

import com.example.application.services.model.TopVenta;
import com.example.application.services.service.TopVentaService;
import com.example.application.services.utilApp.ConstantApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ConstantApp.PATH_SERVICES + "/topventa/")
public class TopVentaController {

    @Autowired
    private TopVentaService service;

    @GetMapping(value = "/")
    public List<TopVenta> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TopVenta> getById(@PathVariable("id") Integer id){
        Optional<TopVenta> getById = service.getById(id);
        if (getById.isPresent()){
            return new ResponseEntity<>(getById.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/")
    public TopVenta create(@RequestBody TopVenta obj){
        service.create(obj);
        return obj;
    }

    @PutMapping(value = "/")
    public ResponseEntity<TopVenta> modify(@RequestBody TopVenta obj){
        if (obj != null){
            service.modify(obj);
        }
        return getById(obj.getCodTop());
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id){
        service.delete(id);
    }

}
