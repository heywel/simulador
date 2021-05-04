package com.example.application.services.service;

import com.example.application.services.model.TopVenta;
import com.example.application.services.repository.TopVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class TopVentaService {

    @Autowired
    private TopVentaRepository repository;

    public List<TopVenta> getAll(){
        return repository.findAll();
    }

    public Optional<TopVenta> getById(@PathVariable("id") Integer id){
        Optional<TopVenta> getById = repository.findById(id);
        return getById;
    }

    public TopVenta create(@RequestBody TopVenta obj){
        repository.save(obj);
        return obj;
    }

    public Optional<TopVenta> modify(@RequestBody TopVenta obj){
        repository.save(obj);
        return getById(obj.getCodTop());
    }

    public void delete(@PathVariable("id") Integer id){
        repository.deleteById(id);
    }

}
