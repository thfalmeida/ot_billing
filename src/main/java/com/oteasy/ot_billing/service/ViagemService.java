package com.oteasy.ot_billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Viagem;
import com.oteasy.ot_billing.repository.ViagemRepository;

@Service
public class ViagemService {

    @Autowired
    ViagemRepository viagemRepository;

    public ResponseEntity<?> findViagemById(int id){
        try{
            Viagem viagem = viagemRepository.findById(id);
            return new ResponseEntity<>(viagem, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public ResponseEntity<?> findAllViagens(){
        try{
            return new ResponseEntity<>(viagemRepository.findAll(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }

    public ResponseEntity<?> deleteViagem(int id){
        try{
            viagemRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }   
        
    }

    public ResponseEntity<?> createViagem(Viagem viagem){
        try{
            Viagem newViagem = viagemRepository.save(viagem);
            return new ResponseEntity<>(newViagem, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

    public ResponseEntity<?> updateViagem(int id, Viagem viagem){
        try{
            Viagem viagemUpdated = viagemRepository.update(id, viagem);
            return new ResponseEntity<>(viagemUpdated, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
