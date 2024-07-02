package com.oteasy.ot_billing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oteasy.ot_billing.dto.CarroDTO;
import com.oteasy.ot_billing.model.Carro;
import com.oteasy.ot_billing.repository.CarroRepository;

@Controller
@RequestMapping("car")
public class CarroController {

    @Autowired
    CarroRepository carroRepository;

    @GetMapping("{id}")
    public ResponseEntity<?> getCarroById(@PathVariable int id){
        try{
            Carro carro = carroRepository.findById(id);
            return new ResponseEntity<Carro>(carro, HttpStatus.OK); 
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    @GetMapping("list")
    public ResponseEntity<?> listCar(){
        try{
            List<CarroDTO> carros = carroRepository.findAll();
            return new ResponseEntity<>(carros, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCar(@PathVariable int id){
        try{
            carroRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
