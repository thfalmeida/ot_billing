package com.oteasy.ot_billing.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.oteasy.ot_billing.dto.CarroDTO;
import com.oteasy.ot_billing.model.Carro;

@Controller
@RequestMapping("car")
public class CarroController {

    @Autowired
    CarroService carroService;

    @GetMapping("{id}")
    public ResponseEntity<?> getCarroById(@PathVariable int id){
        Optional<Carro> carro = carroService.getCarById(id);
        if (carro.isPresent()) {
            return ResponseEntity.ok(carro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("list")
    public ResponseEntity<?> listCar(){
        try{
            List<CarroDTO> carros = carroService.getAllCars();
            return new ResponseEntity<>(carros, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCar(@PathVariable int id){
        try{
            carroService.deleteCar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCar(@PathVariable int id, @RequestBody Carro carro){
        Optional<Carro> OptCarro = carroService.updateCar(id, carro);

        if (OptCarro.isPresent()) {
            return ResponseEntity.ok(OptCarro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> createCar(@RequestBody Carro car){
        return ResponseEntity.ok(carroService.createCar(car));
    }
}
