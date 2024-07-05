package com.oteasy.ot_billing.controller;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oteasy.ot_billing.config.service.RepresentanteService;
import com.oteasy.ot_billing.model.Representante;

@Controller
@RequestMapping("agent")
public class RepresentanteController {

    @Autowired
    RepresentanteService representanteService;

    @GetMapping("{id}")
        public ResponseEntity<?> getRepresentanteById(@PathVariable int id){
        try {
            Optional<Representante> representante = representanteService.findRepresentanteById(id);
            return representante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("list")
    public ResponseEntity<?> listRepresentante(){
        try{
            List<Representante> representantes;
            representantes = (List<Representante>) representanteService.findAllRepresentantes();
            return new ResponseEntity<List<Representante>>(representantes, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRepresentante(@PathVariable int id){
        try{
            representanteService.deleteRepresentante(id);
            return new ResponseEntity<String>("Representante deletado com sucesso!", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> createRepresentante(@RequestBody Representante representante){
        try{
            Representante newRepresentante = representanteService.createRepresentante(representante);
            return new ResponseEntity<Representante>(newRepresentante, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateRepresentante(@PathVariable int id, @RequestBody Representante representante){
        try{
            Representante updatedRepresentante = representanteService.updateRepresentante(id, representante);
            return new ResponseEntity<>(updatedRepresentante, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
