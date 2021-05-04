package com.example.application.services.controller;

import com.example.application.services.model.Producto;
import com.example.application.services.service.ProductoService;
import com.example.application.services.utilApp.ConstantApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ConstantApp.PATH_SERVICES + "/producto/")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping(value = "/")
    public List<Producto> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") Integer id){
        Optional<Producto> getById = service.getById(id);
        if (getById.isPresent()){
            return new ResponseEntity<>(getById.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/")
    public Producto create(@RequestBody Producto obj){
        service.create(obj);
        return obj;
    }

    @PutMapping(value = "/")
    public ResponseEntity<Producto> modify(@RequestBody Producto obj){
        if (obj != null){
            service.modify(obj);
        }
        return getById(obj.getIdProducto());
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id){
        service.delete(id);
    }

}
