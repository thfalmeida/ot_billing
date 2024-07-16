package com.oteasy.ot_billing.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Representante;
import com.oteasy.ot_billing.repository.RepresentanteRepository;

@Service
public class RepresentanteService {
    
    @Autowired
    RepresentanteRepository representanteRepository;

    public ResponseEntity<?>  findRepresentanteById(int id){
        try{
            Representante representante = representanteRepository.findById(id);
            return new ResponseEntity<>(representante, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?>  findAllRepresentantes(){
        try{
            return new ResponseEntity<>(representanteRepository.findAll(), HttpStatus.OK);  
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteRepresentante(int id){
        try{
            Representante representante = representanteRepository.findById(id);
            if(representante == null || representante.getId() < 1)
                return new ResponseEntity<>("Representante não encontado", HttpStatus.BAD_REQUEST);

            representanteRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateRepresentante(int id, Representante representante){
        try{
            Representante representanteUpdated = representanteRepository.update(id, representante);
            if (representanteUpdated != null && representanteUpdated.getId() > 0) {
                return new ResponseEntity<Representante>(representanteUpdated, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> createRepresentante(Representante representante){
        if(representante == null || representante.getNome() == null || representante.getNome().isEmpty())
            return new ResponseEntity<String>("Dados inválidos para criação do cliente", HttpStatus.BAD_REQUEST);
        
        try{
            Representante newRepresentante = representanteRepository.save(representante);
            return new ResponseEntity<>(newRepresentante, HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
