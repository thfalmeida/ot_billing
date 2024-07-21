package com.oteasy.ot_billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Motorista;
import com.oteasy.ot_billing.model.ObjectModel;
import com.oteasy.ot_billing.repository.Repository;

@Service
public class MotoristaService {

    @Autowired
    Repository repository;

    public ResponseEntity<?> findAllMotoristas(){
        try{
            List<ObjectModel> lista = repository.FindAll(Motorista.class);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findMotoristaById(int id){
        try{
            Motorista motorista =  (Motorista) repository.findById(id, Motorista.class);
                if(motorista != null)
                    return new ResponseEntity<Motorista>(motorista, HttpStatus.OK);
                else
                    return new ResponseEntity<String>("Motorista não encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteMotorista(int id) {
        try{
            Motorista motorista = (Motorista) repository.findById(id, Motorista.class);
            if(motorista != null && motorista.getId() > 0){
                repository.delete(id, Motorista.class);
                return new ResponseEntity<String>(HttpStatus.OK);
            }else
                return new ResponseEntity<String>("Motorista não encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> createMotorista(Motorista motorista){
        try{
            Motorista newMotorista = (Motorista) repository.save(motorista, Motorista.class);
            return new ResponseEntity<Motorista>(newMotorista,HttpStatus.OK);
        }catch(Exception e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateMotorista(int id, Motorista motorista)  {
        try{
            Motorista existingMotorista = (Motorista) repository.findById(id, Motorista.class);
            if(existingMotorista != null && existingMotorista.getId() > 0) {
                Motorista updatedMotorista = (Motorista) repository.save(id, motorista, Motorista.class);
                return new ResponseEntity<Motorista>(updatedMotorista, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Motorista não encontrado", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

   