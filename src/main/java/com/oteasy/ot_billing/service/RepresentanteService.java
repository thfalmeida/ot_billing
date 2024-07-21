package com.oteasy.ot_billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Representante;
import com.oteasy.ot_billing.model.ObjectModel;
import com.oteasy.ot_billing.repository.Repository;

@Service
public class RepresentanteService {
    @Autowired
    Repository repository;

    public ResponseEntity<?> findAllRepresentantes(){
        try{
            List<ObjectModel> lista = repository.FindAll(Representante.class);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findRepresentanteById(int id){
        try{
            Representante representante = (Representante) repository.findById(id, Representante.class);
                if(representante != null)
                    return new ResponseEntity<Representante>(representante, HttpStatus.OK);
                else
                    return new ResponseEntity<String>("Representante não encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteRepresentante(int id) {
        try{
            Representante representante = (Representante) repository.findById(id, Representante.class);
            if(representante != null && representante.getId() > 0){
                repository.delete(id, Representante.class);
                return new ResponseEntity<String>(HttpStatus.OK);
            }else
                return new ResponseEntity<String>("Representante não encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> createRepresentante(Representante Representante){
        try{
            Representante newRepresentante = (Representante) repository.save(Representante, Representante.class);
            return new ResponseEntity<Representante>(newRepresentante,HttpStatus.OK);
        }catch(Exception e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateRepresentante(int id, Representante Representante)  {
        try{
            Representante existingRepresentante = (Representante) repository.findById(id, Representante.class);
            if(existingRepresentante != null && existingRepresentante.getId() > 0) {
                Representante updatedRepresentante = (Representante) repository.save(id, Representante, Representante.class);
                return new ResponseEntity<Representante>(updatedRepresentante, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Representante não encontrado", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
