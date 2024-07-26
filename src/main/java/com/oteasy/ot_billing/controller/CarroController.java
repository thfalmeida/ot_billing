package com.oteasy.ot_billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oteasy.ot_billing.model.Carro;
import com.oteasy.ot_billing.service.CarroService;

@Controller
@RequestMapping("car")
public class CarroController {
    
    @Autowired
    CarroService carroService;


    @GetMapping("/list")
    public ResponseEntity<?> listCarros(){
        return carroService.findAllCarros();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCarroById(@PathVariable int id){
        return carroService.findCarroById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCarro(@PathVariable int id){
        return carroService.deleteCarro(id);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createCliente(@RequestBody Carro carro){
        return carroService.createCarro(carro);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCliente(@PathVariable int id, @RequestBody Carro carro){
        return carroService.updateCarro(id, carro);
    }
}
