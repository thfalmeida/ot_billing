package com.oteasy.ot_billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oteasy.ot_billing.service.RepresentanteService;
import com.oteasy.ot_billing.model.Representante;

@Controller
@RequestMapping("agent")
public class RepresentanteController {

    @Autowired
    RepresentanteService representanteService;

    @GetMapping("{id}")
    public ResponseEntity<?> getRepresentanteById(@PathVariable int id){
        return representanteService.findRepresentanteById(id);
    }

    @GetMapping("list")
    public ResponseEntity<?> listRepresentante(){
        return representanteService.findAllRepresentantes();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRepresentante(@PathVariable int id){
        return representanteService.deleteRepresentante(id);
    }

    @PostMapping
    public ResponseEntity<?> createRepresentante(@RequestBody Representante representante){
        return representanteService.createRepresentante(representante);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateRepresentante(@PathVariable int id, @RequestBody Representante representante){
        return representanteService.updateRepresentante(id, representante);
    }
    
}
