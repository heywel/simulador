package com.example.application.services.service;

import com.example.application.services.dto.request.VentaDTORequest;
import com.example.application.services.model.Vende;
import com.example.application.services.repository.VendeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class VendeService {

    @Autowired
    private VendeRepository repository;

    public List<Vende> getAll(){
        return repository.findAll();
    }

    public Optional<Vende> getById(@PathVariable("id") Integer id){
        Optional<Vende> getById = repository.findById(id);
        return getById;
    }

    public Optional<List<Vende>>  findByDate(@RequestBody VentaDTORequest obj){
        Optional<List<Vende>> date = Optional.ofNullable(
                //repository.getByIdProductoAndFechaVentaBetweenFechaVenta(obj.getProducto(), obj.getFechaInicio(), obj.getFechaFin()));
                repository.findAllByIdProductoAndFechaVentaBetween(obj.getProducto(), obj.getFechaInicio(), obj.getFechaFin()));
        return date;
    }

    public Vende create(@RequestBody Vende obj){
        repository.save(obj);
        return obj;
    }

    public Optional<Vende> modify(@RequestBody Vende obj){
        repository.save(obj);
        return getById(obj.getIdProducto());
    }

    public void delete(@PathVariable("id") Integer id){
        repository.deleteById(id);
    }

}
