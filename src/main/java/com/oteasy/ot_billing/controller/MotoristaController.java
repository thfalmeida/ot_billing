package com.oteasy.ot_billing.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.oteasy.ot_billing.config.service.MotoristaService;

@RestController
@RequestMapping("driver")
public class MotoristaController {

    @Autowired
    MotoristaService motoristaService;

    @GetMapping("{id}")
    public ResponseEntity<?> getMotoristaById(@PathVariable int id){
        try {
            Optional<Motorista> motorista = motoristaService.findMotoristaById(id);
            return motorista.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Motorista>> listMotorista(){
        ArrayList<Motorista> motoristas;
        try{
            motoristas = (ArrayList<Motorista>) motoristaService.findAllMotoristas();
            return new ResponseEntity<List<Motorista>>(motoristas, HttpStatus.OK);

        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Motorista> createMotorista(@RequestBody Motorista motorista){
        try{
            Motorista newMotorista = null;
            newMotorista = motoristaService.createMotorista(motorista);
            return new ResponseEntity<Motorista>(newMotorista, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<Motorista>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMotorista(@PathVariable int id){
        try{
            motoristaService.deleteMotorista(id);
            return new ResponseEntity<String>("Motorista deletado com sucesso!", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateMotorista(@PathVariable int id, @RequestBody Motorista motorista){
        try{
            Motorista motoristaUpdated;
            motoristaUpdated = motoristaService.updateMotorista(id, motorista);
            return new ResponseEntity<Motorista>(motoristaUpdated, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
