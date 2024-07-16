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

import com.oteasy.ot_billing.config.service.CarroService;
import com.oteasy.ot_billing.model.Carro;

@Controller
@RequestMapping("car")
public class CarroController {

    @Autowired
    CarroService carroService;

    @GetMapping("{id}")
    public ResponseEntity<?> getCarroById(@PathVariable int id){
        return carroService.getCarById(id);
    }

    @GetMapping("list")
    public ResponseEntity<?> listCar(){
        return carroService.getAllCars();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCar(@PathVariable int id){
        return carroService.deleteCar(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCar(@PathVariable int id, @RequestBody Carro carro){
        return carroService.updateCar(id, carro);

    }

    @PostMapping("/new")
    public ResponseEntity<?> createCar(@RequestBody Carro car){
        return ResponseEntity.ok(carroService.createCar(car));
    }
}
