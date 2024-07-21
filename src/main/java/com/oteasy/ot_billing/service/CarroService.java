package com.oteasy.ot_billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Carro;
import com.oteasy.ot_billing.model.ObjectModel;
import com.oteasy.ot_billing.repository.Repository;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    private Repository repository;

        public ResponseEntity<?> findAllCarros(){
        try{
            List<ObjectModel> lista = repository.FindAll(Carro.class);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findCarroById(int id){
        try{
            Carro Carro =  (Carro) repository.findById(id, Carro.class);
                if(Carro != null)
                    return new ResponseEntity<Carro>(Carro, HttpStatus.OK);
                else
                    return new ResponseEntity<String>("Carro não encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteCarro(int id) {
        try{
            Carro carro = (Carro) repository.findById(id, Carro.class);
            if(carro != null && carro.getId() > 0){
                repository.delete(id, Carro.class);
                return new ResponseEntity<String>(HttpStatus.OK);
            }else
                return new ResponseEntity<String>("Carro não encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> createCarro(Carro carro){
        try{
            Carro newCarro = (Carro) repository.save(carro, Carro.class);
            return new ResponseEntity<Carro>(newCarro,HttpStatus.OK);
        }catch(Exception e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateCarro(int id, Carro carro)  {
        try{
            Carro existingCarro = (Carro) repository.findById(id, Carro.class);
            if(existingCarro != null && existingCarro.getId() > 0) {
                Carro updatedCarro = (Carro) repository.save(id, carro, Carro.class);
                return new ResponseEntity<Carro>(updatedCarro, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Carro não encontrado", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}