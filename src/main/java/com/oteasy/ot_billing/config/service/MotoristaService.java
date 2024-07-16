package com.oteasy.ot_billing.config.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Motorista;
import com.oteasy.ot_billing.repository.MorotistaRepository;

@Service
public class MotoristaService {

    @Autowired
    MorotistaRepository motoristaRepository;

    public ResponseEntity<?> findAllMotoristas(){
        try{
            return new ResponseEntity<List<Motorista>>(motoristaRepository.listAll(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        
    }

    public ResponseEntity<?> findMotoristaById(int id){
        try{
            return new ResponseEntity<>(motoristaRepository.findById(id), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteMotorista(int id){
        try{
            Optional<Motorista> motorista = motoristaRepository.findById(id);
            if(motorista.isPresent() && motorista.get().getId() > 0){
                motoristaRepository.deleteMotorista(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("Motorista não encontrado", HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> createMotorista(Motorista motorista){
        if(motorista == null || motorista.getNome() == null || motorista.getTelefone() == null) {
            return new ResponseEntity<>("Dados inválidos para criação do motorista", HttpStatus.BAD_REQUEST);
        }

        try{
            Motorista newMotorista = motoristaRepository.save(motorista);
            return new ResponseEntity<>(newMotorista, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateMotorista(int id, Motorista motorista){
        try{
            Optional<Motorista> existingMotorista = motoristaRepository.findById(id);
            if(!existingMotorista.isPresent()) 
                return new ResponseEntity<>("Motorista não encontrado", HttpStatus.BAD_REQUEST);

            Motorista updatedMotorista = motoristaRepository.updateMotorista(id, motorista);
            return new ResponseEntity<>(updatedMotorista, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
