package com.example.application.services.service;

import com.example.application.services.model.Producto;
import com.example.application.services.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    public List<Producto> getAll(){
        return repository.findAll();
    }

    public Optional<Producto> getById(@PathVariable("id") Integer id){
        Optional<Producto> getById = repository.findById(id);
        //Optional<Producto> prod = repository.findByIdProducto(id);
        return getById;
    }

    public Producto create(@RequestBody Producto obj){
        repository.save(obj);
        return obj;
    }

    public Optional<Producto> modify(@RequestBody Producto obj){
        repository.save(obj);
        return getById(obj.getIdProducto());
    }

    public void delete(@PathVariable("id") Integer id){
        repository.deleteById(id);
    }

}
