package com.oteasy.ot_billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oteasy.ot_billing.model.Motorista;
import com.oteasy.ot_billing.service.MotoristaService;

@RestController
@RequestMapping("driver")
public class MotoristaController {

    @Autowired
    MotoristaService motoristaService;

    @GetMapping("{id}")
    public ResponseEntity<?> getMotoristaById(@PathVariable int id){
        return motoristaService.findMotoristaById(id);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listMotorista(){
        return motoristaService.findAllMotoristas();
    }

    @PostMapping("/new")
    public ResponseEntity<?> createMotorista(@RequestBody Motorista motorista){
        return motoristaService.createMotorista(motorista);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMotorista(@PathVariable int id){
        return motoristaService.deleteMotorista(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateMotorista(@PathVariable int id, @RequestBody Motorista motorista){
        return motoristaService.updateMotorista(id, motorista);
    }
}
