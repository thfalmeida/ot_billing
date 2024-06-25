package com.oteasy.ot_billing.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oteasy.ot_billing.model.Motorista;
import com.oteasy.ot_billing.dto.MotoristaDTO;
import com.oteasy.ot_billing.repository.MorotistaRepository;

@RestController
@RequestMapping("driver")
public class MotoristaController {

    @Autowired
    MorotistaRepository motoristaRepository;

    

    @GetMapping("{id}")
    public ResponseEntity<Motorista> GetMotoristaById(@PathVariable int id) throws IOException, InterruptedException{
        Motorista motorista = null;
        try{
            motorista = motoristaRepository.findById(id);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        HttpStatus httpStatus = HttpStatus.OK;
        if(motorista == null)
            httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<Motorista>(motorista, httpStatus);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MotoristaDTO>> ListMotorista(){
        ArrayList<MotoristaDTO> motoristas;
        // dotenv.get("TEST");
        try{
            motoristas = (ArrayList<MotoristaDTO>) motoristaRepository.listAll();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(motoristas, HttpStatus.OK);

    }

    @PostMapping("/new")
    public ResponseEntity<Motorista> CreateMotorista(@RequestBody Motorista motorista){
        Motorista newMotorista = null;
        
        try{
            newMotorista = motoristaRepository.save(motorista);
        }catch(Exception e){
            return new ResponseEntity<Motorista>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Motorista>(newMotorista, HttpStatus.CREATED);
        
    }
}
